package com.secret.platform.terminos_alquiler;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TerminosAlquilerService {

    @Autowired
    private TerminosAlquilerRepository terminosAlquilerRepository;

    public List<TerminosAlquiler> getAllTerminosAlquiler() {
        return terminosAlquilerRepository.findAll();
    }

    public Optional<TerminosAlquiler> getTerminosAlquilerById(Long id) {
        return terminosAlquilerRepository.findById(id);
    }

    public TerminosAlquiler createTerminosAlquiler(TerminosAlquiler terminosAlquiler) {
        return terminosAlquilerRepository.save(terminosAlquiler);
    }

    public TerminosAlquiler updateTerminosAlquiler(Long id, TerminosAlquiler terminosAlquilerDetails) {
        TerminosAlquiler terminosAlquiler = terminosAlquilerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TerminosAlquiler not found for this id :: " + id));

        terminosAlquiler.setNombre(terminosAlquilerDetails.getNombre());
        terminosAlquiler.setContenido(terminosAlquilerDetails.getContenido());
        terminosAlquiler.setUpdatedAt(LocalDateTime.now());

        return terminosAlquilerRepository.save(terminosAlquiler);
    }

    public void deleteTerminosAlquiler(Long id) {
        TerminosAlquiler terminosAlquiler = terminosAlquilerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TerminosAlquiler not found for this id :: " + id));

        terminosAlquilerRepository.delete(terminosAlquiler);
    }
}
