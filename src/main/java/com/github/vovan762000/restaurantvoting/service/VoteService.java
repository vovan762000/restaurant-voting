package com.github.vovan762000.restaurantvoting.service;

import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.repository.UserRepository;
import com.github.vovan762000.restaurantvoting.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote save(Vote vote, int userId) {
        vote.setUser(userRepository.getById(userId));
        return voteRepository.save(vote);
    }
}
