package com.capstone.ticket.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

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
    public ModelAndView queryUser(@RequestParam String query, HttpSession session) {
		
		String name = (String) session.getAttribute("username");
		
		if(name!=null ) {
			
			
			Optional<User> user = Optional.ofNullable(userRepository.findByName(name));
			Query q = new Query();
			
			q.setQuery(query);
			q.setUser(user.get());
			
			queryRepository.saveAndFlush(q);
			
			return new ModelAndView("query_received");
		}
		else {
			return new ModelAndView("redirect:/login2");
		}
        
    }

}
