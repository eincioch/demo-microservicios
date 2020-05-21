package com.example.demo.controller;

import com.example.demo.model.entity.Provincia;
import com.example.demo.model.service.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;


@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
public class ProvinciaRestController {

    @Autowired
    private IProvinciaService provinciaService;

    @GetMapping("/provincias")
    public List<Provincia> index() {
        return provinciaService.findAll();
    }

    @GetMapping("/provincias/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable Long id) {

        Provincia provincia = null;
        Map<String, Object> response = new HashMap<>();

        try {
            provincia = provinciaService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (provincia == null) {
            response.put("mensaje", "El Provincia ID: ".concat(id.toString()).concat(" no existe en la base de datos."));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Provincia>(provincia, HttpStatus.OK);
    }


    @PostMapping("/provincias")
    public ResponseEntity<?> create(@Valid @RequestBody Provincia provincia, BindingResult result) {

        Provincia provinciaNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            provinciaNew = provinciaService.save(provincia);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La Provincia fue creado con éxito");
        response.put("provincia", provinciaNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/provincias/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Provincia provincia, BindingResult result, @PathVariable Long id) {

        Provincia provinciaActual = provinciaService.findById(id);
        Provincia provinciaActualizada = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errores", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (provinciaActual == null) {
            response.put("mensaje", "Error: no se pudo editar, la provincia ID: ".concat(id.toString()).concat(" no existe en la base de datos."));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            provinciaActual.setNombre(provincia.getNombre());
            provinciaActualizada = provinciaService.save(provinciaActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La Provincia fue actualizado con éxito");
        response.put("provincia", provinciaActualizada);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/provincias/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            provinciaService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la provincia en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La Provincia fue eliminada con éxito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


}
