package com.example.prototype_recommend_server.recommend.service;

import com.example.prototype_recommend_server.attraction.jpa.AttractionRepository;
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

    public Mono<RecommendResponse> getRecommendations(RecommendRequest request) {
        return webClient.post()
                .uri(pythonApiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), RecommendRequest.class)
                .retrieve()
                .bodyToMono(RecommendResponse.class)
                .onErrorMap(this::handleApiError);
    }

    private Throwable handleApiError(Throwable error) {
        // 여기서 특정 예외 유형에 따라 커스텀 예외로 변환할 수 있습니다.
        return new RecommendationException("Error fetching recommendations", error);
    }
}
