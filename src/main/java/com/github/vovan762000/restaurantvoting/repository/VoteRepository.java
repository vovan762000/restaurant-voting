package com.github.vovan762000.restaurantvoting.repository;

import com.github.vovan762000.restaurantvoting.model.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Integer> {
}
