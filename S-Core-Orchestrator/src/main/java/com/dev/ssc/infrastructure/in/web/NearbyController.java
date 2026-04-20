package com.dev.ssc.infrastructure.in.web;

import com.dev.ssc.core.service.SpatialEngineService;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class NearbyController {
    private final SpatialEngineService spatialEngineService;

    NearbyController(SpatialEngineService spatialEngineService) {
        this.spatialEngineService = spatialEngineService;
    }

   @PostMapping()
   public Mono<NearbyResponse> findNearby(@RequestBody SearchRequest request) {

        spatialEngineService.findNearby(request);

       return
   }
}
