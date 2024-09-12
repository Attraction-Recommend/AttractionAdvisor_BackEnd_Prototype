package com.example.prototype_recommend_server.csv.domain;

import java.util.List;

public interface CsvReader {
    List<String[]> readCsvFile();
}
