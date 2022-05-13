package com.github.vovan762000.restaurant_voting.repository;

import com.github.vovan762000.restaurant_voting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Integer> {
}
