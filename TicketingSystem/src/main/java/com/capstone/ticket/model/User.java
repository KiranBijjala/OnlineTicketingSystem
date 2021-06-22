package com.capstone.ticket.model;

//import java.util.Arrays;
import java.sql.Date;
//import java.util.*;
import java.io.Serializable;
import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
	private int id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "contact", nullable = false)
	private String contact;
	
	@Column(name = "travel_date", nullable = false)
	private Date travelDate;
	
	@Column(name = "return_date", nullable = false)
	private Date returnDate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
	public User() {
		
	}
	
	public User(int id, String name, String address, String contact, Date travelDate, Date returnDate) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.travelDate = travelDate;
		this.returnDate = returnDate;
	}
	

	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", address=" + address + ", contact=" + contact + ", travelDate="
				+ travelDate + ", returnDate=" + returnDate + "]";
	}
	

	
}