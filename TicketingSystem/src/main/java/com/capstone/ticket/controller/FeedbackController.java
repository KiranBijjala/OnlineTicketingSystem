package com.capstone.ticket.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.capstone.ticket.model.Query;
import com.capstone.ticket.model.User;

public class FeedbackController {
	
//	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
//    public Query queryUser(@RequestParam String name, @RequestParam String query) {
//		
//		System.out.println("Inside Post Query");
//		Optional<User> user = Optional.ofNullable(userRepository.findByName(name));
//		
//		Query q = new Query();
//		
//		q.setQuery(query);
//		q.setUser(user.get());
//		
//		System.out.print(user.get().getQueries());
//		return queryRepository.saveAndFlush(q);
//        
//    }

}
