package com.example.prototype_recommend_server.attraction.service;


import com.example.prototype_recommend_server.attraction.jpa.AttractionRepository;
import com.example.prototype_recommend_server.attraction.entity.Attraction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
                .orElseThrow(() -> new RuntimeException("Attraction not found"));
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

    @Transactional
    public String updateModel() {
        List<Attraction> attractions = attractionRepository.findAll();
        Map<String, Object> requestBody = new HashMap<>();

        List<Map<String, Object>> attractionDataList = attractions.stream()
                .map(attraction -> {
                    Map<String, Object> attractionMap = new HashMap<>();
//                    attractionMap.put("id", attraction.getId());
                    attractionMap.put("TRAVEL_ID", attraction.getTravelId());
                    attractionMap.put("POI_ID", attraction.getPoiId());
                    attractionMap.put("POI_NM", attraction.getPoiNm());
                    return attractionMap;
                })
                .collect(Collectors.toList());

        requestBody.put("attractions", attractionDataList);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String fullUrl = PYTHON_API_URL + "/update_model";
        log.info("Sending request to: {}", fullUrl);
        log.info("Request body size: {} attractions", attractionDataList.size());

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(fullUrl, entity, String.class);
            log.info("Response received with status: {}", response.getStatusCode());
            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred while updating model", e);
            throw new RuntimeException("Error occurred while updating model: " + e.getMessage(), e);
        }
    }
}