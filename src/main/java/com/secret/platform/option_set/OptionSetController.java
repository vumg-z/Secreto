package com.secret.platform.option_set;

import com.secret.platform.options.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/option-sets")
public class OptionSetController {

    @Autowired
    private OptionSetService optionSetService;

    @PostMapping
    public ResponseEntity<OptionSet> createOptionSet(@RequestBody OptionSet optionSet) {
        return ResponseEntity.ok(optionSetService.createOptionSet(optionSet));
    }

    @GetMapping
    public ResponseEntity<List<OptionSet>> getAllOptionSets() {
        return ResponseEntity.ok(optionSetService.getAllOptionSets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionSet> getOptionSetById(@PathVariable Long id) {
        return ResponseEntity.ok(optionSetService.getOptionSetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OptionSet> updateOptionSet(@PathVariable Long id, @RequestBody OptionSet optionSetDetails) {
        return ResponseEntity.ok(optionSetService.updateOptionSet(id, optionSetDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOptionSet(@PathVariable Long id) {
        optionSetService.deleteOptionSet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<OptionSet> addOptionsToOptionSet(@PathVariable Long id, @RequestBody List<Options> options) {
        OptionSet optionSet = optionSetService.getOptionSetById(id);
        if (optionSet.getOptions() == null) {
            optionSet.setOptions(new ArrayList<>());
        }
        optionSet.getOptions().addAll(options);
        return ResponseEntity.ok(optionSetService.updateOptionSet(id, optionSet));
    }

    @GetMapping("/options")
    public ResponseEntity<List<Options>> getOptionsByOptSetCode(@RequestParam String optSetCode) {
        List<Options> options = optionSetService.getOptionsByOptSetCode(optSetCode);
        return ResponseEntity.ok(options);
    }
}
