package com.Perfumelandia.controllerV2;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Perfumelandia.assemblers.CarritoModelAssembler;
import com.Perfumelandia.model.Producto;
import com.Perfumelandia.service.ProductoService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/carrito")
public class CarritoControllerV2 {
    
    // Cambio: Usar Map para manejar cantidad por producto
    private final Map<Long, Integer> carrito = new HashMap<>();
    
    @Autowired
    private ProductoService productoServ;
    private CarritoModelAssembler carritoAssembler;
    
    @PostMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable Long id) {
        Producto producto = productoServ.getProductoId(id);
        if (producto != null) {
            // Verificar stock antes de agregar
            int cantidadActual = carrito.getOrDefault(id, 0);
            if (producto.getStock() > cantidadActual) {
                carrito.put(id, cantidadActual + 1);
                return "Producto agregado al carrito: " + producto.getNombre();
            } else {
                return "No hay stock suficiente para el producto: " + producto.getNombre();
            }
        }
        return "Producto no fue encontrado";
    }
    
    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "Carrito vacío";
    }
    
    @DeleteMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        if (carrito.containsKey(id)) {
            int cantidad = carrito.get(id);
            if (cantidad > 1) {
                carrito.put(id, cantidad - 1);
                return "Se redujo la cantidad del producto en el carrito";
            } else {
                carrito.remove(id);
                return "Producto eliminado del carrito";
            }
        }
        return "Producto no estaba en el carrito";
    }
    
    @DeleteMapping("/eliminar/todos/{id}")
    public String eliminarTodosLosProductos(@PathVariable Long id) {
        boolean eliminado = carrito.remove(id) != null;
        return eliminado ? "Todos los productos de este tipo han sido eliminados del carrito" 
                         : "Producto no estaba en el carrito";
    }
    
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> verCarrito() {
        List<EntityModel<Producto>> productos = carrito.entrySet().stream()
            .map(entry -> {
                Producto producto = productoServ.getProductoId(entry.getKey());
                if (producto != null) {
                    // Opcional: Agregar información de cantidad al producto
                    // Aquí podrías crear un DTO que incluya la cantidad
                    return carritoAssembler.toModel(producto);
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
            
        return CollectionModel.of(productos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoControllerV2.class).verCarrito()).withSelfRel());
    }
    
    @PostMapping("/confirmar")
    public String confirmarCompra() {
        if (carrito.isEmpty()) {
            return "El carrito está vacío";
        }
        
        // Validar stock antes de procesar
        for (Map.Entry<Long, Integer> entry : carrito.entrySet()) {
            Long productoId = entry.getKey();
            int cantidadSolicitada = entry.getValue();
            Producto productoEnBD = productoServ.getProductoId(productoId);
            
            if (productoEnBD == null) {
                return "Error: Producto con ID " + productoId + " no encontrado";
            }
            
            if (productoEnBD.getStock() < cantidadSolicitada) {
                return "No hay stock suficiente para el producto: " + productoEnBD.getNombre() + 
                       ". Stock disponible: " + productoEnBD.getStock() + 
                       ", solicitado: " + cantidadSolicitada;
            }
        }
        
        // Si llegamos aquí, hay stock suficiente para todos los productos
        for (Map.Entry<Long, Integer> entry : carrito.entrySet()) {
            Long productoId = entry.getKey();
            int cantidadComprada = entry.getValue();
            Producto productoEnBD = productoServ.getProductoId(productoId);
            
            productoEnBD.setStock(productoEnBD.getStock() - cantidadComprada);
            productoServ.updateProducto(productoEnBD);
        }
        
        carrito.clear();
        return "Gracias por tu compra";
    }
    
    // Método adicional para obtener la cantidad de un producto específico
    @GetMapping("/cantidad/{id}")
    public String obtenerCantidad(@PathVariable Long id) {
        int cantidad = carrito.getOrDefault(id, 0);
        return "Cantidad en carrito: " + cantidad;
    }
    
    // Método adicional para obtener el total de items en el carrito
    @GetMapping("/total")
    public String obtenerTotal() {
        int total = carrito.values().stream().mapToInt(Integer::intValue).sum();
        return "Total de items en carrito: " + total;
    }
}