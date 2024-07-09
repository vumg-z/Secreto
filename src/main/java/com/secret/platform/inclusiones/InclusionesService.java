package com.secret.platform.inclusiones;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InclusionesService {

    @Autowired
    private InclusionesRepository inclusionesRepository;

    public List<Inclusiones> getAllInclusiones() {
        return inclusionesRepository.findAll();
    }

    public Optional<Inclusiones> getInclusionesById(Long id) {
        return inclusionesRepository.findById(id);
    }

    public Inclusiones createInclusiones(Inclusiones inclusiones) {
        return inclusionesRepository.save(inclusiones);
    }

    public Inclusiones updateInclusiones(Long id, Inclusiones inclusionesDetails) {
        Inclusiones inclusiones = inclusionesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inclusiones not found for this id :: " + id));

        inclusiones.setNombre(inclusionesDetails.getNombre());
        inclusiones.setTexto(inclusionesDetails.getTexto());
        inclusiones.setUpdatedAt(LocalDateTime.now());

        return inclusionesRepository.save(inclusiones);
    }

    public void deleteInclusiones(Long id) {
        Inclusiones inclusiones = inclusionesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inclusiones not found for this id :: " + id));

        inclusionesRepository.delete(inclusiones);
    }
}
