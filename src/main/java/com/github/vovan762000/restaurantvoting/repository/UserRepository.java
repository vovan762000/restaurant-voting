package com.github.vovan762000.restaurantvoting.repository;

import com.github.vovan762000.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    Optional<User> getByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.votes WHERE u.id=?1")
    User getWithVotes(int id);
}
