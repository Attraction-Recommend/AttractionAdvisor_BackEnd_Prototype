package com.example.prototype_recommend_server.controller;

import com.example.prototype_recommend_server.domain.attraction.Attraction;
import com.example.prototype_recommend_server.dto.AttractionDTO;
import com.example.prototype_recommend_server.service.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class AttractionController {
    private final AttractionService attractionService;

    @GetMapping("/{id}")
    public ResponseEntity<AttractionDTO> getAttraction(@PathVariable Long id) {
        Attraction attraction = attractionService.getAttractionById(id);
        return ResponseEntity.ok(AttractionDTO.fromAttraction(attraction));
    }

    @PostMapping("/{travelId}")
    public Long createAttraction(@PathVariable("travelId") String travelId, @RequestBody CreateRequest createRequest) {
        Long createId = attractionService.createAttraction(travelId, createRequest.getPoiId(),createRequest.getPoiNm());
        return createId;
    }

    @PostMapping("/updateModel")
    public ResponseEntity<String> updateModel() {
        try {
            String result = attractionService.updateModel();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating model", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating model: " + e.getMessage());
        }
    }
//
//    @GetMapping("/nearby")
//    public ResponseEntity<List<AttractionDTO>> getNearbyAttractions(
//            @RequestParam double latitude,
//            @RequestParam double longitude,
//            @RequestParam(defaultValue = "10000") double distance) {
//        List<Attraction> attractions = attractionService.getNearbyAttractions(latitude, longitude, distance);
//        List<AttractionDTO> attractionDTOs = attractions.stream()
//                .map(AttractionDTO::fromAttraction)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(attractionDTOs);
//    }
//
//    @GetMapping("/type/{visitAreaTypeCd}")
//    public ResponseEntity<List<AttractionDTO>> getAttractionsByType(@PathVariable String visitAreaTypeCd) {
//        List<Attraction> attractions = attractionService.getAttractionsByType(visitAreaTypeCd);
//        List<AttractionDTO> attractionDTOs = attractions.stream()
//                .map(AttractionDTO::fromAttraction)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(attractionDTOs);
//    }
//
//    @GetMapping("/region/{sggCd}")
//    public ResponseEntity<List<AttractionDTO>> getAttractionsByRegion(@PathVariable String sggCd) {
//        List<Attraction> attractions = attractionService.getAttractionsByRegion(sggCd);
//        List<AttractionDTO> attractionDTOs = attractions.stream()
//                .map(AttractionDTO::fromAttraction)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(attractionDTOs);
//    }

    @PostMapping
    public ResponseEntity<AttractionDTO> createAttraction(@RequestBody AttractionDTO attractionDTO) {
        Attraction attraction = attractionDTO.toAttraction();
        Attraction savedAttraction = attractionService.saveAttraction(attraction);
        return ResponseEntity.ok(AttractionDTO.fromAttraction(savedAttraction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable Long id) {
        attractionService.deleteAttraction(id);
        return ResponseEntity.noContent().build();
    }
}