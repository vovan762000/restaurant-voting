package com.github.vovan762000.restaurantvoting.web.restaurant;

import com.github.vovan762000.restaurantvoting.model.Restaurant;
import com.github.vovan762000.restaurantvoting.web.MatcherFactory;

import java.util.List;

import static com.github.vovan762000.restaurantvoting.web.dish.DishTestData.firstRestaurantDishes;
import static com.github.vovan762000.restaurantvoting.web.dish.DishTestData.secondRestaurantDishes;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_WITH_DISHES =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int RESTAURANT_1_ID = 1;
    public static final int RESTAURANT_2_ID = 2;

    public static final int NOT_FOUND = 100;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "First restaurant", firstRestaurantDishes);
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "Second restaurant", secondRestaurantDishes);

    public static final List<Restaurant> restaurants = List.of(RESTAURANT_1, RESTAURANT_2);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", null);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_1_ID, "Updated restaurant name", List.of());
    }
}
