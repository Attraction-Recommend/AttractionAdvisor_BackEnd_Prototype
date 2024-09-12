package com.example.prototype_recommend_server.recommend.controller;


import com.example.prototype_recommend_server.recommend.controller.requestDto.RecommendRequest;
import com.example.prototype_recommend_server.recommend.controller.responseDto.RecommendResponse;
import com.example.prototype_recommend_server.recommend.service.AttractionRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecommendationController {
    private final AttractionRecommendService recommendationService;

    @PostMapping("/recommend")
    public Mono<ResponseEntity<RecommendResponse>> getRecommendations(@RequestBody RecommendRequest request) {
        return recommendationService.getRecommendations(request)
                .map(ResponseEntity::ok)
                .onErrorResume(this::handleError);
    }

    private Mono<ResponseEntity<RecommendResponse>> handleError(Throwable error) {
        return Mono.just(ResponseEntity.internalServerError()
                .body(new RecommendResponse())); // 에러 시 빈 응답 반환
    }
}
