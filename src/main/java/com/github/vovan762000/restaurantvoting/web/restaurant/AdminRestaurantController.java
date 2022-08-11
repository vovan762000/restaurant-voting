package com.github.vovan762000.restaurantvoting.web.restaurant;


import com.github.vovan762000.restaurantvoting.model.Restaurant;
import com.github.vovan762000.restaurantvoting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {
    static final String REST_URL = "/api/admin/restaurant";

    private final RestaurantRepository repository;

    @PutMapping( value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        repository.deleteExisted(id);
    }
}
