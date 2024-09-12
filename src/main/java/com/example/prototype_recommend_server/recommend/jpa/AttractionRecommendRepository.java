package com.example.prototype_recommend_server.recommend.jpa;

import com.example.prototype_recommend_server.recommend.entity.AttractionRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRecommendRepository extends JpaRepository<AttractionRecommend, Long> {

}
