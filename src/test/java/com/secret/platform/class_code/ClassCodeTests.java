package com.secret.platform.class_code;

import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.pricing_code.PricingCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClassCodeTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClassCodeRepository classCodeRepository;

    @Autowired
    private PricingCodeRepository pricingCodeRepository;

    @Test
    public void testCreateAndFindClassCode() {
        // Create a PricingCode
        PricingCode pricingCode = PricingCode.builder()
                .code("A")
                .description("Economy and Fullsize Cars")
                .ldwRate(20.0)
                .noLdwResp(3000.0)
                .noLdwAge1(24)
                .noLdwResp1(4000.0)
                .noLdwAge2(21)
                .noLdwResp2(5000.0)
                .inclLdwResp(0.0)
                .cvg2Value(0.0)
                .cvg3Value(0.0)
                .cvg4Value(0.0)
                .build();
        entityManager.persist(pricingCode);
        entityManager.flush();

        // Create a ClassCode
        ClassCode classCode = ClassCode.builder()
                .location("NYC")
                .classCode("ECAR")
                .description("Economy Car")
                .pricingCode(pricingCode)
                .minimumAge(25)
                .underAgeSrchg("Y")
                .resWarn1(85)
                .resWarn2(110)
                .classHierarchy(1)
                .edrbFlag("Y")
                .dropCategory("1")
                .useInPlanning("Y")
                .ldwRespEx("N")
                .addlIdReq("N")
                .altDesc("Econ Car")
                .classAuthPad("200")
                .surcharge("0.15")
                .assetTypeCode("VEH")
                .conversionCode("ECON")
                .url("http://example.com/econ_car.jpg")
                .discTimeOnly("Y")
                .bookWRes("Y")
                .lotScreenAva(1)
                .maxPassengers(4)
                .maxGvwr(2000)
                .pct2DaysOut("30")
                .reservable("Y")
                .minDaysForMetroplex("2")
                .vlfRate("1.00")
                .build();
        entityManager.persist(classCode);
        entityManager.flush();

        // Retrieve the ClassCode
        Optional<ClassCode> foundClassCode = classCodeRepository.findById(classCode.getId());
        assertThat(foundClassCode).isPresent();
        assertThat(foundClassCode.get().getPricingCode()).isEqualTo(pricingCode);
    }
}
