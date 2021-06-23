package com.capstone.ticket.repository;

import com.capstone.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>  {
	
}
