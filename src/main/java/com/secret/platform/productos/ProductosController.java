package com.secret.platform.productos;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @GetMapping
    public List<Productos> getAllProductos() {
        return productosService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productos> getProductosById(@PathVariable Long id) {
        Productos productos = productosService.getProductosById(id);
        return ResponseEntity.ok().body(productos);
    }


    @PostMapping
    public Productos createProductos(@RequestBody Productos productos) {
        return productosService.createProductos(productos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> updateProductos(@PathVariable Long id, @RequestBody Productos productosDetails) {
        Productos updatedProductos = productosService.updateProductos(id, productosDetails);
        return ResponseEntity.ok(updatedProductos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductos(@PathVariable Long id) {
        productosService.deleteProductos(id);
        return ResponseEntity.noContent().build();
    }
}
