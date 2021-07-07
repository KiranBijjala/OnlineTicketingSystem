package com.capstone.ticket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "passengers")
@EntityListeners(AuditingEntityListener.class)
public class Passenger {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "passengerId", nullable = false, unique = true)
	private long passengerid;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getPassengerContact() {
		return passengerContact;
	}

	public void setPassengerContact(String passengerContact) {
		this.passengerContact = passengerContact;
	}


	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	
	@Column(name = "passenger_contact", nullable = false, unique = true)
	private String passengerContact;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties({"passengers","user"})
	@JoinColumn(name = "ticket_id", nullable = true)
	private Ticket ticket;
	
	@Override
	public String toString() {
		return name ;
	}

	public long getPassengerid() {
		return passengerid;
	}

	public void setPassengerid(long passengerid) {
		this.passengerid = passengerid;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	
}
