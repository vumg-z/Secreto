package com.secret.platform.productos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaqueteProductosExtrasRepository extends JpaRepository<PaqueteProductosExtras, Long> {
}
