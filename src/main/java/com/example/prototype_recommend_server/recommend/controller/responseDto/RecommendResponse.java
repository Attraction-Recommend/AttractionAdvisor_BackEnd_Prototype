package com.example.prototype_recommend_server.recommend.controller.responseDto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendResponse {
    private List<RecommendItem> recommendations;


}