package com.capstone.ticket.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.capstone.ticket.model.Address;
import com.capstone.ticket.model.Query;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.QueryRepository;
import com.capstone.ticket.repository.UserRepository;

@RestController
public class QueryController {

	
	@Autowired
	QueryRepository queryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
    public Query queryUser(@RequestParam String name, @RequestParam String query) {
		
		System.out.println("Inside Post Query");
		Optional<User> user = Optional.ofNullable(userRepository.findByName(name));
		
		Query q = new Query();
		
		q.setQuery(query);
		q.setUser(user.get());
		
		System.out.print(user.get().getQueries());
		return queryRepository.saveAndFlush(q);
        
    }
}
