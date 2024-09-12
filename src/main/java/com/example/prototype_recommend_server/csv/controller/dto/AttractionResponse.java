package com.example.prototype_recommend_server.csv.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttractionResponse {
    private String travelId;
    private String poiId;
    private String poiNm;
}

