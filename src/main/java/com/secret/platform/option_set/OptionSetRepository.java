package com.secret.platform.option_set;

import com.secret.platform.option_set.OptionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionSetRepository extends JpaRepository<OptionSet, Long> {
    Optional<OptionSet> findByCode(String code);

}
