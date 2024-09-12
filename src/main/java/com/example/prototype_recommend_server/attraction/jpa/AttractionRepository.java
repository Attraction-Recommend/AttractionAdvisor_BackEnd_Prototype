package com.example.prototype_recommend_server.attraction.jpa;

import com.example.prototype_recommend_server.attraction.entity.Attraction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByTravelId(String travelId);

    List<Attraction> findByPoiId(String poiId);

}