package com.secret.platform.productos;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaqueteProductosExtrasService {

    @Autowired
    private PaqueteProductosExtrasRepository paqueteProductosExtrasRepository;

    public List<PaqueteProductosExtras> getAllPaquetes() {
        return paqueteProductosExtrasRepository.findAll();
    }

    public Optional<PaqueteProductosExtras> getPaqueteById(Long id) {
        return paqueteProductosExtrasRepository.findById(id);
    }

    public PaqueteProductosExtras createPaquete(PaqueteProductosExtras paquete) {
        return paqueteProductosExtrasRepository.save(paquete);
    }

    public PaqueteProductosExtras updatePaquete(Long id, PaqueteProductosExtras paqueteDetails) {
        PaqueteProductosExtras paquete = paqueteProductosExtrasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaqueteProductosExtras not found for this id :: " + id));

        paquete.setNombre(paqueteDetails.getNombre());
        paquete.setDescripcion(paqueteDetails.getDescripcion());
        paquete.setDiscount(paqueteDetails.getDiscount());
        paquete.setProductos(paqueteDetails.getProductos());
        paquete.setUpdatedAt(LocalDateTime.now());

        return paqueteProductosExtrasRepository.save(paquete);
    }

    public void deletePaquete(Long id) {
        PaqueteProductosExtras paquete = paqueteProductosExtrasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaqueteProductosExtras not found for this id :: " + id));

        paqueteProductosExtrasRepository.delete(paquete);
    }
}
