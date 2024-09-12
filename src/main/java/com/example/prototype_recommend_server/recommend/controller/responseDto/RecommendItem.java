package com.example.prototype_recommend_server.recommend.controller.responseDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class RecommendItem {
    private String poi_id;
    private float score;

}