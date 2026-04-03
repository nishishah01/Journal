package com.nishioffduty.journalApp.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public String getSentiment(String text) {
        if (text == null || text.isEmpty()) return null;

        text = text.toLowerCase();

        if (text.contains("good") || text.contains("happy") || text.contains("great")) {
            return "POSITIVE";
        } else if (text.contains("bad") || text.contains("sad") || text.contains("angry")) {
            return "NEGATIVE";
        } else {
            return "NEUTRAL";
        }
    }
}
