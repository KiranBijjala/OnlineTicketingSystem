package com.capstone.ticket.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.capstone.ticket.model.Ticket;

class TicketTest {

	private Ticket ticket = new Ticket((long) Long.valueOf(1), "Alex", "2636 E. Millway Road", "2097281232",
			new Date(1, 2, 21), new Date(1, 2, 22));

	@Test
	void ticketTestID() {
		assertEquals(ticket.getId(), 1l);
	}

	@Test
	void ticketTestName() {
		assertEquals(ticket.getName(), "Alex");
	}

	@Test
	void ticketTestAddress() {
		assertEquals(ticket.getAddress(), "2636 E. Millway Road");
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
	void ticketSetTestAddress() {
		ticket.setAddress("2435 N. Westwood Way");
		assertEquals(ticket.getAddress(), "2435 N. Westwood Way");
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
