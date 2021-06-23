package com.capstone.ticket.repository;

import com.capstone.ticket.model.Ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>  {
	List<Ticket> findByName(String name);
}
