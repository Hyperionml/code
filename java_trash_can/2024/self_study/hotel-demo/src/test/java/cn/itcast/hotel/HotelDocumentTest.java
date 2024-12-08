package cn.itcast.hotel;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static cn.itcast.hotel.constant.HotelConstants.MAPPING_TEMPLATE;

@SpringBootTest
public class HotelDocumentTest {

    @Autowired
    private IHotelService hotelService;

    private RestHighLevelClient client;

    @Test
    void testAddDocument() throws IOException {
        Hotel hotel = hotelService.getById(61083L);
        HotelDoc hotelDoc = new HotelDoc(hotel);
        // 1.
        IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());
        // 2.
        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
        // 3.
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    void testGetDocumentById() throws IOException {
        // 1.
        GetRequest request = new GetRequest("hotel","61083");
        // 2.
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.
        String json = response.getSourceAsString();

        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        System.out.println(hotelDoc);
    }

    @Test
    void testDeleteDocument() throws IOException {
        // 1.
        DeleteRequest request = new DeleteRequest("hotel", "61083");
        //2.
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testUpdateDocument() throws IOException {
        // 1.准备
        UpdateRequest request = new UpdateRequest("hotel", "61083");
        // 2.
        request.doc(
                "price", "952",
                "starName", "四钻"
        );
        // 3.
        client.update(request, RequestOptions.DEFAULT);
    }

    @Test
    void testBulkRequest() throws IOException {
        // 查询
        List<Hotel> hotels = hotelService.list();

        // 1.
        BulkRequest request = new BulkRequest();
        // 2.
        for (Hotel hotel : hotels) {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            request.add(new IndexRequest("hotel")
                    .id(hotel.getId().toString())
                    .source(JSON.toJSONString(hotelDoc), XContentType.JSON));
        }

        // 3.
        client.bulk(request, RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setUp(){
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.13.10:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }
}
