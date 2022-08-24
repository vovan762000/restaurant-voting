package com.github.vovan762000.restaurantvoting.web.dish;

import com.github.vovan762000.restaurantvoting.model.Dish;
import com.github.vovan762000.restaurantvoting.repository.DishRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
    static final String REST_URL = "/api/dish";

    private final DishRepository repository;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('scope:read')")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        log.info("get dish {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('scope:read')")
    public List<Dish> getAll() {
        log.info("get all dishes");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "dishName"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:write')")
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update dish {} with id {}", dish, id);
        assureIdConsistent(dish, id);
        repository.save(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('scope:write')")
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish) {
        log.info("create dish {}", dish);
        checkNew(dish);
        Dish created = repository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('scope:write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish {}", id);
        repository.deleteExisted(id);
    }
}
