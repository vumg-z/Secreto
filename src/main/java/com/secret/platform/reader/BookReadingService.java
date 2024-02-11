package com.secret.platform.reader;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookReadingService {

    private final ExecutorService executorService;

    public BookReadingService() {
        this.executorService = Executors.newCachedThreadPool(); // Adjust thread pool size as needed
    }

    private ConcurrentHashMap<String, Integer> readBookAndComputeWordFrequency(String bookPath) {
        ConcurrentHashMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(bookPath))) {
            lines.forEach(line -> {
                String[] words = line.toLowerCase() // Convert to lower case to ensure case-insensitive counting
                        .replaceAll("[^a-zA-Z ]", "") // Remove non-letter characters
                        .split("\\s+"); // Split by any amount of whitespace
                Arrays.stream(words)
                        .forEach(word -> {
                            if (!word.isEmpty()) { // Ignore empty words
                                wordCountMap.merge(word, 1, Integer::sum);
                            }
                        });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordCountMap;
    }


    // Correctly aggregate and sort word frequencies from multiple books
    public LinkedHashMap<String, Integer> readBooksAndComputeWordFrequencySorted(List<String> bookPaths) {
        List<Future<ConcurrentHashMap<String, Integer>>> futures = new ArrayList<>();
        ConcurrentHashMap<String, Integer> globalWordCount = new ConcurrentHashMap<>();

        // Aggregate word frequencies from multiple books
        for (String path : bookPaths) {
            Future<ConcurrentHashMap<String, Integer>> future = executorService.submit(() -> readBookAndComputeWordFrequency(path));
            futures.add(future);
        }

        for (Future<ConcurrentHashMap<String, Integer>> future : futures) {
            try {
                ConcurrentHashMap<String, Integer> result = future.get();
                result.forEach((key, value) -> globalWordCount.merge(key, value, Integer::sum));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Sort the aggregated word counts
        return globalWordCount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
