package com.secret.platform.options;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options-rates")
public class OptionsRatesController {

    @Autowired
    private OptionsRatesService optionsRatesService;

    @PostMapping("/add")
    public ResponseEntity<OptionsRates> addRateToOption(@RequestBody OptionsRateRequest rateRequest) {
        OptionsRates newRate = optionsRatesService.addRateToOption(
                rateRequest.getOptionCode(),
                rateRequest.getLocationCode(),
                rateRequest.getCurrency(),
                rateRequest.getPrimaryRate(),
                rateRequest.getWeeklyRate(),
                rateRequest.getMonthlyRate(),
                rateRequest.getXdayRate(),
                rateRequest.getPricingCode(),
                rateRequest.getPrivilegeCode()
        );
        return ResponseEntity.ok(newRate);
    }

    @GetMapping("/find")
    public ResponseEntity<List<OptionsRates>> findRatesByCriteria(
            @RequestParam String optionCode,
            @RequestParam String privilegeCodeId,
            @RequestParam String pricingCodeId) {

        List<OptionsRates> rates = optionsRatesService.findRatesByCriteria(optionCode, privilegeCodeId, pricingCodeId);
        return ResponseEntity.ok(rates);
    }
}
