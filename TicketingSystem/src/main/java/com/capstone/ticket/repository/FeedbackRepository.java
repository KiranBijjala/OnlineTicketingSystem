package com.capstone.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.ticket.model.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback, Long>  {

}