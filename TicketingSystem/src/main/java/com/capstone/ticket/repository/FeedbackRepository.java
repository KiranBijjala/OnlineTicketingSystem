package com.capstone.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.ticket.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>  {

}