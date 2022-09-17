package com.github.vovan762000.restaurantvoting.web.restaurant;

import com.github.vovan762000.restaurantvoting.model.Restaurant;
import com.github.vovan762000.restaurantvoting.repository.RestaurantRepository;
import com.github.vovan762000.restaurantvoting.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";
    private final RestaurantRepository repository;

    private final RestaurantService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(repository.get(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('scope:user_permission')")
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "restaurantName"));
    }

    @GetMapping("/{id}/with-dishes")
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id) {
        log.info("get restaurant {} with dishes", id);
        return ResponseEntity.of(repository.getWithDishes(id));
    }

    @GetMapping("/{id}/with-votes")
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<Restaurant> getWithVotes(@PathVariable int id) {
        log.info("get restaurant {} with votes", id);
        return ResponseEntity.of(repository.getWithVotes(id));
    }

    @GetMapping("/{id}/with-votesByDay")
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<Restaurant> getWithVotesByDay(@PathVariable int id,
                                                        @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get restaurant {} with votes", id);
        return ResponseEntity.of(service.getWithVotesByDay(id,date));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:admin_permission')")
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('scope:admin_permission')")
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('scope:admin_permission')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurants",allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        repository.deleteExisted(id);
    }
}
