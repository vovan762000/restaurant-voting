package com.github.vovan762000.restaurantvoting.web.vote;

import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.repository.VoteRepository;
import com.github.vovan762000.restaurantvoting.util.JsonUtil;
import com.github.vovan762000.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static com.github.vovan762000.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_2;
import static com.github.vovan762000.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static com.github.vovan762000.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static com.github.vovan762000.restaurantvoting.web.vote.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';
    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_VOTE_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(USER_VOTE_1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(userVotes));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ADMIN_VOTE_1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(ADMIN_VOTE_1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void create() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createDuplicateDate() throws Exception {
        Vote duplicateDayVote = getNew();
        duplicateDayVote.setDate(USER_VOTE_1.getDate());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicateDayVote)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateNotCurrentDay() throws Exception {
        Vote invalid = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_VOTE_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Vote updated = VOTE_MATCHER.readFromJson(action);
        updated.setRestaurant(RESTAURANT_2);
        int createdId = updated.id();
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            perform(MockMvcRequestBuilders.put(REST_URL + createdId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            VOTE_MATCHER.assertMatch(repository.getById(createdId), updated);
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL + createdId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }
}