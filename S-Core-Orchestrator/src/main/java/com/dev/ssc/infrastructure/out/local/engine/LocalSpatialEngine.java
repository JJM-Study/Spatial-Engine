package com.dev.ssc.infrastructure.out.local.engine;


import com.dev.ssc.application.port.in.dto.SpatialSearchQuery;
import com.dev.ssc.application.port.out.dto.SpatialEngineRequest;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.core.service.SpatialEngineService;
import com.dev.ssc.infrastructure.out.fastapi.FastApiAdapter;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;
import com.dev.ssc.infrastructure.out.local.LocalEngineAdapter;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineRequest;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineResponse;
import com.github.davidmoten.rtree2.RTree;
import com.github.davidmoten.rtree2.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;
import java.util.List;

public class LocalSpatialEngine {

    private static final Logger logger = LogManager.getLogger(LocalSpatialEngine.class);

    private RTree<Integer, Point> localRtree = RTree.star().create();

    public synchronized void insertPlace() {
    }

    public Mono<LocalEngineResponse> get_nearby(LocalEngineRequest request) {
        logger.info("Local Get_Nearby executed");


//        Math.sin(i);


        return Mono.just(new LocalEngineResponse(
                        new LocalEngineResponse.MyLocation(request.lat(), request.lon()),
                List.of(new LocalEngineResponse.Location(1, 3235.5, 1.5, 1.5))));

    }
//
//    public double calculateHaversineMeter(double lat1, double lon1, double lat2, double lon2) {
//
//        final double r = 6371000;
//
//
//        //  # 서울역 기준 반경 약 10km 이내 랜덤 좌표
////        s_lat = 37.5559 + (0.01 * math.sin(i))
////        s_lon = 126.9723 + (0.01 * math.cos(i))
//
//
//        // 위도 (lat) 등은 점이 아니라, 이미 각도라고 볼 수 있다.
//        // 이를 테면, 지구 중심에서 점까지 뻗어가는 거리 그 자체. 지구가 구에 가까우니 2차원면의 점 하나로 전제 시 (실질적 0 + lat1 거리),
//        // x, y축 자체가 하나의 비로 성립하게 되는 거고, radians를 통해서 지구라는 곡면의 최단거리(호)의 곡률을 정의.
//        double phi1 = Math.toRadians(lat1);
//        double phi2 = Math.toRadians(lat2);
//
//        // 여기에서 위도이므로, x축을 중심으로 한 cos으로 호와 호 사이의 거리.
//        double lat = Math.cos(lat2 - lat1); // 위도
//        double lon = lon2 - lon1;  // 경도
//
//
//        Math.sin()
//
//        return
//    }

}
