package com.gmail.vovan762000.repository;

import com.gmail.vovan762000.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Integer> {
}
