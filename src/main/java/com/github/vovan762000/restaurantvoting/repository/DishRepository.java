package com.github.vovan762000.restaurantvoting.repository;

import com.github.vovan762000.restaurantvoting.error.DataConflictException;
import com.github.vovan762000.restaurantvoting.model.Dish;
import com.github.vovan762000.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.restaurant.id=:restaurantId")
    Optional<Dish> get(int id, int restaurantId);
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.dishName")
    List<Dish> getAll(int restaurantId);

    default Dish checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Dish id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}
