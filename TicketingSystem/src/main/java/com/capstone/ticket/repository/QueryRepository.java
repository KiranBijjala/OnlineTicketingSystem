package com.capstone.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.ticket.model.Query;
import com.capstone.ticket.model.Ticket;

public interface QueryRepository extends JpaRepository<Query, Long>  {

}
