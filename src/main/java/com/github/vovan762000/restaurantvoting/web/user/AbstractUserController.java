package com.github.vovan762000.restaurantvoting.web.user;

import com.github.vovan762000.restaurantvoting.model.User;
import com.github.vovan762000.restaurantvoting.repository.UserRepository;
import com.github.vovan762000.restaurantvoting.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    public ResponseEntity<User> getWithVotes(int id){
        log.info("get {} with votes",id);
        return ResponseEntity.ok(repository.getWithVotes(id));
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}
