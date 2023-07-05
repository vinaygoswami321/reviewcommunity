package com.reviewcommunity.repository;

import com.reviewcommunity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    @Query("SELECT COUNT(U) FROM User U")
    int getUserCount();
}
