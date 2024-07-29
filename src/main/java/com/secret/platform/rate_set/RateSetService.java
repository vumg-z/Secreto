package com.secret.platform.rate_set;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.rate_product.RateProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateSetService {

    @Autowired
    private RateSetRepository rateSetRepository;

    public List<RateSetResponseDTO> getAllRateSets() {
        return rateSetRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public RateSetResponseDTO getRateSetById(Long id) {
        RateSet rateSet = rateSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateSet not found for this id :: " + id));
        return convertToDto(rateSet);
    }

    public RateSet createRateSet(RateSet rateSet) {
        return rateSetRepository.save(rateSet);
    }

    public RateSet updateRateSet(Long id, RateSet rateSetDetails) {
        RateSet rateSet = rateSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateSet not found for this id :: " + id));

        rateSet.setRateSetCode(rateSetDetails.getRateSetCode());
        rateSet.setDescription(rateSetDetails.getDescription());

        return rateSetRepository.save(rateSet);
    }

    public void deleteRateSet(Long id) {
        RateSet rateSet = rateSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateSet not found for this id :: " + id));
        rateSetRepository.delete(rateSet);
    }

    private RateSetResponseDTO convertToDto(RateSet rateSet) {
        List<RateProductResponseDTO> rateProductDTOs = rateSet.getRateProducts().stream()
                .map(rateProduct -> RateProductResponseDTO.builder()
                        .id(rateProduct.getId())
                        .productName(rateProduct.getProduct())
                        .build())
                .collect(Collectors.toList());

        List<LocationResponseDTO> locationDTOs = rateSet.getLocations().stream()
                .map(location -> LocationResponseDTO.builder()
                        .id(location.getId())
                        .locationCode(location.getLocationNumber())
                        .build())
                .collect(Collectors.toList());

        return RateSetResponseDTO.builder()
                .id(rateSet.getId())
                .rateSetCode(rateSet.getRateSetCode())
                .description(rateSet.getDescription())
                .rateProducts(rateProductDTOs)
                .locations(locationDTOs)
                .build();
    }
}
