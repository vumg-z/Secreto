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
        // Log and check the isFee attribute
        if (createdOption.isFee()) {
            System.out.println("Created option with isFee: true for Option Code: " + createdOption.getOptionCode());
        } else {
            System.out.println("Created option with isFee: false for Option Code: " + createdOption.getOptionCode());
        }
        return ResponseEntity.ok(createdOption);
    }

    @GetMapping
    public ResponseEntity<List<Options>> getAllOptions() {
        List<Options> options = optionsService.getAllOptions();
        // Log and verify isFee for each option
        options.forEach(option -> {
            if (option.isFee()) {
                System.out.println("Retrieved option with isFee: true for Option Code: " + option.getOptionCode());
            } else {
                System.out.println("Retrieved option with isFee: false for Option Code: " + option.getOptionCode());
            }
        });
        return ResponseEntity.ok(options);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Options> getOptionById(@PathVariable Long id) {
        Options option = optionsService.getOptionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for this id :: " + id));
        // Log isFee status
        if (option.isFee()) {
            System.out.println("Retrieved option with isFee: true for Option ID: " + id);
        } else {
            System.out.println("Retrieved option with isFee: false for Option ID: " + id);
        }
        return ResponseEntity.ok(option);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Options> updateOption(@PathVariable Long id, @RequestBody Options optionDetails) {
        Options updatedOption = optionsService.updateOption(id, optionDetails);
        // Log isFee status after update
        if (updatedOption.isFee()) {
            System.out.println("Updated option with isFee: true for Option ID: " + id);
        } else {
            System.out.println("Updated option with isFee: false for Option ID: " + id);
        }
        return ResponseEntity.ok(updatedOption);
    }

    @DeleteMapping("/code/{optionCode}")
    public ResponseEntity<Void> deleteOptionByCode(@PathVariable String optionCode) {
        optionsService.deleteOptionByCode(optionCode);
        // Log deletion
        System.out.println("Deleted option with Option Code: " + optionCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code/{optionCode}")
    public ResponseEntity<Options> findByOptionCode(@PathVariable String optionCode) {
        Options option = optionsService.findByOptionCode(optionCode);
        // Log isFee status
        if (option.isFee()) {
            System.out.println("Retrieved option with isFee: true for Option Code: " + optionCode);
        } else {
            System.out.println("Retrieved option with isFee: false for Option Code: " + optionCode);
        }
        return ResponseEntity.ok(option);
    }

    @GetMapping("/optSetCode/{optSetCode}")
    public ResponseEntity<List<Options>> findByOptSetCode(@PathVariable String optSetCode) {
        List<Options> options = optionsService.findByOptSetCode(optSetCode);
        // Log and verify isFee for each option
        options.forEach(option -> {
            if (option.isFee()) {
                System.out.println("Retrieved option with isFee: true for Opt Set Code: " + optSetCode);
            } else {
                System.out.println("Retrieved option with isFee: false for Opt Set Code: " + optSetCode);
            }
        });
        return ResponseEntity.ok(options);
    }
}
