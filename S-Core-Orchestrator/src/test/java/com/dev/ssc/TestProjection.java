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



//    SpatialResult
    @Test
    public void Calculator() {

        // 기존 degree 방식 테스트용
        localSpatialEngine.get_nearby(new LocalEngineRequest(lon, lat, k));
        final double R = 6371000;

//        logger.info("Check CSV : {}", nodeData);

        // N개 (CSV 기준 약 2만 4천)의 순환을 통해 lon, lat 전부 meter로 환산 기능 필요.

        for (int i; i < nodeData.size(); i++) {
            
        }

        double lonToMeter = R * Math.toRadians(lon);
        double latToMeter = R * Math.toRadians(lat);

        logger.info("lonToMeter : {} \n latToMeter : {}", lonToMeter, latToMeter);

//        // projection 테스트용
//        localSpatialEngine.calculateHaversineMeter();

    }



}
