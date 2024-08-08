package com.secret.platform.res_rates;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContractRepository;
import com.secret.platform.exception.CorporateRateNotFoundException;
import com.secret.platform.exception.InvalidDateException;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.rate_product.RateProductRepository;
import com.secret.platform.rate_product.RateProductServiceImpl;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.rate_product.RateProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class ResRatesService implements ResRatesServiceInterface {

    private final CorporateAccountRepository corporateAccountRepository;
    private final CorporateContractRepository corporateContractRepository;
    private final RateProductRepository rateProductRepository;
    private final RateProductServiceImpl rateProductService;
    private final ClassCodeRepository classCodeRepository;

    @Autowired
    public ResRatesService(CorporateAccountRepository corporateAccountRepository,
                           CorporateContractRepository corporateContractRepository,
                           RateProductRepository rateProductRepository,
                           RateProductServiceImpl rateProductService,
                           ClassCodeRepository classCodeRepository) {
        this.corporateAccountRepository = corporateAccountRepository;
        this.corporateContractRepository = corporateContractRepository;
        this.rateProductRepository = rateProductRepository;
        this.rateProductService = rateProductService;
        this.classCodeRepository = classCodeRepository;
    }

    @Override
    public ResRatesResponseDTO getRates(ResRatesDTO resRatesDTO) {
        // Validate the dates
        validateDates(resRatesDTO.getPickup().getDateTime(), resRatesDTO.getReturnInfo().getDateTime());

        // Retrieve the CorporateAccount using the CorpRateID
        String corpRateID = resRatesDTO.getCorpRateID();
        CorporateAccount corporateAccount = corporateAccountRepository.findByCdpId(corpRateID)
                .orElseThrow(() -> new CorporateRateNotFoundException(corpRateID));

        // Get the CorporateContract linked to the CorporateAccount
        CorporateContract corporateContract = corporateAccount.getCorporateContract();

        // Retrieve the RateProduct linked to the CorporateContract
        String rateProductName = corporateContract.getRateProduct();

        // Here we should get the rate product by specifying (location, countrycode, and rate product name)
        RateProduct rateProduct = resolveRateProduct(resRatesDTO.getPickup().getLocationCode(), resRatesDTO.getCountryCode(), rateProductName);

        // Create response DTO
        ResRatesResponseDTO response = new ResRatesResponseDTO();
        response.setSuccess(true);

        LocalDateTime pickupDateTime = resRatesDTO.getPickup().getDateTime();
        LocalDateTime returnDateTime = resRatesDTO.getReturnInfo().getDateTime();

        for (ClassCode classCode : rateProduct.getClassCodes()) {

            String rateID = rateProduct.getId().toString() + classCode.getClassCode();

            ResRatesResponseDTO.Rate rate = new ResRatesResponseDTO.Rate();
                rate.setRateID(rateID);
                rate.setAvailability("Available");
                rate.setClassCode(classCode.getClassCode());
                rate.setCurrencyCode("USD");

            double estimate = calculateEstimate(classCode, pickupDateTime, returnDateTime);
            rate.setEstimate(estimate);
            rate.setRateOnlyEstimate(estimate);

            rate.setDropCharge(createDropCharge());
            rate.setDistance(createDistance());
            rate.setLiability(0);
            rate.setPrePaid(false);
            response.addRate(rate);
        }

        response.setCount(response.getResRates().getRates().size());

        return response;
    }
    double calculateEstimate(ClassCode classCode, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        long totalDays = ChronoUnit.DAYS.between(pickupDateTime, returnDateTime);

        // Rates
        double dayRate = classCode.getDayRate();
        double weekRate = classCode.getWeekRate();
        double monthRate = classCode.getMonthRate();

        // Calculate the number of full months, weeks, and remaining days
        long months = totalDays / 30;
        long remainingDaysAfterMonths = totalDays % 30;
        long weeks = remainingDaysAfterMonths / 7;
        long days = remainingDaysAfterMonths % 7;

        // Calculate the total estimate
        double estimate = (months * monthRate) + (weeks * weekRate) + (days * dayRate);

        return estimate;
    }

    void validateDates(LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (pickupDateTime.isBefore(now) || returnDateTime.isBefore(pickupDateTime)) {
            throw new InvalidDateException("Invalid pickup or return date.");
        }

        long hoursUntilPickup = ChronoUnit.HOURS.between(now, pickupDateTime);
        if (hoursUntilPickup < 1) {
            throw new InvalidDateException("Pickup date must be at least 1 hour from now.");
        }

        long daysUntilReturn = ChronoUnit.DAYS.between(pickupDateTime, returnDateTime);
        if (daysUntilReturn < 1) {
            throw new InvalidDateException("Return date must be at least 1 day after pickup date.");
        }
    }

    RateProduct resolveRateProduct(String locationCode, String countryCode, String rateProductName) {
        Optional<RateProduct> rateProductOpt = rateProductService.getSpecificRateProduct(locationCode, countryCode, rateProductName);

        return rateProductOpt.orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for location: "
                + locationCode + ", country: " + countryCode + ", product: " + rateProductName));
    }




    private ResRatesResponseDTO.Rate.DropCharge createDropCharge() {
        ResRatesResponseDTO.Rate.DropCharge dropCharge = new ResRatesResponseDTO.Rate.DropCharge();
        dropCharge.setResponsibility("renter");
        dropCharge.setAmount(0.00);
        return dropCharge;
    }

    private ResRatesResponseDTO.Rate.Distance createDistance() {
        ResRatesResponseDTO.Rate.Distance distance = new ResRatesResponseDTO.Rate.Distance();
        distance.setIncluded("unlimited");
        return distance;
    }
}
