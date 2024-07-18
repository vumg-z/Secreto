package com.secret.platform.tarifa;

import com.secret.platform.corporateid.CorporateID;
import com.secret.platform.corporateid.CorporateIDRepository;
import com.secret.platform.dto.ResRatesRequest;
import com.secret.platform.dto.ResRatesResponse;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.productos.Productos;
import com.secret.platform.productos.PaqueteProductosExtras;
import com.secret.platform.vehicle_class.VehicleClass;
import com.secret.platform.vehicle_class.VehicleClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private CorporateIDRepository corporateIDRepository;

    @Autowired
    private VehicleClassRepository vehicleClassRepository;

    public List<Tarifa> getAllTarifas() {
        return tarifaRepository.findAll();
    }

    public Optional<Tarifa> getTarifaById(Long id) {
        return tarifaRepository.findById(id);
    }

    public Tarifa createTarifa(Tarifa tarifa) {
        // Calculate the Estimate before saving
        tarifa.setEstimate(calculateEstimate(tarifa));
        return tarifaRepository.save(tarifa);
    }

    public Tarifa updateTarifa(Long id, Tarifa tarifaDetails) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa not found for this id :: " + id));

        tarifa.setName(tarifaDetails.getName());
        tarifa.setDescription(tarifaDetails.getDescription());
        tarifa.setCorporateID(tarifaDetails.getCorporateID());
        tarifa.setVehicleClass(tarifaDetails.getVehicleClass());
        tarifa.setAvailability(tarifaDetails.isAvailability());
        tarifa.setCodigoMoneda(tarifaDetails.getCodigoMoneda());
        tarifa.setRateOnlyEstimate(tarifaDetails.getRateOnlyEstimate());
        tarifa.setDropCharge(tarifaDetails.getDropCharge());
        tarifa.setDistance(tarifaDetails.getDistance());
        tarifa.setPrepaid(tarifaDetails.isPrepaid());
        tarifa.setIva(tarifaDetails.getIva());
        tarifa.setDiscount(tarifaDetails.getDiscount());
        tarifa.setInclusiones(tarifaDetails.getInclusiones());
        tarifa.setProductosObligatorios(tarifaDetails.getProductosObligatorios());
        tarifa.setProductosExtras(tarifaDetails.getProductosExtras());
        tarifa.setTerminosAlquiler(tarifaDetails.getTerminosAlquiler());
        tarifa.setUpdatedAt(LocalDateTime.now());

        // Calculate the Estimate
        tarifa.setEstimate(calculateEstimate(tarifa));

        return tarifaRepository.save(tarifa);
    }

    public void deleteTarifa(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa not found for this id :: " + id));
        tarifaRepository.delete(tarifa);
    }

    public double calculateEstimate(Tarifa tarifa) {
        double rateOnlyEstimate = tarifa.getRateOnlyEstimate();
        double discount = tarifa.getDiscount();
        double iva = tarifa.getIva();

        // Initialize mandatoryProductCost
        double mandatoryProductCost = 0.0;
        if (tarifa.getProductosObligatorios() != null) {
            mandatoryProductCost = tarifa.getProductosObligatorios().stream()
                    .mapToDouble(Productos::getCosto)
                    .sum();
        }

        // Initialize paqueteDiscount
        double paqueteDiscount = 0.0;
        if (tarifa.getCorporateID() != null && tarifa.getCorporateID().getPaqueteProductosExtras() != null) {
            paqueteDiscount = tarifa.getCorporateID().getPaqueteProductosExtras().stream()
                    .mapToDouble(PaqueteProductosExtras::getDiscount)
                    .sum();
        }

        // Apply discounts
        double discountedRate = rateOnlyEstimate * (1 - discount / 100) * (1 - paqueteDiscount / 100);

        // Add mandatory product costs
        double totalEstimate = discountedRate + mandatoryProductCost;

        // Apply IVA
        return totalEstimate * (1 + iva / 100);
    }


    public ResRatesResponse getRates(ResRatesRequest request, String rateSet) {
        System.out.println("Request received: " + request);

        // Fetch the corporate ID
        Optional<CorporateID> corporateIDOpt = corporateIDRepository.findByNombre(request.getCorpRateID());
        if (!corporateIDOpt.isPresent()) {
            return new ResRatesResponse(false, "Corporate ID not found");
        }
        CorporateID corporateID = corporateIDOpt.get();
        System.out.println("Corporate ID: " + corporateID);

        // Fetch the tariffs
        List<Tarifa> tarifas = tarifaRepository.findByRateSetAndLocationCode(rateSet, request.getPickupLocationCode());
        System.out.println("Fetched tarifas: " + tarifas);

        if (tarifas.isEmpty()) {
            return new ResRatesResponse(false, "No rates found for the given location");
        }

        // Process the tariffs
        List<ResRatesResponse.Rate> rates = tarifas.stream().map(tarifa -> {
            long days = java.time.Duration.between(request.getPickupDateTime(), request.getReturnDateTime()).toDays();

            ResRatesResponse.Rate rate = new ResRatesResponse.Rate();
            rate.setRateID(tarifa.getId().toString());
            rate.setVehicleClass(tarifa.getVehicleClass().getClassName());
            rate.setAvailability(tarifa.isAvailability() ? "Available" : "Not Available");
            rate.setCurrencyCode(tarifa.getCodigoMoneda());
            rate.setEstimate(tarifa.getEstimate() * days);
            rate.setRateOnlyEstimate(tarifa.getRateOnlyEstimate() * days);
            rate.setDropCharge(tarifa.getDropCharge());
            rate.setDistanceIncluded("unlimited");
            rate.setLiability(0);
            rate.setPrePaid(tarifa.isPrepaid());
            return rate;
        }).collect(Collectors.toList());

        ResRatesResponse response = new ResRatesResponse(true, rates);
        System.out.println("Response generated: " + response);
        return response;
    }
}
