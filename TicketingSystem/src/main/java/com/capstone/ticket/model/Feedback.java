package com.capstone.ticket.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "feedback")
public class Feedback implements Serializable{

	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name = "purchase_rating", nullable = false)
	private int purchaseRating;
	
	
	public int getPurchaseRating() {
		return purchaseRating;
	}

	public void setPurchaseRating(int purchaseRating) {
		this.purchaseRating = purchaseRating;
	}

	public int getRefundRating() {
		return refundRating;
	}

	public void setRefundRating(int refundRating) {
		this.refundRating = refundRating;
	}

	public int getSupportRating() {
		return supportRating;
	}

	public void setSupportRating(int supportRating) {
		this.supportRating = supportRating;
	}

	@Column(name = "refund_rating", nullable = false)
	private int refundRating;
	
	
	@Column(name = "support_rating", nullable = false)
	private int supportRating;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional=true)
	@JsonIgnoreProperties({"feedbacks","queries"})
	@JoinColumn(name="user_name",referencedColumnName = "name")
	private User user;
}
