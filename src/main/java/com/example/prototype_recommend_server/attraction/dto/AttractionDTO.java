package com.example.prototype_recommend_server.attraction.dto;

import com.example.prototype_recommend_server.attraction.entity.Attraction;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AttractionDTO {
    private Long id;
    private String travelId;
    private String poiId;
    private String poiNm;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AttractionDTO fromAttraction(Attraction attraction) {
        AttractionDTO dto = new AttractionDTO();
        dto.setId(attraction.getId());
        dto.setTravelId(attraction.getTravelId());
        dto.setPoiId(attraction.getPoiId());
        dto.setPoiNm(attraction.getPoiNm());
        dto.setCreatedAt(attraction.getCreatedAt());
        dto.setUpdatedAt(attraction.getUpdatedAt());
        return dto;
    }

    public Attraction toAttraction() {
        Attraction attraction = new Attraction();
        attraction.setId(this.id);
        attraction.setTravelId(this.travelId);
        attraction.setPoiId(this.poiId);
        attraction.setPoiNm(this.poiNm);
        // Note: createdAt and updatedAt are managed by JPA
        return attraction;
    }
}