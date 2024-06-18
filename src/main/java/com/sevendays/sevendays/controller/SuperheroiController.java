package com.sevendays.sevendays.controller;

import com.sevendays.sevendays.repository.SuperheroiRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sevendays.sevendays.model.Superheroi;

@RestController
@RequestMapping("/superherois")
public class SuperheroiController {

    @Autowired    
    private SuperheroiRepository repository;

    @GetMapping
    public List<Superheroi> listar() {
        return repository.findAll();
    }

    @GetMapping("/search")
    public List<Superheroi> buscarPorPoderes(@RequestParam String poderes) {
        return repository.findByPoderesContainingIgnoreCase(poderes);
    }

    @PostMapping
    public ResponseEntity<?> adicionarLista(@Valid @RequestBody List<Superheroi> superherois, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        List<Superheroi> savedSuperheroes = repository.saveAll(superherois);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSuperheroes);
    }
    
    @PutMapping
    public ResponseEntity<?> alterar(@Valid @RequestBody Superheroi superheroi, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        if (superheroi.getId() != null && superheroi.getId() > 0) {
            return ResponseEntity.ok(repository.save(superheroi));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do super-herói inválido.");
        }
    }

    @DeleteMapping
    public String deletar(@RequestBody Superheroi superheroi) {
        if (superheroi.getId() > 0) {
            repository.delete(superheroi);
            return "Removido com sucesso";
        }
        return "Superherói não encontrado";
    }
}
