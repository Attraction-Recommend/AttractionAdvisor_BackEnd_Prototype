package com.example.prototype_recommend_server.recommend;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecommendationController {

    private final String PYTHON_API_URL = "http://localhost:9999/recommend";

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/recommend")
    public ResponseEntity<?> getRecommendations(@RequestBody RecommendRequest request) {
        try {
            // Python API에 요청을 보냄
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RecommendRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<RecommendResponse> response = restTemplate.postForEntity(
                    PYTHON_API_URL,
                    entity,
                    RecommendResponse.class
            );

            // Python API의 응답을 그대로 클라이언트에게 전달
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching recommendations: " + e.getMessage());
        }
    }
}

class RecommendRequest {
    private String travel_id;

    // Getter and Setter
    public String getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(String travel_id) {
        this.travel_id = travel_id;
    }
}

class RecommendResponse {
    private List<RecommendItem> recommendations;

    // Getter and Setter
    public List<RecommendItem> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendItem> recommendations) {
        this.recommendations = recommendations;
    }
}

class RecommendItem {
    private String poi_id;
    private float score;

    // Getters and Setters
    public String getPoi_id() {
        return poi_id;
    }

    public void setPoi_id(String poi_id) {
        this.poi_id = poi_id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}