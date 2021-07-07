package com.capstone.ticket.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "contact", nullable = false)
	private String contact;

	@Column(name = "status")
	private String status;

	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(name = "travel_date", nullable = false)
	private Date travelDate;
	
	@Column(name = "return_date", nullable = false)
	private Date returnDate;


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ticket",orphanRemoval = true, cascade = CascadeType.ALL)
	 private List<Passenger> passengers;


	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JsonIgnoreProperties({"queries","feedbacks","passengers","tickets"})
	@JoinColumn(name="user_name", referencedColumnName="name")
	private User user;

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passengers == null) ? 0 : passengers.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (passengers == null) {
			if (other.passengers != null)
				return false;
		} else if (!passengers.equals(other.passengers))
			return false;
		return true;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}



	public long getId() {
		return id;
	}


	public List<Passenger> getPassengers() {
		return passengers;
	}


	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
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



	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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
		return "Ticket{" +
				"id=" + id +
				", name='" + name + '\'' +
				", departureLocation='" + departureLocation + '\'' +
				", destinationLocation='" + destinationLocation + '\'' +
				", contact='" + contact + '\'' +
				", status='" + status + '\'' +
				", price=" + price +
				", travelDate=" + travelDate +
				", returnDate=" + returnDate +
				", passengers=" + passengers +
				", user=" + user +
				'}';
	}

	public Ticket() {
		this.price = 100;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

//	public Ticket(long id, String name, String departureLocation, String destinationLocation, String contact,
//				  Date travelDate, Date returnDate, List<Passenger> passengers, User user, int price) {
//	super();
//	this.id = id;
//	this.name = name;
//	this.price=100;
//	this.departureLocation = departureLocation;
//	this.destinationLocation = destinationLocation;
//	this.contact = contact;
//	this.travelDate = travelDate;
//	this.returnDate = returnDate;
//	this.passengers = passengers;
//	this.user = user;
//}


//	public Ticket(String name, String departureLocation, String destinationLocation, String contact, String status, int price, Date travelDate, Date returnDate, List<Passenger> passengers, User user) {
//		this.name = name;
//		this.departureLocation = departureLocation;
//		this.destinationLocation = destinationLocation;
//		this.contact = contact;
//		this.status = status;
//		this.price = 100;
//		this.travelDate = travelDate;
//		this.returnDate = returnDate;
//		this.passengers = passengers;
//		this.user = user;
//	}


	public Ticket(long id, String name, String departureLocation, String destinationLocation, String contact, String status, int price, Date travelDate, Date returnDate, List<Passenger> passengers, User user) {
		this.id = id;
		this.name = name;
		this.departureLocation = departureLocation;
		this.destinationLocation = destinationLocation;
		this.contact = contact;
		this.status = status;
		this.price = 100;
		this.travelDate = travelDate;
		this.returnDate = returnDate;
		this.passengers = passengers;
		this.user = user;
	}
}
