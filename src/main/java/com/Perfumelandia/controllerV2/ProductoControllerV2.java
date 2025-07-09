package com.Perfumelandia.controllerV2;

import com.Perfumelandia.model.Producto;
import com.Perfumelandia.service.ProductoService;
import com.Perfumelandia.assemblers.ProductoModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;


import java.util.stream.Collectors;
import java.util.List;





@RestController
@RequestMapping("/api/v2/productos")
@CrossOrigin(origins = "*", allowedHeaders = "*") // AGREGADO: Configuración CORS
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoServ;
    private boolean datosCargados = false;

    @Autowired
    private ProductoModelAssembler assembler;

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> registrarProducto(@RequestBody Producto p) {
        Producto crear = productoServ.saveProducto(p);
        return ResponseEntity
            .created(linkTo(methodOn(ProductoControllerV2.class)
            .obtenerProductoPorId(crear.getId())).toUri())
            .body(assembler.toModel(crear));
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> obtenerProductos() {
        
        if (!datosCargados && productoServ.getProducto().isEmpty()) {
            productoServ.saveProducto(new Producto(null, "Perfume Ocean", "Marca A", 10, 18990));
            productoServ.saveProducto(new Producto(null, "Perfume Sunset", "Marca B", 7, 22500));
            productoServ.saveProducto(new Producto(null, "Perfume Midnight", "Marca C", 5, 24990));
            datosCargados = true;
        }
        
        List<EntityModel<Producto>> productos = productoServ.getProducto().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(productos,
            linkTo(methodOn(ProductoControllerV2.class)
            .obtenerProductos()).withSelfRel());
    }

    // CORREGIDO: Cambio el tipo de retorno para ser consistente
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoServ.getProductoId(id);
        if (producto != null) {
            return ResponseEntity.ok(assembler.toModel(producto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/actualizar")
    public ResponseEntity<Void> getActualizarProductoLink(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        String resultado = productoServ.deleteProducto(id);
        return ResponseEntity.ok(resultado);
    }
}