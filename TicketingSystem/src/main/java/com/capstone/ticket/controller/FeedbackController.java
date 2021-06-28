package com.capstone.ticket.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.ticket.model.Feedback;
import com.capstone.ticket.model.Query;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.FeedbackRepository;
import com.capstone.ticket.repository.UserRepository;

@RestController
public class FeedbackController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public Feedback feedbackUser(@ModelAttribute("feedback") Feedback feedback, @RequestParam String name) {
		
		System.out.println("Inside Feedback Query");
		Optional<User> user = Optional.ofNullable(userRepository.findByName(name));
		
		Feedback f = new Feedback();
		
		f.setPurchaseRating(feedback.getPurchaseRating());
		f.setSupportRating(feedback.getSupportRating());
		f.setRefundRating(feedback.getRefundRating());
		f.setUser(user.get());
		

		
		System.out.println(user.get().getFeedbacks());
		
		return feedbackRepository.saveAndFlush(f);
        
    }

}
