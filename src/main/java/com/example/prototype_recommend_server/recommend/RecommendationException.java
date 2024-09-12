package com.example.prototype_recommend_server.recommend;

// RecommendationException.java
public class RecommendationException extends RuntimeException {
    public RecommendationException(String message, Throwable cause) {
        super(message, cause);
    }
}