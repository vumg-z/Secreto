package com.secret.platform.options;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionsController {

    @Autowired
    private OptionsServiceImpl optionsService;

    @PostMapping
    public ResponseEntity<Options> createOption(@RequestBody Options option) {
        Options createdOption = optionsService.createOption(option);
        return ResponseEntity.ok(createdOption);
    }

    @GetMapping
    public ResponseEntity<List<Options>> getAllOptions() {
        List<Options> options = optionsService.getAllOptions();
        return ResponseEntity.ok(options);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Options> getOptionById(@PathVariable Long id) {
        Options option = optionsService.getOptionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for this id :: " + id));
        return ResponseEntity.ok(option);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Options> updateOption(@PathVariable Long id, @RequestBody Options optionDetails) {
        Options updatedOption = optionsService.updateOption(id, optionDetails);
        return ResponseEntity.ok(updatedOption);
    }

    @DeleteMapping("/code/{optionCode}")
    public ResponseEntity<Void> deleteOptionByCode(@PathVariable String optionCode) {
        optionsService.deleteOptionByCode(optionCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code/{optionCode}")
    public ResponseEntity<Options> findByOptionCode(@PathVariable String optionCode) {
        Options option = optionsService.findByOptionCode(optionCode);
        return ResponseEntity.ok(option);
    }

    @GetMapping("/optSetCode/{optSetCode}")
    public ResponseEntity<List<Options>> findByOptSetCode(@PathVariable String optSetCode) {
        List<Options> options = optionsService.findByOptSetCode(optSetCode);
        return ResponseEntity.ok(options);
    }
}
