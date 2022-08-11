package com.github.vovan762000.restaurantvoting.web.restaurant;

import com.github.vovan762000.restaurantvoting.model.Restaurant;
import com.github.vovan762000.restaurantvoting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/api/profile/restaurant";
    private final RestaurantRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(repository.get(id));
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "restaurantName"));
    }

    @GetMapping("/{id}/with-dishes")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id) {
        log.info("get restaurant {} with dishes", id);
        return ResponseEntity.of(repository.getWithDishes(id));
    }
}
