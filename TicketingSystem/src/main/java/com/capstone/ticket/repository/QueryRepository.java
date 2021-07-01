package com.capstone.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.ticket.model.Query;
import com.capstone.ticket.model.Ticket;
@Repository
public interface QueryRepository extends JpaRepository<Query, Long>  {

}
