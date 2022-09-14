package com.github.vovan762000.restaurantvoting.web.vote;

import com.github.vovan762000.restaurantvoting.model.User;
import com.github.vovan762000.restaurantvoting.model.Vote;
import com.github.vovan762000.restaurantvoting.repository.UserRepository;
import com.github.vovan762000.restaurantvoting.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

@Component
@AllArgsConstructor
public class UniqueVoteValidator implements org.springframework.validation.Validator {
    public static final String EXCEPTION_DUPLICATE_DAY_OR_TOO_LATE = "Vote in this day already exist or too late change vote";

    private final VoteRepository voteRepository;

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(Class<?> clazz) {
        return Vote.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target,@NonNull Errors errors) {
        Vote vote = ((Vote) target);
        User authUser = userRepository.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (vote.isNew()) {
            voteRepository.getAllForUser(authUser.getId()).stream()
                    .filter(v -> v.getDate().isEqual(vote.getDate()))
                    .findAny().ifPresent(dbVote -> {
                        errors.rejectValue("date", "", EXCEPTION_DUPLICATE_DAY_OR_TOO_LATE);
                    });
        } else {
            voteRepository.get(vote.getId(), authUser.getId())
                    .ifPresent(dbVote -> {
                        if (request.getMethod().equals("PUT")) {  // UPDATE
                            int dbId = dbVote.id();
                            if (vote.getId() != null &&
                                    dbId == vote.id() &&
                                    LocalTime.now().isBefore(LocalTime.of(11, 0))) {
                                return;
                            }
                        }
                        errors.rejectValue("date", "", EXCEPTION_DUPLICATE_DAY_OR_TOO_LATE);
                    });
        }
    }
}
