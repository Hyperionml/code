package cn.itcast.hotel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static cn.itcast.hotel.constant.HotelConstants.MAPPING_TEMPLATE;

@SpringBootTest
public class HotelIndexTest {
    private RestHighLevelClient client;

    @Test
    void testInit() {
        System.out.println(client);
    }

    @Test
    void createHotelIndex() throws IOException {
        // 1.创建request对象
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        // 2.准备请求的dsl语句
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteHotelIndex() throws IOException {
        // 1.创建request对象
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        // 2.
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testExistsHotelIndex() throws IOException {
        // 1.创建request对象
        GetIndexRequest request = new GetIndexRequest("hotel");
        // 2.
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        // 3.
        System.err.println(exists ? "索引库已经存在" : "索引库不存在");
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
