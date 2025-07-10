package com.Perfumelandia.controllerV2;

import com.Perfumelandia.model.Reporte;
import com.Perfumelandia.service.ReporteService;
import com.Perfumelandia.assemblers.ReporteModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;




@RestController
@RequestMapping("/api/v2/reportes")

public class ReporteControllerV2 {

    @Autowired
    private ReporteService reporteServ;

    @Autowired
    private ReporteModelAssembler assembler;

    
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reporte>> enviarR(@RequestBody Reporte r) {
        Reporte creado = reporteServ.guardarReporte(r);
        return ResponseEntity
            .created(linkTo(methodOn(ReporteControllerV2.class)
            .verReporte(creado.getId())).toUri())
            .body(assembler.toModel(creado));
    }

    
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Reporte> verReporte(@PathVariable Long id) {
        Reporte reporte = reporteServ.obtenerReporte(id);
        return assembler.toModel(reporte);
    }
}
