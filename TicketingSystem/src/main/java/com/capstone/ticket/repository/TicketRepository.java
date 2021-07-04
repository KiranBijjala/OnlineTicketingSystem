package com.capstone.ticket.repository;

import com.capstone.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long>  {
	List<Ticket> findByName(String name);

	@Query(value = "SELECT p FROM Ticket p WHERE p.name = :name and p.status ='Active'")
	public Optional<List<Ticket>> findTicketsByName(@Param("name") String name);

	@Query(value = "SELECT p FROM Ticket p WHERE p.name = :name and p.status ='Cancelled'")
	public Optional<List<Ticket>> findTicketsByStatus(@Param("name") String name);


}
