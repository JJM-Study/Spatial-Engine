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

    // 2026/09/29 추가
    public SpatialEnginePort localSpatialNodes(LocalSpatialEngine localSpatialEngine, @Qualifier("forSpatialNodes") List<NodeData> forSpatialNodes) {
//        return new LocalEngineAdapter(forSpatialNodes);
        return new LocalEngineAdapter(localSpatialEngine, forSpatialNodes);
    }
//
//    public SpatialEnginePort fastSpatialNodes(WebClient.Builder webClientBuilder, @Value("${external.api.fastapi.url}") String baseUrl, @Qualifier("forSpatialNodes") List<NodeData> forSpatialNodes){
//
//        return new FastApiAdapter(webClientBuilder, baseUrl, forSpatialNodes);
//    }
//////        return new LocalEngineAdapter(forSpatialNodes);
////        return new LocalEngineAdapter(webClientBuilder, baseUrl,forSpatialNodes);
////    }

}
