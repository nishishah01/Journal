package com.nishioffduty.journalApp.scheduler;

import com.nishioffduty.journalApp.entity.JournalEntry;
import com.nishioffduty.journalApp.entity.User;
import com.nishioffduty.journalApp.enums.Sentiment;
import com.nishioffduty.journalApp.repository.UserRepositoryImpl;
import com.nishioffduty.journalApp.service.EmailService;
import com.nishioffduty.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 * * ? * *")
    public void fetchUsersAndSendSaMail() {

        List<User> users = userRepository.getUserForSA();

        for (User user : users) {

            List<JournalEntry> journalEntries = user.getJournalEntries();

            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate() != null &&
                            x.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(entry -> {
                        try {
                            String result = sentimentAnalysisService.getSentiment(entry.getContent());
                            return result != null ? Sentiment.valueOf(result.toUpperCase()) : null;
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for (Sentiment sentiment : sentiments) {
                sentimentCounts.put(
                        sentiment,
                        sentimentCounts.getOrDefault(sentiment, 0) + 1
                );
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                emailService.sendEmail(
                        user.getEmail(),
                        "Sentiment for last 7 days",
                        mostFrequentSentiment.toString()
                );
            }
        }
    }
}