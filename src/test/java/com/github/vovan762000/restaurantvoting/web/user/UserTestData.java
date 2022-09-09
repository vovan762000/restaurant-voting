package com.github.vovan762000.restaurantvoting.web.user;

import com.github.vovan762000.restaurantvoting.model.Role;
import com.github.vovan762000.restaurantvoting.model.User;
import com.github.vovan762000.restaurantvoting.util.JsonUtil;
import com.github.vovan762000.restaurantvoting.web.MatcherFactory;
import com.github.vovan762000.restaurantvoting.web.vote.VoteTestData;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password", "votes");
    public static MatcherFactory.Matcher<User> USER_MATCHER_WITH_VOTES =
            MatcherFactory.usingAssertions(User.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@github.com";
    public static final String ADMIN_MAIL = "admin@github.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER, VoteTestData.userVotes);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Role.USER);
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Role.USER);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
