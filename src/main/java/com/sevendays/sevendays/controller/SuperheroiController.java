package com.sevendays.sevendays.controller;

import com.sevendays.sevendays.repository.SuperheroiRepository;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Superheroi> adicionarLista(@RequestBody List<Superheroi> superherois) {
        return repository.saveAll(superherois);
    }
    
    @PutMapping
    public Superheroi alterar(@RequestBody Superheroi superheroi) {
        if (superheroi.getId() > 0) {
            return repository.save(superheroi);
        }
        return null;
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
