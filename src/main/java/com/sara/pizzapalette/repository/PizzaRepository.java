package com.sara.pizzapalette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sara.pizzapalette.model.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}
