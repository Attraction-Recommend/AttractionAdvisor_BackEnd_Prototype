package com.example.prototype_recommend_server.recommend.controller.responseDto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendResponse {
    private List<RecommendItem> recommendations;


}