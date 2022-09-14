package com.github.vovan762000.restaurantvoting.service;

import com.github.vovan762000.restaurantvoting.model.Restaurant;
import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public Optional<Restaurant> getWithVotesByDay(int id, LocalDate date) {
        Restaurant restaurant = repository.getWithVotes(id).orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        List<Vote> votesByDay = restaurant.getVotes().stream()
                .filter(v -> v.getDate().isEqual(date))
                .collect(Collectors.toList());
        restaurant.setVotes(votesByDay);
        return Optional.of(restaurant);
    }
}
