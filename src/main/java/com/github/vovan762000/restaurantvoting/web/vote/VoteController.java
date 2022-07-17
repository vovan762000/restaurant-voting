package com.github.vovan762000.restaurantvoting.web.vote;

import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.repository.VoteRepository;
import com.github.vovan762000.restaurantvoting.service.VoteService;
import com.github.vovan762000.restaurantvoting.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/profile/votes";

    private final VoteRepository repository;

    private final VoteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.get(id, authUser.id()));
    }

    @GetMapping
    public List<Vote> getAllForUser(@AuthenticationPrincipal AuthUser authUser){
        log.info("get all votes");
        return repository.getAllForUser(authUser.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete {} for user {}", id, authUser.id());
        Vote vote = repository.checkBelong(id, authUser.id());
        repository.delete(vote);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote) {
        int userId = authUser.id();
        log.info("create {} for user {}", vote, userId);
        checkNew(vote);
        Vote created = service.save(vote, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update {} for user {}", vote, userId);
        assureIdConsistent(vote, id);
        repository.checkBelong(id, userId);
        service.save(vote, userId);
    }
}
