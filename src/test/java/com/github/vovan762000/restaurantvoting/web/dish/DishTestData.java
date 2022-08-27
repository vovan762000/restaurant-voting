package com.github.vovan762000.restaurantvoting.web.dish;

import com.github.vovan762000.restaurantvoting.model.Dish;
import com.github.vovan762000.restaurantvoting.web.MatcherFactory;

import java.math.BigDecimal;
import java.util.List;

import static com.github.vovan762000.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_1;
import static com.github.vovan762000.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_2;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);
    public static final int FIRST_RES_DISH_1_ID = 1;
    public static final int FIRST_RES_DISH_2_ID = 2;
    public static final int FIRST_RES_DISH_3_ID = 3;
    public static final int FIRST_RES_DISH_4_ID = 4;
    public static final int SECOND_RES_DISH_1_ID = 5;
    public static final int SECOND_RES_DISH_2_ID = 6;
    public static final int SECOND_RES_DISH_3_ID = 7;

    public static final Dish FIRST_RES_DISH_1 = new Dish(FIRST_RES_DISH_1_ID, "first restaurant dish 1", new BigDecimal("10.99"), RESTAURANT_1);
    public static final Dish FIRST_RES_DISH_2 = new Dish(FIRST_RES_DISH_2_ID, "first restaurant dish 2", new BigDecimal("5.00"), RESTAURANT_1);
    public static final Dish FIRST_RES_DISH_3 = new Dish(FIRST_RES_DISH_3_ID, "first restaurant dish 3", new BigDecimal("88.00"), RESTAURANT_1);
    public static final Dish FIRST_RES_DISH_4 = new Dish(FIRST_RES_DISH_4_ID, "first restaurant dish 4", new BigDecimal("145.00"), RESTAURANT_1);
    public static final Dish SECOND_RES_DISH_1 = new Dish(SECOND_RES_DISH_1_ID, "second restaurant dish 1", new BigDecimal("5.00"), RESTAURANT_2);
    public static final Dish SECOND_RES_DISH_2 = new Dish(SECOND_RES_DISH_2_ID, "second restaurant dish 2", new BigDecimal("8.45"), RESTAURANT_2);
    public static final Dish SECOND_RES_DISH_3 = new Dish(SECOND_RES_DISH_3_ID, "second restaurant dish 3", new BigDecimal("5.99"), RESTAURANT_2);

    public static final List<Dish> firstRestaurantDishes = List.of(FIRST_RES_DISH_1, FIRST_RES_DISH_2, FIRST_RES_DISH_3, FIRST_RES_DISH_4);
    public static final List<Dish> secondRestaurantDishes = List.of(SECOND_RES_DISH_1, SECOND_RES_DISH_2, SECOND_RES_DISH_3);

    public static Dish getNew() {
        return new Dish(null, "New Dish for restaurant 1", new BigDecimal("1.00"));
    }

    public static Dish getUpdated() {
        return new Dish(FIRST_RES_DISH_1_ID, "Updated dish name", new BigDecimal("100.00"), RESTAURANT_1);
    }

}
