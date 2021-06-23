package com.capstone.ticket.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Optional;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tickets")
public class Ticket implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     @Column(name = "id", nullable = false)
     private long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "contact", nullable = false)
	private String contact;
	
	@Column(name = "travel_date", nullable = false)
	private Date travelDate;
	
	@Column(name = "return_date", nullable = false)
	private Date returnDate;

	@Column(name = "passengers", nullable = true)
	private String passengers;
	

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public Date getTravelDate() {
		return travelDate;
	}


	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}


	public Date getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Ticket(long id, String name, String address, String contact, Date travelDate, Date returnDate) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.travelDate = travelDate;
		this.returnDate = returnDate;
	}

	public Ticket() {
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", contact='" + contact + '\'' +
				", travelDate=" + travelDate +
				", returnDate=" + returnDate +
				'}';
	}
}