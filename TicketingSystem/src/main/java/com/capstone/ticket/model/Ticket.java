package com.capstone.ticket.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
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
	
	@Column(name = "departure_location", nullable = false)
	private String departureLocation;
	
	@Column(name = "destination_location", nullable = false)
	private String destinationLocation;
	
	@Column(name = "contact", nullable = false)
	private String contact;
	
	@Column(name = "travel_date", nullable = false)
	private Date travelDate;
	
	@Column(name = "return_date", nullable = false)
	private Date returnDate;

	@Column(name = "passengers", nullable = true)
	private String passengers;
	
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
//	 private List<Passenger> passengers;
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional=true)
	@JsonIgnoreProperties({"queries","feedbacks"})
	@JoinColumn(name="user_name", referencedColumnName="name")
	private User user;

	public String getPassengers() {
		return passengers;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}


	public long getId() {
		return id;
	}


//	public List<Passenger> getPassengers() {
//		return passengers;
//	}
//
//
//	public void setPassengers(List<Passenger> passengers) {
//		this.passengers = passengers;
//	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


//	public String getAddress() {
//		return address;
//	}
//
//
//	public void setAddress(String address) {
//		this.address = address;
//	}


	public String getContact() {
		return contact;
	}


	public String getDepartureLocation() {
		return departureLocation;
	}
	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}
	public String getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
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

	

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", name=" + name + ", depatureLocation=" + departureLocation
				+ ", destinationLocation=" + destinationLocation + ", contact=" + contact + ", travelDate=" + travelDate
				+ ", returnDate=" + returnDate + ", passengers=" + passengers + ", user=" + user + "]";
	}
	public Ticket(long id, String name, String departureLocation, String destinationLocation, String contact,
			Date travelDate, Date returnDate, String passengers, User user) {
		super();
		this.id = id;
		this.name = name;
		this.departureLocation = departureLocation;
		this.destinationLocation = destinationLocation;
		this.contact = contact;
		this.travelDate = travelDate;
		this.returnDate = returnDate;
		this.passengers = passengers;
		this.user = user;
	}
	
	
	public Ticket() {
	}
//public Ticket(long id, String name, String departureLocation, String destinationLocation, String contact,
//		Date travelDate, Date returnDate, List<Passenger> passengers, User user) {
//	super();
//	this.id = id;
//	this.name = name;
//	this.departureLocation = departureLocation;
//	this.destinationLocation = destinationLocation;
//	this.contact = contact;
//	this.travelDate = travelDate;
//	this.returnDate = returnDate;
//	this.passengers = passengers;
//	this.user = user;
//}

	
}