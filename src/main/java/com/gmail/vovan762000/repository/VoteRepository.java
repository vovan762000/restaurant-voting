package com.gmail.vovan762000.repository;

import com.gmail.vovan762000.model.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Integer> {
}
