package com.secret.platform.options;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OptionsRepository extends JpaRepository<Options, Long> {
    Optional<Options> findByOptionCode(String optionCode);

}
