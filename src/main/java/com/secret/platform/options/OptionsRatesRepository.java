package com.secret.platform.options;

import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.privilege_code.PrivilegeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionsRatesRepository extends JpaRepository<OptionsRates, Long> {

    List<OptionsRates> findByOptionCodeAndPrivilegeCodeAndPricingCode(String optionCode, PrivilegeCode privilegeCode, PricingCode pricingCode);

    List<OptionsRates> findByOptionCodeAndPrivilegeCodeAndPricingCodeAndCurrency(String optionCode, PrivilegeCode privilegeCode, PricingCode pricingCode, String currency);
}
