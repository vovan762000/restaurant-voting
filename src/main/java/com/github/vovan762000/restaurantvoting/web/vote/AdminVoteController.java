package com.github.vovan762000.restaurantvoting.web.vote;

import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    static final String REST_URL = "/api/admin/votes";

    private final VoteRepository voteRepository;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('scope:admin_permission')")
    public ResponseEntity<Vote> get(@PathVariable int id) {
        log.info("get vote {}", id);
        return ResponseEntity.of(voteRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('scope:admin_permission')")
    public void delete(@PathVariable int id) {
        log.info("delete vote {}", id);
        voteRepository.deleteExisted(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('scope:admin_permission')")
    public List<Vote> getAll() {
        log.info("get all votes");
        return voteRepository.getAll();
    }
}
