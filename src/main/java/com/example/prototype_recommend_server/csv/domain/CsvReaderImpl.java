package com.example.prototype_recommend_server.csv.domain;

import com.example.prototype_recommend_server.csv.CsvImportException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CsvReaderImpl implements CsvReader{

    @Value("${file.csv.path}")
    private String csvFileUrl;

    public List<String[]> readCsvFile() {
        ClassPathResource resource = new ClassPathResource(csvFileUrl);

        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<String[]> allData = reader.readAll();
            allData.remove(0); // Remove header
            log.info("Read {} rows from CSV file", allData.size());
            return allData;
        } catch (IOException e) {
            log.error("Failed to read CSV file: {}", csvFileUrl, e);
            throw new CsvImportException("Failed to read CSV file", e);
        } catch (CsvException e) {
            log.error("Failed to parse CSV data: {}", csvFileUrl, e);
            throw new CsvImportException("Failed to parse CSV data", e);
        }
    }
}