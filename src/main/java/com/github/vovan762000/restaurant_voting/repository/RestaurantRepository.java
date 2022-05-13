package com.github.vovan762000.restaurant_voting.repository;

import com.github.vovan762000.restaurant_voting.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant,Integer> {
}
