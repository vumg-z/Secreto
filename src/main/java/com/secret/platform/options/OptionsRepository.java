package com.secret.platform.options;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OptionsRepository extends JpaRepository<Options, Long> {
    Optional<Options> findByOptionCode(String optionCode);

    @Query("SELECT o FROM Options o WHERE o.optSetCode = :optSetCode AND o.optionCode = :optionCode")
    Optional<Options> findByOptionCodeAndOptSetCode(@Param("optionCode") String optionCode, @Param("optSetCode") String optSetCode);

    @Query("SELECT o FROM Options o WHERE o.optSetCode = :optSetCode")
    List<Options> findByOptSetCode(@Param("optSetCode") String optSetCode);

    List<Options> findByWebResVisibleIn(List<String> visibilityFlags);


    @Query("SELECT o FROM Options o WHERE :optSetCode MEMBER OF o.optSetCodeAppended")
    List<Options> findOptionsByOptSetCodeAppended(@Param("optSetCode") String optSetCode);

}
