package com.github.vovan762000.restaurantvoting.web.vote;

import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.web.MatcherFactory;
import com.github.vovan762000.restaurantvoting.web.restaurant.RestaurantTestData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.vovan762000.restaurantvoting.web.user.UserTestData.admin;
import static com.github.vovan762000.restaurantvoting.web.user.UserTestData.user;
import static java.time.LocalDate.of;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");
    public static final int USER_VOTE_1_ID = 1;
    public static final int USER_VOTE_2_ID = 2;
    public static final int USER_VOTE_3_ID = 5;
    public static final int ADMIN_VOTE_1_ID = 3;
    public static final int ADMIN_VOTE_2_ID = 4;


    public static final Vote USER_VOTE_1 = new Vote(USER_VOTE_1_ID, of(2022, 1, 1), RestaurantTestData.RESTAURANT_1, user);
    public static final Vote USER_VOTE_2 = new Vote(USER_VOTE_2_ID, of(2022, 2, 1), RestaurantTestData.RESTAURANT_2, user);
    public static final Vote USER_VOTE_3 = new Vote(USER_VOTE_3_ID, of(2022, 8, 1), RestaurantTestData.RESTAURANT_1, user);
    public static final Vote ADMIN_VOTE_1 = new Vote(ADMIN_VOTE_1_ID, of(2022, 1, 1), RestaurantTestData.RESTAURANT_1, admin);
    public static final Vote ADMIN_VOTE_2 = new Vote(ADMIN_VOTE_2_ID, of(2022, 6, 1), RestaurantTestData.RESTAURANT_1, admin);

    public static Vote getNew() {
        return new Vote(null, of(2022, 9, 7), null, null);
    }

    public static Vote getUpdated() {
        return new Vote(USER_VOTE_1_ID, of(2022, 1, 1), RestaurantTestData.RESTAURANT_2, user);
    }

    public static final List<Vote> userVotes = Stream.of(USER_VOTE_1, USER_VOTE_2, USER_VOTE_3)
            .sorted(Comparator.comparing(Vote::getDate).reversed())
            .collect(Collectors.toList());
    public static final List<Vote> adminVotes = Stream.of(ADMIN_VOTE_1, ADMIN_VOTE_2)
            .sorted(Comparator.comparing(Vote::getDate).reversed())
            .collect(Collectors.toList());

    public static final List<Vote> restaurant1Votes = Stream.of(USER_VOTE_1, USER_VOTE_3, ADMIN_VOTE_1, ADMIN_VOTE_2)
            .sorted(Comparator.comparing(Vote::getDate))
            .collect(Collectors.toList());
    public static final List<Vote> restaurant1VotesByDay = Stream.of(USER_VOTE_1, ADMIN_VOTE_1)
            .sorted(Comparator.comparing(Vote::getDate))
            .collect(Collectors.toList());
}
