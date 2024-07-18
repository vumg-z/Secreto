package com.secret.platform.corporateid;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CorporateIDService {

    @Autowired
    private CorporateIDRepository corporateIDRepository;

    public List<CorporateID> getAllCorporateIDs() {
        return corporateIDRepository.findAll();
    }

    public Optional<CorporateID> getCorporateIDById(Long id) {
        return corporateIDRepository.findById(id);
    }

    public CorporateID createCorporateID(CorporateID corporateID) {
        corporateID.setCreatedAt(LocalDateTime.now());
        corporateID.setUpdatedAt(LocalDateTime.now());
        return corporateIDRepository.save(corporateID);
    }

    public CorporateID updateCorporateID(Long id, CorporateID corporateIDDetails) {
        CorporateID corporateID = corporateIDRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateID not found for this id :: " + id));

        corporateID.setNombre(corporateIDDetails.getNombre());
        corporateID.setDescripcion(corporateIDDetails.getDescripcion());
        corporateID.setCodigoMoneda(corporateIDDetails.getCodigoMoneda());
        corporateID.setIva(corporateIDDetails.getIva());
        corporateID.setDiscount(corporateIDDetails.getDiscount());
        corporateID.setInclusiones(corporateIDDetails.getInclusiones());
        corporateID.setTerminosAlquiler(corporateIDDetails.getTerminosAlquiler());
        //corporateID.setProductos(corporateIDDetails.getProductos());
        corporateID.setPaqueteProductosExtras(corporateIDDetails.getPaqueteProductosExtras());
        corporateID.setUpdatedAt(LocalDateTime.now());

        return corporateIDRepository.save(corporateID);
    }

    public void deleteCorporateID(Long id) {
        CorporateID corporateID = corporateIDRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CorporateID not found for this id :: " + id));

        corporateIDRepository.delete(corporateID);
    }
}
