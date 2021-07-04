package com.capstone.ticket.controller;

import com.capstone.ticket.model.Address;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.QueryRepository;
import com.capstone.ticket.repository.TicketRepository;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	QueryRepository queryRepository;
	
	@Autowired 
	TicketRepository ticketRepository;

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
	public ModelAndView loginUser(@ModelAttribute("user") User user,
			HttpSession session, ModelMap modelMap) {

		Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(user.getName()));

		ModelAndView model = new ModelAndView("login2");

		if (existingUser.isPresent() && encoder().matches(user.getPassword(), existingUser.get().getPassword())) {
			session.setAttribute("username", user.getName());
			model.addObject("user", user);
			model.setStatus(HttpStatus.OK);
			return new ModelAndView("redirect:/tickets");
			
		}else if(!existingUser.isPresent()){
			model.addObject("error", "User Doesn't Exist, Please sign up");
			return model;
		}
		else {
			model.addObject("error", "Invalid Credentials");
			return model;
		}

	}

	
	
	@RequestMapping("/logout2")
	public ModelAndView logout(HttpSession session) {

		session.removeAttribute("username");
    	session.invalidate();
		return new ModelAndView("redirect:/login2");
	}
	
	

	@RequestMapping("/signup")
	public ModelAndView signUp() {
		ModelAndView model = new ModelAndView("signup");
		model.addObject("user", new User());
		model.addObject("address", new Address());

		return model;
	}
	
	
	
	
	
	@RequestMapping(value = "/signupuser", method = RequestMethod.POST)
	public ModelAndView signUpUser(@ModelAttribute("user") User user1,@ModelAttribute("address") Address address, HttpSession session) {

		Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(user1.getName()));
		
		ModelAndView model = new ModelAndView("signup");


		
		if (existingUser.isPresent()) {
			model.addObject("error","User exists, Please Login");
			return model;

		}else {
			
			User user = new User();
			String encodedPassword = encoder().encode(user1.getPassword());
			
			Address address1 = new Address();
			
			
			address1.setStreet(address.getStreet());
			address1.setCity(address.getCity());
			address1.setState(address.getState());
			address1.setZip(address.getZip());
			
			user.setName(user1.getName());
			user.setPassword(encodedPassword);
			user.setAddress(address1);
			user.setContactNumber(user1.getContactNumber());

			session.setAttribute("address", address1);
			session.setAttribute("contactNumber", user1.getContactNumber());
			
			model.addObject("user",user);
			model.addObject("address",address1);
			userRepository.save(user);
			return new ModelAndView("redirect:/login2");
		}
		
	}



	@RequestMapping("/updateprofile")
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

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public ModelAndView updateUser(@RequestParam("password") String password, @RequestParam("street") String street,
			@RequestParam("city") String city, @RequestParam("state") String state, @RequestParam("zip") String zip,
			@RequestParam("contactNumber") String contactNumber,Model model , HttpSession session) {

   	
		String username = (String) session.getAttribute("username");
		
		
		Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(username));

		Address address = new Address();
		String encodedPassword = encoder().encode(password);

		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZip(zip);

		existingUser.get().setPassword(encodedPassword);
		existingUser.get().setAddress(address);
		existingUser.get().setContactNumber(contactNumber);

		userRepository.saveAndFlush(existingUser.get());
		
		return new ModelAndView("redirect:gettickets/" + username);
	}
	
	

	@GetMapping(value = "/getusers", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<User> getUsers() {
		
		return userRepository.findAll();
	}
	
	

	@GetMapping(value = "/getuser/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public User getUser(@PathVariable long id) {

		Optional<User> user = userRepository.findById(id);
		return user.get();
	}

}
