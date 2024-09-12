package com.example.prototype_recommend_server.recommend.controller.responseDto;

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