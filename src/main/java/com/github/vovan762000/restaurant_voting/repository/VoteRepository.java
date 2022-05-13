package com.github.vovan762000.restaurant_voting.repository;

import com.github.vovan762000.restaurant_voting.model.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Integer> {
}
