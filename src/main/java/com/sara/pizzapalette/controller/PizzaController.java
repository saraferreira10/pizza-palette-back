package com.sara.pizzapalette.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sara.pizzapalette.model.Pizza;
import com.sara.pizzapalette.repository.PizzaRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/pizzas")
@CrossOrigin(origins = "*")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public List<Pizza> getAll() {
        return pizzaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id) {
        try {
            Pizza pizza = pizzaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("pizza not found"));
            return ResponseEntity.status(HttpStatus.OK).body(pizza);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityNotFoundException.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Pizza> save(@RequestBody Pizza pizza) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pizzaRepository.save(pizza));
    }

    @PutMapping
    public ResponseEntity<Object> edit(@RequestBody Pizza pizza) {
        try {
            if (!pizzaRepository.findById(pizza.getId()).isPresent()) {
                throw new EntityNotFoundException("pizza not found!");
            }

            return ResponseEntity.status(HttpStatus.OK).body(pizzaRepository.save(pizza));
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityNotFoundException.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id) {
        try {
            pizzaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("pizza deleted");
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityNotFoundException.getMessage());
        }
    }

}
