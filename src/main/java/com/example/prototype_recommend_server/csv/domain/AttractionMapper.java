package com.example.prototype_recommend_server.csv.domain;

import com.example.prototype_recommend_server.csv.entity.Attraction;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AttractionMapper {

    public List<Attraction> mapToAttractions(List<String[]> rawData) {
        return rawData.stream()
                .map(this::createAttractionFromCsvData)
                .collect(Collectors.toList());
    }

    private Attraction createAttractionFromCsvData(String[] data) {
        if (data.length < 3) {
            log.warn("Insufficient data in CSV row. Expected at least 3 columns, got {}", data.length);
            return null;
        }

        Attraction attraction = new Attraction();
        attraction.setTravelId(data[0]);
        attraction.setPoiId(data[1]);
        attraction.setPoiNm(data[2]);

        log.debug("Created AttractionRecommend: travelId={}, poiId={}, poiNm={}",
                attraction.getTravelId(), attraction.getPoiId(), attraction.getPoiNm());

        return attraction;
    }
}