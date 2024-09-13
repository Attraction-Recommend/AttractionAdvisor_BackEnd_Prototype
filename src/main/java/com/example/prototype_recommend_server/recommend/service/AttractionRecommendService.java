package com.example.prototype_recommend_server.recommend.service;

import com.example.prototype_recommend_server.recommend.RecommendationException;
import com.example.prototype_recommend_server.recommend.controller.requestDto.RecommendRequest;
import com.example.prototype_recommend_server.recommend.controller.responseDto.RecommendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class AttractionRecommendService {
    private final WebClient webClient;

    @Value("${python.api.url}")
    private String pythonApiUrl;

    public Mono<RecommendResponse> getRecommendations(String travelId) {
        RecommendRequest request = new RecommendRequest(travelId);

        return webClient.post()
                .uri(pythonApiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RecommendResponse.class)
                .onErrorMap(this::handleApiError);
    }

    private Throwable handleApiError(Throwable error) {
        return new RecommendationException("Error fetching recommendations", error);
    }
}