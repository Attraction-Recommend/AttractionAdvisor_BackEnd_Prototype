package com.example.prototype_recommend_server.recommend.service;

import com.example.prototype_recommend_server.recommend.controller.requestDto.RecommendRequest;
import com.example.prototype_recommend_server.recommend.controller.responseDto.RecommendResponse;
import com.example.prototype_recommend_server.recommend.entity.AttractionRecommend;
import com.example.prototype_recommend_server.recommend.jpa.AttractionRecommendRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAttractionAiService {


        private final WebClient webClient;
        private final AttractionRecommendRepository attractionRecommendRepository;

        @Value("${python.api.url}")
        private String pythonApiUrl;

        @Value("${python.api.update-model-path}")
        private String updateModelPath;

        public Mono<RecommendResponse> getRecommendations(RecommendRequest request) {
            return webClient.post()
                    .uri(pythonApiUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(request), RecommendRequest.class)
                    .retrieve()
                    .bodyToMono(RecommendResponse.class)
                    .onErrorMap(this::handleApiError);
        }

        @Transactional(readOnly = true)
        public Mono<String> updateModel() {
            List<AttractionRecommend> attractions = attractionRecommendRepository.findAll();
            Map<String, Object> requestBody = Map.of("attractions", mapAttractionsToDto(attractions));

            return webClient.post()
                    .uri(pythonApiUrl + updateModelPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(requestBody), Map.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSubscribe(subscription -> log.info("Sending request to update model with {} attractions", attractions.size()))
                    .doOnSuccess(response -> log.info("Model update successful"))
                    .onErrorMap(this::handleApiError);
        }

        private List<Map<String, Object>> mapAttractionsToDto(List<AttractionRecommend> attractions) {
            return attractions.stream()
                    .map(attraction -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("TRAVEL_ID", attraction.getTravelId());
                        map.put("POI_ID", attraction.getPoiId());
                        map.put("POI_NM", attraction.getPoiNm());
                        return map;
                    })
                    .collect(Collectors.toList());
        }

        private Throwable handleApiError(Throwable error) {
            log.error("API error occurred", error);
            return new IllegalArgumentException("Error in attraction service", error);
        }

}
