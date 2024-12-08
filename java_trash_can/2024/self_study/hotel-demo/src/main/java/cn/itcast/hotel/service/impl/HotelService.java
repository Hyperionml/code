package cn.itcast.hotel.service.impl;

import cn.itcast.hotel.mapper.HotelMapper;
import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public PageResult search(RequestParams params) {
        try {
            // 1.
            SearchRequest request = new SearchRequest("hotel");
            // 2.
            // 2.1.关键字搜索
            buildBasicQuery(params, request);

            // 2.2.分页
            int page = params.getPage();
            int size = params.getSize();
            request.source().from((page - 1) * size).size(size);

            // 2.3.排序
            String location = params.getLocation();
            if(location != null && !location.isEmpty()){
                request.source().sort(SortBuilders.
                        geoDistanceSort("location", new GeoPoint(location))
                        .order(SortOrder.ASC)
                        .unit(DistanceUnit.KILOMETERS));
            }
            // 3.
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, List<String>> filters(RequestParams params) {
        try {
            // 1.
            SearchRequest request = new SearchRequest("hotel");
            // 2.
            // 2.1.query
            buildBasicQuery(params, request);
            // 2.2.设置size
            request.source().size(0);
            buildAggregation(request);
            // 3.发请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 4.解析结果
            Map<String, List<String>> result = new HashMap<>();
            Aggregations aggregations = response.getAggregations();
            // 4.2.根据名称获取品牌结果
            List<String> brandList = getAggByName(aggregations, "brandAgg");
            result.put("brand", brandList);
            // 4.2.根据名称获取品牌结果
            List<String> cityList = getAggByName(aggregations, "cityAgg");
            result.put("city", cityList);
            // 4.2.根据名称获取品牌结果
            List<String> starList = getAggByName(aggregations, "starAgg");
            result.put("starName", starList);

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            // 1.准备request
            DeleteRequest request = new DeleteRequest("hotel", id.toString());
            // 2.发送请求
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void insertById(Long id) {
        try {
            // 0.根据id查酒店数据
            Hotel hotel = getById(id);
            HotelDoc hotelDoc = new HotelDoc(hotel);
            // 1.准备request
            IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());
            // 2.
            request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
            // 3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static List<String> getAggByName(Aggregations aggregations, String aggName) {
        // 4.1.根据聚合名称获取聚合结果
        Terms brandTerms = aggregations.get(aggName);
        // 4.2.获取buckets
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        // 4.3.遍历
        List<String> brandList = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            brandList.add(key);
        }
        return brandList;
    }

    private static void buildAggregation(SearchRequest request) {
        request.source().aggregation(AggregationBuilders
                .terms("brandAgg")
                .field("brand")
                .size(100));

        request.source().aggregation(AggregationBuilders
                .terms("cityAgg")
                .field("city")
                .size(100));

        request.source().aggregation(AggregationBuilders
                .terms("starAgg")
                .field("starName")
                .size(100));
    }

    private static void buildBasicQuery(RequestParams params, SearchRequest request) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 关键字检索
        String key = params.getKey();
        if(key == null || key.isEmpty()){
            boolQuery.must(QueryBuilders.matchAllQuery());
        }else {
            boolQuery.must(QueryBuilders.matchQuery("all", key));
        }
        // 城市条件过滤
        if(params.getCity() != null && !params.getCity().isEmpty()){
            boolQuery.filter(QueryBuilders.termQuery("city", params.getCity()));
        }
        // 品牌条件过滤
        if(params.getBrand() != null && !params.getBrand().isEmpty()){
            boolQuery.filter(QueryBuilders.termQuery("city", params.getBrand()));
        }
        if(params.getStarName() != null && !params.getStarName().isEmpty()){
            boolQuery.filter(QueryBuilders.termQuery("city", params.getStarName()));
        }
        if(params.getMinPrice() != null && params.getMaxPrice() != null){
            boolQuery.filter(QueryBuilders.
                    rangeQuery("price").gte(params.getMinPrice()).lte(params.getMaxPrice()));
        }

        // 2.算分控制
        FunctionScoreQueryBuilder functionScoreQueryBuilder =
                QueryBuilders.functionScoreQuery(
                        // 原始查询，相关性算分
                        boolQuery,
                        // function score的数组
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                                // 其中一个元素
                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                        // 过滤条件
                                        QueryBuilders.termQuery("isAD", true),
                                        // 算分函数
                                        ScoreFunctionBuilders.weightFactorFunction(10)
                                )
                        });

        request.source().query(functionScoreQueryBuilder);
    }

    private static PageResult handleResponse(SearchResponse response) {
        // 4.
        SearchHits searchHits = response.getHits();
        long value = searchHits.getTotalHits().value;
        System.out.println("共搜索到" + value + "条数据");
        SearchHit[] hits = searchHits.getHits();

        List<HotelDoc> hotels = new ArrayList<>();
        for (SearchHit hit : hits) {
            // 读取
            String json = hit.getSourceAsString();
            // 反序列化
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            // 获取排序值
            Object[] sortValues = hit.getSortValues();
            if(sortValues.length > 0){
                Object sortValue = sortValues[0];
                hotelDoc.setDistance(sortValue);
            }
            hotels.add(hotelDoc);
        }

        //返回数据
        return new PageResult(value, hotels);
    }
}
