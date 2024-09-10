package com.example.prototype_recommend_server.jpa;

import com.example.prototype_recommend_server.domain.attraction.Attraction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByTravelId(String travelId);

    List<Attraction> findByPoiId(String poiId);

}