package com.usedcars.user.repository;

import com.usedcars.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Return a list instead of Optional, to avoid NonUniqueResultException
    List<User> findByEmail(String email);
}
