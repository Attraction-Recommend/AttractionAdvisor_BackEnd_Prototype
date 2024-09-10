package com.example.prototype_recommend_server.controller;

import com.example.prototype_recommend_server.domain.attraction.Attraction;
import com.example.prototype_recommend_server.service.CsvImportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
public class CsvImportController {

    private final CsvImportService csvImportService;

    @GetMapping("/csv")
    public ResponseEntity<String> importCsvData() {
        List<Attraction> importedAttractions = csvImportService.importCsvData();
        return ResponseEntity.ok("Imported " + importedAttractions.size() + " attractions from CSV file."+"⚡  "+importedAttractions);
    }
}