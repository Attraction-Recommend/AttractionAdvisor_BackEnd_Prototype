package com.example.prototype_recommend_server.recommend.controller.responseDto;

import java.util.List;

public class RecommendResponse {
    private List<RecommendItem> recommendations;

    // Getter and Setter
    public List<RecommendItem> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendItem> recommendations) {
        this.recommendations = recommendations;
    }
}