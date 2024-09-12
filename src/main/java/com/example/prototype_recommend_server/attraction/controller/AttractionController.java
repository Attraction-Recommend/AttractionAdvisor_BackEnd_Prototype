package com.example.prototype_recommend_server.attraction.controller;

import com.example.prototype_recommend_server.attraction.dto.AttractionDTO;
import com.example.prototype_recommend_server.attraction.entity.Attraction;
import com.example.prototype_recommend_server.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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