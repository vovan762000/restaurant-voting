package com.github.vovan762000.restaurantvoting.repository;

import com.github.vovan762000.restaurantvoting.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant,Integer> {
}
