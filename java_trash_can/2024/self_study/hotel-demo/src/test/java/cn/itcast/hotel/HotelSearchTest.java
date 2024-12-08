package cn.itcast.hotel;

import cn.itcast.hotel.pojo.HotelDoc;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static cn.itcast.hotel.constant.HotelConstants.MAPPING_TEMPLATE;

@SpringBootTest
public class HotelSearchTest {
    private RestHighLevelClient client;

    @Test
    void testMatchAll() throws IOException {
        // 1.
        SearchRequest request = new SearchRequest("hotel");
        // 2.
        request.source().query(QueryBuilders.matchAllQuery());
        // 3.
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.
        handleResponse(response);

        //System.out.println(response);
    }

    @Test
    void testMatch() throws IOException {
        // 1.
        SearchRequest request = new SearchRequest("hotel");
        // 2.
        request.source().query(QueryBuilders.matchQuery("all", "如家"));
        // 3.
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    @Test
    void testBool() throws IOException {
        // 1.
        SearchRequest request = new SearchRequest("hotel");
        // 2.
        // 2.1.
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 2.2.
        boolQuery.must(QueryBuilders.termQuery("city", "上海"));
        // 2.3.
        boolQuery.filter(QueryBuilders.rangeQuery("price").lte(250));

        request.source().query(boolQuery);
        // 3.
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    @Test
    void testPageAndSort() throws IOException {
        int page = 2, size = 5;
        // 1.
        SearchRequest request = new SearchRequest("hotel");
        // 2.
        // 2.1.
        request.source().query(QueryBuilders.matchAllQuery());
        // 2.2.
        request.source().sort("price", SortOrder.ASC);
        // 2.3.
        request.source().from((page - 1) * size).size(size);
        // 3.
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    @Test
    void testHighlight() throws IOException {
        // 1.
        SearchRequest request = new SearchRequest("hotel");
        // 2.
        // 2.1.
        request.source().query(QueryBuilders.matchQuery("name", "如家"));
        // 2.2
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));
        // 3.
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        handleResponse(response);
    }

    private static void handleResponse(SearchResponse response) {
        // 4.
        SearchHits searchHits = response.getHits();
        long value = searchHits.getTotalHits().value;
        System.out.println("共搜索到" + value + "条数据");
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            // 读取
            String json = hit.getSourceAsString();
            // 反序列化
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            // 获取高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(!CollectionUtils.isEmpty(highlightFields)){
                HighlightField highlightField = highlightFields.get("name");
                if(highlightField != null){
                    // 获取高亮值
                    String name = highlightField.getFragments()[0].string();
                    // 覆盖非高亮结果
                    hotelDoc.setName(name);
                }
            }
            System.out.println("hotelDoc: " + hotelDoc);
        }

        //System.out.println(response);
    }

    @Test
    void testAggregation() throws IOException {
        // 1.
        SearchRequest request = new SearchRequest("hotel");
        // 2.
        // 2.1.设置size
        request.source().size(0);
        request.source().aggregation(AggregationBuilders
                .terms("brandAgg")
                .field("brand")
                .size(10));
        // 3.发请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.解析结果
        Aggregations aggregations = response.getAggregations();
        // 4.1.根据聚合名称获取聚合结果
        Terms brandTerms = aggregations.get("brandAgg");
        // 4.2.获取buckets
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        // 4.3.遍历
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            System.out.println("key = " + key);
        }
    }

    @Test
    void testSuggest() throws IOException {
        //1.准备request
        SearchRequest request = new SearchRequest("hotel");
        //2.
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions",
                SuggestBuilders.completionSuggestion("suggestion")
                        .prefix("hz")
                        .skipDuplicates(true)
                        .size(10)
        ));
        //3.
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4.解析结果
        Suggest suggest = response.getSuggest();
        // 4.1.
        CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
        // 4.2.
        List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
        // 4.3.
        for (CompletionSuggestion.Entry.Option option : options) {
            String text = option.getText().toString();
            System.out.println(text);
        }
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
