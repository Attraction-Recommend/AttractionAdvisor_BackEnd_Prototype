package com.example.prototype_recommend_server.service;


import com.example.prototype_recommend_server.domain.attraction.Attraction;
import com.example.prototype_recommend_server.jpa.AttractionRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CsvImportService {
    private static final Logger logger = LoggerFactory.getLogger(CsvImportService.class);
    private final AttractionRepository attractionRepository;

    @Transactional
    public List<Attraction> importCsvData() {
        List<Attraction> attractions = new ArrayList<>();
        Resource resource = new ClassPathResource("static/info.csv");

        if (!resource.exists()) {
            logger.error("CSV file not found: static/info.csv");
            throw new RuntimeException("CSV file not found: static/info.csv");
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                Attraction attraction = createAttractionFromCsvData(data);
                attractions.add(attraction);
            }
        } catch (Exception e) {
            logger.error("Failed to import CSV data", e);
            throw new RuntimeException("Failed to import CSV data: " + e.getMessage(), e);
        }

        logger.info("Imported {} attractions from CSV file", attractions.size());
        return attractionRepository.saveAll(attractions);
    }

    private Attraction createAttractionFromCsvData(String[] data) {
        Attraction attraction = new Attraction();

        if (data.length < 3) {
            logger.warn("Insufficient data in CSV row. Expected at least 3 columns, got {}", data.length);
            return null;
        }

        attraction.setTravelId(data[0]);
        attraction.setPoiId(data[1]);
        attraction.setPoiNm(data[2]);

        logger.debug("Created Attraction: travelId={}, poiId={}, poiNm={}",
                attraction.getTravelId(), attraction.getPoiId(), attraction.getPoiNm());

        return attraction;
    }
}