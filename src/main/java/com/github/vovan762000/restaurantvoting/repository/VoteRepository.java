package com.github.vovan762000.restaurantvoting.repository;

import com.github.vovan762000.restaurantvoting.error.DataConflictException;
import com.github.vovan762000.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.id = :id AND v.user.id=:userId")
    Optional<Vote> get(int id, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.date DESC")
    List<Vote> getAllForUser(int userId);

    @Query("SELECT v FROM Vote v ORDER BY v.date DESC ")
    List<Vote> getAll();

    default Vote checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + " doesn't belong to User id=" + userId));
    }

}
