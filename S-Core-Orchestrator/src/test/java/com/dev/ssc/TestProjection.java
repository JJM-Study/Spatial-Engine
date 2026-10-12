package com.dev.ssc;

import com.dev.ssc.core.dto.NodeData;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.file.CsvNodeLoader;
import com.dev.ssc.infrastructure.out.local.engine.LocalSpatialEngine;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineRequest;
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

import java.util.List;
import java.util.logging.LogManager;
import java.util.stream.IntStream;

@SpringBootTest
public class TestProjection {

    @Mock
    LocalSpatialEngine localSpatialEngine;


    // projection 테스트용
    private List<NodeData> nodeData;

    private TestProjection(@Qualifier("forSpatialNodes") List<NodeData> nodeData) {
        this.nodeData = nodeData;
    }

    Logger logger = LoggerFactory.getLogger(TestProjection.class);

    private record TestNode(int nodeId, double lon, double lat) {};

    RTree<LocalSpatialEngine.NodeData, Point> rtree = RTree.star().create();
    double lon = 126.9842;
    double lat = 37.5615;
    int k = 3;

    record NodeDataToMeter(int nodeId, List<String> externalId, double lon, double lat) {}

//    SpatialResult
    @Test
    public void Calculator() {


//        logger.info("nodeData lists: {}", nodeData);

        // 기존 degree 방식 테스트용
        localSpatialEngine.get_nearby(new LocalEngineRequest(lon, lat, k));
        final double R = 6371000;

//        logger.info("Check CSV : {}", nodeData);

        double sLat = Math.cos(Math.toRadians(37.56));// 기준위도 . 서울특별시 중심.
        List<NodeDataToMeter> nodeDataToMeter = null;



        // N개 (CSV 기준 약 2만 4천)의 순환을 통해 lon, lat 전부 meter로 환산 기능 필요.

//        for (int i = 0; i<nodeData.size(); i++) {
            //i, nodeData.get(i).metaNodes().stream().map(NodeData.MetaNode::shopId).toList(), nodeData.get(i).lon(), nodeData.get(i).lat()
//            nodeDataToMeter = java.util.stream.IntStream.range(0, nodeData.size())
//        }

        List<NodeDataToMeter> nodeDataToMeters = IntStream.range(0, nodeData.size())
                .mapToObj(i -> {
                NodeData node = nodeData.get(i);

                return new NodeDataToMeter(
                        i,
                        node.metaNodes().stream().map(NodeData.MetaNode::shopId).toList(),
                        R * Math.toRadians(node.lon())* Math.cos(Math.toRadians(node.lat())),
                        R * Math.toRadians(node.lat())
                );

            }).toList();
            // 지구 둘레 기준 위도 1도 ≒ 88.1km 정도.

//        logger.info("lonToMeter : {} \n latToMeter : {}", lonToMeter, latToMeter);
        logger.info("nodeDataToMeter Result: {}", nodeDataToMeters);


//        // projection 테스트용
//        localSpatialEngine.calculateHaversineMeter();

    }



}
