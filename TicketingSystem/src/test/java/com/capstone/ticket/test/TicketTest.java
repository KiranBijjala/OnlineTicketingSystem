package com.capstone.ticket.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.model.User;

class TicketTest {

	private Ticket ticket = new Ticket((long) Long.valueOf(1), "Alex", "Canada", "Australia", "2097281232",
			new Date(1, 2, 21), new Date(1, 2, 22), "Kyle", new User(1, "Alex", "Password", "2091234567"));

	@Test
	void ticketTestID() {
		assertEquals(ticket.getId(), 1l);
	}

	@Test
	void ticketTestName() {
		assertEquals(ticket.getName(), "Alex");
	}

	@Test
	void ticketTestDepartureLoation() {
		assertEquals(ticket.getDepartureLocation(), "Canada");
	}
	
	@Test
	void ticketTestDestinationLocation() {
		assertEquals(ticket.getDestinationLocation(), "Australia");
	}

	@Test
	void ticketTestContact() {
		assertEquals(ticket.getContact(), "2097281232");
	}

	@Test
	void ticketTestTravelDate() {
		assertEquals(ticket.getTravelDate(), new Date(1, 2, 21));
	}

	@Test
	void ticketTestReturnDate() {
		assertEquals(ticket.getReturnDate(), new Date(1, 2, 22));
	}

	@Test
	void ticketSetTestID() {
		ticket.setId(2l);
		assertEquals(ticket.getId(), 2l);
	}

	@Test
	void ticketSetTestName() {
		ticket.setName("Timothy");
		assertEquals(ticket.getName(), "Timothy");
	}
	
	@Test
	void ticketSetDepartureLocation() {
		ticket.setDepartureLocation("United States");
		assertEquals(ticket.getDepartureLocation(), "United States");
	}
	
	@Test
	void ticketSetDestinationLocation() {
		ticket.setDestinationLocation("Mexico");
		assertEquals(ticket.getDestinationLocation(), "Mexico");
	}

	@Test
	void ticketSetTestContact() {
		ticket.setContact("2312341324");
		assertEquals(ticket.getContact(), "2312341324");
	}

	@Test
	void ticketSetTestTravelDate() {
		Date newTravelDate = new Date(1, 3, 21);
		ticket.setTravelDate(newTravelDate);
		assertEquals(ticket.getTravelDate(), newTravelDate);
	}

	@Test
	void ticketSetTestReturnDate() {
		Date newReturnDate = new Date(1, 8, 21);
		ticket.setReturnDate(newReturnDate);
		assertEquals(ticket.getReturnDate(), newReturnDate);
	}
}
