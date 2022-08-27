package com.github.vovan762000.restaurantvoting.web.dish;

import com.github.vovan762000.restaurantvoting.model.Dish;
import com.github.vovan762000.restaurantvoting.repository.DishRepository;
import com.github.vovan762000.restaurantvoting.service.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    static final String REST_URL = "/api/restaurants/{restaurantId}/dishes";

    private final DishRepository repository;
    private final DishService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('scope:read')")
    public ResponseEntity<Dish> get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", id,restaurantId);
        return ResponseEntity.of(repository.get(id, restaurantId));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('scope:read')")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant {}",restaurantId);
        return repository.getAll(restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:write')")
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update dish {} with id {} for restaurant {}", dish, id ,restaurantId);
        assureIdConsistent(dish, id);
        repository.checkBelong(id, restaurantId);
        service.save(dish, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('scope:write')")
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        Dish created = service.save(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/restaurants/{restaurantId}/dishes/{id}")
                .buildAndExpand(restaurantId,created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('scope:write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        Dish dish = repository.checkBelong(id, restaurantId);
        repository.delete(dish);
    }
}
