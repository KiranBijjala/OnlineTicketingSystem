package com.capstone.ticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.ticket.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

 
    void save(Optional<User> user);
}