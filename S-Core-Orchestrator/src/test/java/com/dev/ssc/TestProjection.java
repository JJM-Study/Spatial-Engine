package com.dev.ssc;

import com.dev.ssc.core.dto.NodeData;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.file.CsvNodeLoader;
import com.dev.ssc.infrastructure.out.local.engine.LocalSpatialEngine;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineRequest;
import com.github.davidmoten.rtree2.Entry;
import com.github.davidmoten.rtree2.RTree;
import com.github.davidmoten.rtree2.geometry.Geometries;
import com.github.davidmoten.rtree2.geometry.Geometry;

import com.github.davidmoten.rtree2.geometry.Point;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.IntStream;

@SpringBootTest
public class TestProjection {

    @Autowired
    LocalSpatialEngine localSpatialEngine;


    // projection 테스트용
    private List<NodeData> nodeData;

    private TestProjection(
            @Qualifier("forSpatialNodes") List<NodeData> nodeData) {
        this.nodeData = nodeData;

    }

    Logger logger = LoggerFactory.getLogger(TestProjection.class);


    RTree<LocalSpatialEngine.NodeData, Point> rtree = RTree.star().create();
    double lon = 126.9842;
    double lat = 37.5615;
    int k = 100;

    record NodeDataToMeter(int nodeId, List<String> externalId, double lon, double lat) {
    }


    @Test
    public void Calculator() {


//        logger.info("nodeData lists: {}", nodeData);

        // 기존 degree 방식 테스트용
        localSpatialEngine.get_nearby(new LocalEngineRequest(lon, lat, k));
        final double R = 6371000;

//        logger.info("Check CSV : {}", nodeData);

        double sLat = Math.cos(Math.toRadians(37.56));// 기준위도 . 서울특별시 중심.
        NodeDataToMeter nodeDataToMeter = null;


        // N개 (CSV 기준 약 2만 4천)의 순환을 통해 lon, lat 전부 meter로 환산 기능 필요.

        RTree<NodeDataToMeter, Point> rtree = RTree.star().create();

        RTree<NodeData, Point> rtree2 = RTree.star().create();
        for (int i = 0; i < nodeData.size(); i++) {
            NodeData data = nodeData.get(i);


            nodeDataToMeter = new NodeDataToMeter(
                    i,
                    data.metaNodes().stream()
                            .map(NodeData.MetaNode::shopId).toList(),
                    R * Math.toRadians(data.lon()) * sLat,
                    R * Math.toRadians(data.lat())
            );

            Point point = Geometries.point(nodeDataToMeter.lon, nodeDataToMeter.lat);

            rtree = rtree.add(nodeDataToMeter, point);


            rtree2 = rtree2.add(data, Geometries.point(data.lon(), data.lat()));
        }

        logger.info("rtree size : {}", rtree.size());
        logger.info("rtree2 size : {}", rtree2.size());


        double myLonToMeter = R * Math.toRadians(lon) * sLat;
        double myLatToMeter = R * Math.toRadians(lat);

        logger.info("my Meter Lon : {}", myLonToMeter);
        logger.info("my Meter Lat : {}", myLatToMeter);


        Point myPoint = Geometries.point(myLonToMeter, myLatToMeter);

        Iterable<Entry<NodeDataToMeter, Point>> M_nearest = rtree.nearest(myPoint, Double.MAX_VALUE, 100);

        Point myPoint2 = Geometries.point(lon, lat);

        Iterable<Entry<NodeData, Point>> D_nearest = rtree2.nearest(myPoint2, Double.MAX_VALUE, 100);

        logger.info("Meter nearest Entries : {}", M_nearest);

        logger.info("Rtree2 (Degree to Meter) nearest Entries : {}", D_nearest);


        record CheckDegreeToMeter(
                int nodeId,

                List<String> degreeShop,
                List<String> meterShopId,
                double degreeLon,
                double degreeLat,
                double meterLon,
                double meterLat) {
        }

        Iterator<Entry<NodeData, Point>> degreeIterator = D_nearest.iterator();
        Iterator<Entry<NodeDataToMeter, Point>> meterIterator = M_nearest.iterator();


        int i = 0;

        // 어차피 degree와 meter의 nearest 순서대로 짝지어져 queue되어 있을 것이므로 그냥 인출하자.


        //=== nearest TOP 100 ===

        record misMatch(List<String> misMatchDegree, List<String> misMatchMeter) {
        }

        Map<Integer, misMatch> misMatchMap = new HashMap<>();
//        int misCount;

        while (degreeIterator.hasNext() && meterIterator.hasNext()) {

            NodeData degreeData = degreeIterator.next().value();
            NodeDataToMeter meterData = meterIterator.next().value();

            CheckDegreeToMeter check = new CheckDegreeToMeter(
                    i++,
                    degreeData.metaNodes().stream().map(NodeData.MetaNode::shopId).toList(),
                    meterData.externalId(),
                    R * Math.toRadians(degreeData.lon()) * sLat,
                    R * Math.toRadians(degreeData.lat()),
                    meterData.lon,
                    meterData.lat
            );

            logger.info("degree Data - degree_shopId {} : rank {}, lon {}, lat {}", check.degreeShop, i, check.degreeLon, check.degreeLat); // 중간에 record 추가해서 shopId도 나오게 하자.
            logger.info("meter Data  - meter_shopId {} : rank {}, lon {}, lat {}", check.meterShopId(), i, check.meterLon, check.meterLat);


            if (!check.meterShopId.equals(check.degreeShop)) {
                misMatchMap.put(i, new misMatch(check.degreeShop, check.meterShopId));
            }

            // 단순 오차가 아닌 상가의 순위가 얼마나 달라졌느냐.
            // asOrderedList에서 자체적으로 받은 순서대로 오름차순
            // sort(Collections.sort(list, this.comparator);) 되는 거 확인함. 그냥 return 순차적으로 출력하면 됨.


            // 2026/10/13 식 제대로 됐는지 재차 확인 필요. => 완료
            logger.info("Margin - degree/meter shopId-{}, Lon {} Lat {} ", check.meterShopId(), check.degreeLon / check.meterLon, check.degreeLat / check.meterLat);

        }

        for (Map.Entry<Integer, misMatch> map : misMatchMap.entrySet()) {
            logger.info("misMatching list = NodeId : {}, Degree Id : {} ≠ Meter Id : {}", map.getKey(), map.getValue().misMatchDegree, map.getValue().misMatchMeter);

        }

        logger.info("mismatching count : {}", misMatchMap.size());

        record HaverDistance(List<String> externalId, double lon, double lat, double distance) {}

        double result;

        List<HaverDistance> haverDistanceList = new ArrayList<>();


        for(int j = 0; j < nodeData.size(); j++) {

            result = localSpatialEngine.calculateHaversineMeter(lon, lat, nodeData.get(i).lon(), nodeData.get(i).lat());

            HaverDistance item = new HaverDistance(
                    nodeData.get(i).metaNodes().stream().map(NodeData.MetaNode::shopId).toList(),
                    nodeData.get(i).lon(),
                    nodeData.get(i).lat(),
                    result
            );

            haverDistanceList.add(item);

        }

    }

}