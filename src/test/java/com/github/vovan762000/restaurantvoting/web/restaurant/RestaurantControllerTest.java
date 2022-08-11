package com.github.vovan762000.restaurantvoting.web.restaurant;

import com.github.vovan762000.restaurantvoting.web.AbstractControllerTest;
import com.github.vovan762000.restaurantvoting.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.vovan762000.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest{

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 1 + "/with-dishes"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}