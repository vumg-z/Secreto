package com.secret.platform.reader;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption; // Import this for the REPLACE_EXISTING option
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookReadingService bookReadingService;
    private final String uploadDir = "uploads"; // Directory to store uploaded files

    public BookController(BookReadingService bookReadingService) {
        this.bookReadingService = bookReadingService;
        new File(uploadDir).mkdir(); // Ensure the upload directory exists
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/upload-books")
    public ResponseEntity<Map<String, Integer>> uploadBooks(@RequestParam("books") MultipartFile[] books) throws Exception {
        List<String> bookPaths = new ArrayList<>();

        for (MultipartFile book : books) {
            if (!book.isEmpty()) {
                // Save each book to the upload directory
                String filename = book.getOriginalFilename();
                Path savePath = Paths.get(uploadDir, filename);

                // Use the REPLACE_EXISTING option to overwrite any existing file
                Files.copy(book.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

                bookPaths.add(savePath.toString());
            }
        }

        // Call the method that aggregates and sorts word frequencies
        LinkedHashMap<String, Integer> sortedWordFrequencies = bookReadingService.readBooksAndComputeWordFrequencySorted(bookPaths);
        return ResponseEntity.ok(sortedWordFrequencies);
    }
}
