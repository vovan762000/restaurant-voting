package com.github.vovan762000.restaurantvoting.web.user;

import com.github.vovan762000.restaurantvoting.model.Restaurant;
import com.github.vovan762000.restaurantvoting.model.Vote;

import static com.github.vovan762000.restaurantvoting.web.user.UserTestData.user;
import static java.time.LocalDateTime.of;

public class VoteTestData {
    public static final Vote USER_VOTE_1 = new Vote(of(2022,01,01,11,00), new Restaurant("First restaurant",null), user);
}
