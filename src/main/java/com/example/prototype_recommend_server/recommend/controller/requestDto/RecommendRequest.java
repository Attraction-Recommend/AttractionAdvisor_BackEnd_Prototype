package com.example.prototype_recommend_server.recommend.controller.requestDto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RecommendRequest {
    private String travel_id;

    // 기본 생성자 추가
    public RecommendRequest() {
    }

    // JsonCreator 어노테이션을 사용한 생성자
    @JsonCreator
    public RecommendRequest(@JsonProperty("travel_id") String travel_id) {
        this.travel_id = travel_id;
    }

    // Getter and Setter
    public String getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(String travel_id) {
        this.travel_id = travel_id;
    }
}