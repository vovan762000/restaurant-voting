package com.github.vovan762000.restaurantvoting.web.user;

import com.github.vovan762000.restaurantvoting.model.User;
import com.github.vovan762000.restaurantvoting.to.UserTo;
import com.github.vovan762000.restaurantvoting.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/api/profile";

    @GetMapping
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<User> get(@AuthenticationPrincipal UserDetails authUser) {
        return super.get(getAuthUserId(authUser));
    }

    @GetMapping("/with-votes")
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<User> getWithVotes(@AuthenticationPrincipal UserDetails authUser) {
        return super.getWithVotes(getAuthUserId(authUser));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public void delete(@AuthenticationPrincipal UserDetails authUser) {
        super.delete(getAuthUserId(authUser));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = prepareAndSave(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:user_permission')")
    @Transactional
    public void update(@RequestBody @Valid UserTo userTo, @AuthenticationPrincipal UserDetails authUser) {
        assureIdConsistent(userTo, getAuthUserId(authUser));
        User user = repository.getById(getAuthUserId(authUser));
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    private int getAuthUserId(@AuthenticationPrincipal UserDetails authUser) {
        User user = repository.getByEmail(authUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exists"));
        return user.getId();
    }
}
