package com.secret.platform.tarifa;

import com.secret.platform.corporateid.CorporateID;
import com.secret.platform.corporateid.CorporateIDRepository;
import com.secret.platform.dto.ResRatesRequest;
import com.secret.platform.dto.ResRatesResponse;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.vehicle_class.VehicleClass;
import com.secret.platform.vehicle_class.VehicleClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
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
        tarifa.setEstimate(tarifaDetails.getEstimate());
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

        return tarifaRepository.save(tarifa);
    }

    public void deleteTarifa(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa not found for this id :: " + id));
        tarifaRepository.delete(tarifa);
    }

    public ResRatesResponse getRates(ResRatesRequest request) {
        Optional<CorporateID> corporateID = corporateIDRepository.findByName(request.getCorpRateID());

        if (!corporateID.isPresent()) {
            throw new ResourceNotFoundException("CorporateID not found for the provided name :: " + request.getCorpRateID());
        }

        List<Tarifa> tarifas = tarifaRepository.findByCorporateID(String.valueOf(corporateID.get()));

        long days = ChronoUnit.DAYS.between(request.getPickupDateTime(), request.getReturnDateTime());

        List<ResRatesResponse.Rate> rates = tarifas.stream().map(tarifa -> {
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

        ResRatesResponse response = new ResRatesResponse();
        response.setSuccess(true);
        response.setCount(rates.size());
        response.setRates(rates);

        return response;
    }
}
