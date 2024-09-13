package com.example.prototype_recommend_server.recommend.jpa;

import com.example.prototype_recommend_server.attraction.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRecommendRepository extends JpaRepository<Attraction, Long> {

}
