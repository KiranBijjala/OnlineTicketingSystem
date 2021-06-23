package com.capstone.ticket.repository;

import com.capstone.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    @Query(value = "SELECT u FROM User u WHERE u.name=name")
    User findByName(String name);

}