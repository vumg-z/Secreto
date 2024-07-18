package com.secret.platform.rate_product;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateProductService {

    @Autowired
    private RateProductRepository rateProductRepository;

    public List<RateProduct> getAllRateProducts() {
        return rateProductRepository.findAll();
    }

    public Optional<RateProduct> getRateProductById(Long id) {
        return rateProductRepository.findById(id);
    }

    public RateProduct createRateProduct(RateProduct rateProduct) {
        return rateProductRepository.save(rateProduct);
    }

    public RateProduct updateRateProduct(Long id, RateProduct rateProductDetails) {
        RateProduct rateProduct = rateProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for this id :: " + id));

        // Update fields
        rateProduct.setRateSet(rateProductDetails.getRateSet());
        rateProduct.setProduct(rateProductDetails.getProduct());
        rateProduct.setEffPkupDate(rateProductDetails.getEffPkupDate());
        rateProduct.setEffPkupTime(rateProductDetails.getEffPkupTime());
        rateProduct.setMustPkupBefore(rateProductDetails.getMustPkupBefore());
        rateProduct.setComment(rateProductDetails.getComment());
        rateProduct.setRateType(rateProductDetails.getRateType());

        // Update remaining fields as required...

        return rateProductRepository.save(rateProduct);
    }

    public void deleteRateProduct(Long id) {
        RateProduct rateProduct = rateProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for this id :: " + id));

        rateProductRepository.delete(rateProduct);
    }
}
