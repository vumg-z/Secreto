package com.secret.platform.rate_product;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RateProductServiceImpl implements RateProductService {

    @Autowired
    private RateProductRepository rateProductRepository;

    @Autowired
    private OptionsServiceImpl optionsService;

    @Override
    public List<RateProduct> getAllRateProducts() {
        return rateProductRepository.findAll();
    }

    @Override
    public Optional<RateProduct> getRateProductById(Long id) {
        return rateProductRepository.findById(id);
    }

    @Override
    public RateProduct createRateProduct(RateProduct rateProduct, Map<String, Boolean> coverages) {
        updateIncludedOptions(rateProduct, coverages);
        return rateProductRepository.save(rateProduct);
    }

    @Override
    public RateProduct updateRateProduct(Long id, RateProduct rateProductDetails, Map<String, Boolean> coverages) {
        RateProduct rateProduct = rateProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for this id :: " + id));

        // Update field
        rateProduct.setRateSet(rateProductDetails.getRateSet());
        rateProduct.setProduct(rateProductDetails.getProduct());
        rateProduct.setEffPkupDate(rateProductDetails.getEffPkupDate());
        rateProduct.setEffPkupTime(rateProductDetails.getEffPkupTime());
        rateProduct.setMustPkupBefore(rateProductDetails.getMustPkupBefore());
        rateProduct.setComment(rateProductDetails.getComment());
        rateProduct.setRateType(rateProductDetails.getRateType());

        updateIncludedOptions(rateProduct, coverages);

        return rateProductRepository.save(rateProduct);
    }

    @Override
    public void deleteRateProduct(Long id) {
        RateProduct rateProduct = rateProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for this id :: " + id));

        rateProductRepository.delete(rateProduct);
    }

    private void updateIncludedOptions(RateProduct rateProduct, Map<String, Boolean> coverages) {
        rateProduct.getIncludedOptions().clear();

        for (Map.Entry<String, Boolean> entry : coverages.entrySet()) {
            if (entry.getValue() != null && entry.getValue()) {
                Options option = optionsService.findByOptionCode(entry.getKey());
                if (option != null) {
                    rateProduct.getIncludedOptions().add(option);
                }
            }
        }
    }
}
