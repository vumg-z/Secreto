package com.secret.platform.productos;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    public List<Productos> getAllProductos() {
        return productosRepository.findAll();
    }

    public Productos getProductosById(Long id) {
        return productosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Productos not found for this id :: " + id));
    }

    @Transactional
    public Productos createProductos(Productos productos) {
        return productosRepository.save(productos);
    }

    @Transactional
    public Productos updateProductos(Long id, Productos productosDetails) {
        Productos productos = productosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Productos not found for this id :: " + id));

        productos.setNombre(productosDetails.getNombre());
        productos.setDescripcion(productosDetails.getDescripcion());
        productos.setCodigo(productosDetails.getCodigo());
        productos.setCosto(productosDetails.getCosto());
        productos.setObligatorio(productosDetails.getObligatorio());
        productos.setIcono(productosDetails.getIcono());
        productos.setUpdatedAt(LocalDateTime.now());

        return productosRepository.save(productos);
    }

    @Transactional
    public void deleteProductos(Long id) {
        Productos productos = productosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Productos not found for this id :: " + id));

        productosRepository.delete(productos);
    }
}
