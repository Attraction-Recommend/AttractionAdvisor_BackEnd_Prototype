package com.example.prototype_recommend_server.csv.service;


import com.example.prototype_recommend_server.attraction.entity.Attraction;
import com.example.prototype_recommend_server.csv.domain.AttractionMapper;
import com.example.prototype_recommend_server.csv.domain.CsvReader;
import com.example.prototype_recommend_server.csv.jpa.AttractionCsvRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CsvImportService {
    private final CsvReader csvReader;
    private final AttractionMapper attractionMapper;
    private final AttractionCsvRepository attractionCsvRepository;

    @Transactional
    public List<Attraction> importCsvData() {
        List<String[]> rawData = csvReader.readCsvFile();
        List<Attraction> attractions = attractionMapper.mapToAttractions(rawData);
        return saveAttractions(attractions);
    }

    private List<Attraction> saveAttractions(List<Attraction> attractions) {
        return attractionCsvRepository.saveAll(attractions);
    }
}
