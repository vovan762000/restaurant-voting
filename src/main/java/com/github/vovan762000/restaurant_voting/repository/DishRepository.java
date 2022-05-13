package com.github.vovan762000.restaurant_voting.repository;

import com.github.vovan762000.restaurant_voting.model.Dish;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish,Integer> {
}
