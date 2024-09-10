package com.example.prototype_recommend_server.service;

import com.example.prototype_recommend_server.jpa.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttractionRecommendService {
    private final AttractionRepository attractionRepository;

//    public List<Attraction>  getTop3(){
//        attractionRepository.
//
//    }
}
