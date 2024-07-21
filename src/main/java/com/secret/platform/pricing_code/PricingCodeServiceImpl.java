package com.secret.platform.pricing_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PricingCodeServiceImpl implements PricingCodeService {

    private final PricingCodeRepository pricingCodeRepository;

    @Autowired
    public PricingCodeServiceImpl(PricingCodeRepository pricingCodeRepository) {
        this.pricingCodeRepository = pricingCodeRepository;
    }

    @Override
    public PricingCode createPricingCode(PricingCode pricingCode) {
        return pricingCodeRepository.save(pricingCode);
    }

    @Override
    public Optional<PricingCode> getPricingCodeById(Long id) {
        return pricingCodeRepository.findById(id);
    }

    @Override
    public List<PricingCode> getAllPricingCodes() {
        return pricingCodeRepository.findAll();
    }

    @Override
    public PricingCode updatePricingCode(Long id, PricingCode pricingCode) {
        if (pricingCodeRepository.existsById(id)) {
            pricingCode.setId(id);
            return pricingCodeRepository.save(pricingCode);
        }
        return null;
    }

    @Override
    public void deletePricingCode(Long id) {
        pricingCodeRepository.deleteById(id);
    }
}
