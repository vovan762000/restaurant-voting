package com.github.vovan762000.restaurantvoting.web.user;

import com.github.vovan762000.restaurantvoting.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminUserController extends AbstractUserController {

    static final String REST_URL = "/api/admin/users";

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('scope:write')")
    public ResponseEntity<User> get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/with-votes/{id}")
    @PreAuthorize("hasAuthority('scope:write')")
    public ResponseEntity<User> getWithVotes(@PathVariable int id){
        return super.getWithVotes(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:write')")
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('scope:write')")
    public List<User> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('scope:write')")
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create {}", user);
        checkNew(user);
        User created = prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:write')")
//    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    @GetMapping("/by-email")
    @PreAuthorize("hasAuthority('scope:write')")
    public ResponseEntity<User> getByEmail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        return ResponseEntity.of(repository.getByEmail(email));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:write')")
    @Transactional
//    @CacheEvict(allEntries = true)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        User user = repository.getById(id);
        user.setEnabled(enabled);
    }
}
