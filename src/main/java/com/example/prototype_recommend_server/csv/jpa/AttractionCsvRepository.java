package com.example.prototype_recommend_server.csv.jpa;


import com.example.prototype_recommend_server.csv.entity.Attraction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttractionCsvRepository extends JpaRepository<Long, Attraction> {
    List<Attraction> saveAll(List<Attraction> attractions);

}
