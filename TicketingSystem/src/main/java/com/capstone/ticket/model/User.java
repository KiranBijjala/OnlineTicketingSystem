package com.capstone.ticket.model;

//import java.util.Arrays;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

//import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	public List<Query> getQueries() {
		return queries;
	}
	public void setQueries(List<Query> queries) {
		this.queries = queries;
	}

	@Column(name = "password", nullable = false)
	private String password;
	
//	@Column(name = "address", nullable = true)
//	private String address;
	
	@Embedded
	private Address address;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
	 private List<Query> queries;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
	 private List<Ticket> tickets;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	 private List<Feedback> feedbacks;
	
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	public User(long id, String name, String password, String contactNumber) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.contactNumber = contactNumber;
	}

	@Column(name = "contact_number", nullable = true)
	private String contactNumber;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", contact='" + contactNumber + '\'' +
				'}';
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public User() {
		
	}

	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}