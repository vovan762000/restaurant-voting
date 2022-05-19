package com.github.vovan762000.restaurantvoting.repository;

import com.github.vovan762000.restaurantvoting.model.Dish;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish,Integer> {
}
