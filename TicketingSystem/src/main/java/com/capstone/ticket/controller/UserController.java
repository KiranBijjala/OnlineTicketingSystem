package com.capstone.ticket.controller;

import com.capstone.ticket.exceptions.NotFoundException;
import com.capstone.ticket.model.Address;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.UserRepository;
import com.capstone.ticket.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@RequestMapping("/")
	public ModelAndView viewHomePage(HttpSession session) {


		String name = (String) session.getAttribute("username");
		if (session.getAttribute("username") != null) {
			return new ModelAndView("redirect:/tickets");
		}
		else {
			ModelAndView model = new ModelAndView("index1");
			return model;
		}
	}


	@GetMapping("/login2")
	public ModelAndView login(HttpSession session) {
		logger.info("Inside Login Method");
		String name = (String) session.getAttribute("username");
		if (session.getAttribute("username") != null) {
			return new ModelAndView("redirect:/tickets");
		}else{
			User user = new User();
			ModelAndView model = new ModelAndView("login2");
			model.addObject("user", user);
			return model;
		}
	}


	@PostMapping(value = "/login2")
	public ModelAndView loginUser(@ModelAttribute("user") User user, HttpSession session) {

		// Find the user using username
		Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(user.getName()));
		ModelAndView model = new ModelAndView("login2");

		//check if the request parameters matches the user data
		if (existingUser.isPresent() && encoder().matches(user.getPassword(), existingUser.get().getPassword())) {
			logger.trace("Login Successful");
			session.setAttribute("username", user.getName());
			model.addObject("user", user);
			model.setStatus(HttpStatus.OK);
			return new ModelAndView("redirect:/tickets");
		}
		else {
			logger.error("Error 404 : Invalid Credentials");
			model.setStatus(HttpStatus.NOT_FOUND);
			model.addObject("error", "Invalid Credentials");
			return model;
		}
	}

	
	@RequestMapping("/logout2")
	public ModelAndView logout(HttpSession session) {
		session.removeAttribute("username");
    	session.invalidate();
    	logger.info("Logout Success");
		return new ModelAndView("redirect:/login2");
	}
	

	@GetMapping("/signup")
	public ModelAndView signUp() {

		ModelAndView model = new ModelAndView("signup");
		model.addObject("user", new User());
		model.addObject("address", new Address());
		return model;
	}



	@PostMapping(value = "/signup")
	public ModelAndView signUpUser(@ModelAttribute("user") User user1,@ModelAttribute("address") Address address, HttpSession session) {

		// find the user using username
		Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(user1.getName()));

		ModelAndView model = new ModelAndView("signup");


		// check if user already exists
		if (existingUser.isPresent()) {
			logger.info("Invalid user name or password");
			model.addObject("error","Invalid Credentials");
			return model;

		}else {
			logger.info("User registration Successful");
			userService.registerUser(user1,address,session);
			return new ModelAndView("redirect:/login2");
		}

	}



	@GetMapping("/user")
	public ModelAndView update(HttpSession session) {

		if (session.getAttribute("username") == null || session.getAttribute("username").equals("")) {

			return new ModelAndView("redirect:/login2");
		} else {
		
			ModelAndView model = new ModelAndView("update");
			model.addObject("user", new User());

			model.addObject("address", new Address());

			return model;
		}

	}


	@PostMapping(value = "/user")
	public ModelAndView updateUser(@RequestParam("password") String password, @RequestParam("street") String street,
								   @RequestParam("city") String city, @RequestParam("state") String state, @RequestParam("zip") String zip,
								   @RequestParam("contactNumber") String contactNumber, HttpSession session) {

		logger.info("Inside User Updation");
		userService.updateUser(password, street, city, state, zip, contactNumber, session);
		return new ModelAndView("redirect:tickets" );
	}
	
	

	@GetMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<User> getUsers() {
		
		return userService.getUsersList();
	}
	
	

	@GetMapping(value = "/user/{name}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public User getUser(@PathVariable String name) {

		logger.info("Inside User Method");
		User user = userRepository.findByName(name);

//		if(user==null) throw new NotFoundException("Invalid User name " + name);
		if(user==null) throw new NotFoundException("Invalid User name " + name);
		return userRepository.findByName(name);
	}



}
