//package com.capstone.ticket.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@Entity
//@Table(name = "passengers")
//@EntityListeners(AuditingEntityListener.class)
//public class Passenger {
//
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "passengerId", nullable = false, unique = true)
//	private long passengerid;
//	
//	@ManyToOne
//	@JsonIgnoreProperties({ "tickets" })
//	@JoinColumn(name = "ticket_id", nullable = true)
//	private Ticket ticket;
//	
//	public long getPassengerid() {
//		return passengerid;
//	}
//
//	public void setPassengerid(long passengerid) {
//		this.passengerid = passengerid;
//	}
//
//	public Ticket getTicket() {
//		return ticket;
//	}
//
//	public void setTicket(Ticket ticket) {
//		this.ticket = ticket;
//	}
//
//	public String getQuery() {
//		return query;
//	}
//
//	public void setQuery(String query) {
//		this.query = query;
//	}
//
//	@Column(name = "query", nullable = true)
//	private String query;
//	
//}
