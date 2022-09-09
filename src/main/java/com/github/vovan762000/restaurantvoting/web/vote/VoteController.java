package com.github.vovan762000.restaurantvoting.web.vote;

import com.github.vovan762000.restaurantvoting.model.User;
import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.repository.RestaurantRepository;
import com.github.vovan762000.restaurantvoting.repository.UserRepository;
import com.github.vovan762000.restaurantvoting.repository.VoteRepository;
import com.github.vovan762000.restaurantvoting.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.vovan762000.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/profile/votes";

    private final VoteRepository voteRepository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    private final UniqueVoteValidator voteValidator;

    private final VoteService service;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(voteValidator);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<Vote> get(@AuthenticationPrincipal UserDetails authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, getAuthUserId(authUser));
        return ResponseEntity.of(voteRepository.get(id, getAuthUserId(authUser)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public List<Vote> getAllForUser(@AuthenticationPrincipal UserDetails authUser) {
        log.info("get all votes");
        return voteRepository.getAllForUser(getAuthUserId(authUser));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:admin_permission')")
    public void delete(@AuthenticationPrincipal UserDetails authUser, @PathVariable int id) {
        log.info("delete {} for user {}", id, getAuthUserId(authUser));
        Vote vote = voteRepository.checkBelong(id, getAuthUserId(authUser));
        voteRepository.delete(vote);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote,
                                       @AuthenticationPrincipal UserDetails authUser,
                                       @RequestParam @Nullable int restaurantId) {
        int userId = getAuthUserId(authUser);
        vote.setRestaurant(restaurantRepository.getById(restaurantId));
        log.info("create {} for user {} for restaurant {}", vote, userId, restaurantId);
        checkNew(vote);
        Vote created = service.save(vote, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:user_permission')")
    public void update(@Valid @RequestBody Vote vote, @AuthenticationPrincipal UserDetails authUser, @PathVariable int id) {
        int userId = getAuthUserId(authUser);
        log.info("update {} for user {}", vote, userId);
        assureIdConsistent(vote, id);
        voteRepository.checkBelong(id, userId);
        service.save(vote, userId);
    }

    private int getAuthUserId(@AuthenticationPrincipal UserDetails authUser) {
        User user = Objects.requireNonNull(userRepository.getByEmail(authUser.getUsername()))
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        return user.getId();
    }
}
