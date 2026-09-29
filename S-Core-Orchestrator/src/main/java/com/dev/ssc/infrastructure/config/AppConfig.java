package com.dev.ssc.infrastructure.config;

import com.dev.ssc.application.port.in.SpatialEngineUseCase;
import com.dev.ssc.application.port.out.SpatialEnginePort;
import com.dev.ssc.core.dto.NodeData;
import com.dev.ssc.core.service.SpatialEngineService;
import com.dev.ssc.infrastructure.file.NodeDataCsvLoaders;
import com.dev.ssc.infrastructure.out.fastapi.FastApiAdapter;
import com.dev.ssc.infrastructure.out.local.LocalEngineAdapter;
import com.dev.ssc.infrastructure.out.local.engine.LocalSpatialEngine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class AppConfig {

//    public LocalSpatialEngine forSpatialNodes(@Qualifier("forSpatialNodes")
    public SpatialEnginePort localSpatialNodes(LocalSpatialEngine localSpatialEngine, @Qualifier("forSpatialNodes") List<NodeData> forSpatialNodes) {
//        return new LocalEngineAdapter(forSpatialNodes);
        return new LocalEngineAdapter(localSpatialEngine, forSpatialNodes);
    }

//    public LocalSpatialEngine forSpatialNodes(WebClient.Builder webClientBuilder, @Value("${external.api.fastapi.url}") String baseUrl, @Qualifier("forSpatialNodes") List<NodeData> forSpatialNodes) {
////        return new LocalEngineAdapter(forSpatialNodes);
//        return new LocalEngineAdapter(webClientBuilder, baseUrl,forSpatialNodes);
//    }

//
//    @Configuration
//    public class AppConfig {
//
//        // 1. [진짜 엔진] CSV 데이터를 넣어서 인메모리 R-Tree 엔진 본체를 만듦
//        @Bean
//        public LocalSpatialEngine localSpatialEngine(List<NodeData> forSpatialNodes) {
//            return new LocalSpatialEngine(forSpatialNodes);
//        }
//
//        // 2. [어댑터] 위에서 만든 엔진을 껍데기(어댑터)로 감싸서 'SpatialEnginePort' 표준 규격으로 등록
//        @Bean
//        public SpatialEnginePort localEngineAdapter(LocalSpatialEngine localSpatialEngine) {
//            return new LocalEngineAdapter(localSpatialEngine); // 어댑터 리턴!
//        }
//    }

}
