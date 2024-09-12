package com.example.prototype_recommend_server.attraction.service;


import com.example.prototype_recommend_server.attraction.entity.Attraction;
import com.example.prototype_recommend_server.attraction.jpa.AttractionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttractionService {
    private final String PYTHON_API_URL = "http://localhost:9999";
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(AttractionService.class);



    private final AttractionRepository attractionRepository;

    @Transactional(readOnly = true)
    public Attraction getAttractionById(Long id) {
        return attractionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AttractionRecommend not found"));
    }

    @Transactional(readOnly = true)
    public List<Attraction> getAttractionsByTravelId(String travelId) {
        return attractionRepository.findByTravelId(travelId);
    }


    @Transactional(readOnly = true)
    public List<Attraction> getAttractionsByPoiId(String poiId) {
        return attractionRepository.findByPoiId(poiId);
    }

    @Transactional
    public Attraction saveAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }



    @Transactional
    public void deleteAttraction(Long id) {
        attractionRepository.deleteById(id);
    }



    public Long createAttraction(String travelId, String poiId, String poiNm) {
        Attraction attraction = new Attraction();
        attraction.setPoiId(poiId);
        attraction.setPoiNm(poiNm);
        attraction.setTravelId(travelId);
        Attraction createdAttraction = attractionRepository.save(attraction);
        return createdAttraction.getId();
    }

}