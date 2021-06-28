package com.capstone.ticket.controller;

import com.capstone.ticket.model.Address;
import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    
//    @Autowired
//    Address address;

    // @PostMapping("/signup")
    // public String signup(@RequestBody User user) {
    // System.out.println("Inside Signup");
    // System.out.println(user.getName());

    // Optional<User> existingUser =
    // Optional.ofNullable(userRepository.findByName(user.getName()));
    // if(existingUser.isPresent()){
    // System.out.println("User already present. Please proceed to login");
    // }
    // userRepository.save(user);

    // return "Sign up successful";
    // }


    @RequestMapping("/")
    public ModelAndView viewHomePage() {
        ModelAndView model = new ModelAndView("index1");

        return model;

    }

    @RequestMapping("/login2")
    public ModelAndView login() {
        User user = new User();
        ModelAndView model = new ModelAndView("login2");
        model.addObject("user", user);

        return model;
    }

    @RequestMapping(value = "/loginuser", method = RequestMethod.POST)
    public ModelAndView loginUser(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session,ModelMap modelMap) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        ModelAndView model = new ModelAndView();
//        if (!existingUser.isPresent()) {
//            System.out.println("User does not exist");
//            model.setStatus(HttpStatus.NOT_FOUND);
//            model.setViewName("redirect:login");
//            return model;
//            
//        }else if (!existingUser.get().getPassword().equals(password)) {
//        	model.setStatus(HttpStatus.UNAUTHORIZED);
//            System.out.println("User exists but password is wrong");
//            modelMap.put("error", "Invalid Account");
//            model.setViewName("redirect:login");
//            return model;
//
//         }
//        session.setAttribute("username", name);
//        System.out.println("User logged in successfully");
//    	model.setStatus(HttpStatus.OK);
//        model.setViewName("redirect:gettickets/" + name);
//        return model;	
        
        if(existingUser.isPresent() && existingUser.get().getPassword().equals(password)) {
        	session.setAttribute("username", name);
            System.out.println("User logged in successfully");
        	model.setStatus(HttpStatus.OK);
            model.setViewName("redirect:gettickets/" + name);
            return model;	
        }else {
        	model.setStatus(HttpStatus.UNAUTHORIZED);
	          System.out.println("User exists but password is wrong");
	          modelMap.put("error", "Invalid Account");
	          model.addObject("error","Invalid Credentials");
	          model.setViewName("redirect:login");
	          return model;
        }
        
    }
    
    
    
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	session.removeAttribute("username");
    	session.invalidate();
		return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/signup")
    public ModelAndView signUp() {
        User user = new User();
//        Address address = new Address();
//        user.setAddress(address);
//        Address address = new Address();
        ModelAndView model = new ModelAndView("signup");
        model.addObject("user", user);
        model.addObject("address", new Address());

        return model;
    }

    @RequestMapping(value = "/signupuser", method = RequestMethod.POST)
    public ModelAndView signUpUser(@RequestParam("name") String name, @RequestParam("password") String password,
            @RequestParam("street") String street,@RequestParam("city") String city,@RequestParam("state") String state, @RequestParam("zip") String zip, @RequestParam("contactNumber") String contactNumber, HttpSession session) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        if (existingUser.isPresent()) {
            System.out.println("User exists, Please Login");
            
        }
        User user = new User();
        Address address = new Address();
        
        
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        
        
        user.setName(name);
        user.setPassword(password);
        user.setAddress(address);
        user.setContactNumber(contactNumber);
        
        session.setAttribute("address", address);
        session.setAttribute("contactNumber",contactNumber);

        userRepository.save(user);
        return new ModelAndView("redirect:login2" );
    }
    
    
    

    @RequestMapping("/updateprofile")
    public ModelAndView update() {
    	
    	
    	System.out.println("Inside Update");
        ModelAndView model = new ModelAndView("update");
//        model.addObject("name",name);
        model.addObject("user", new User());
        model.addObject("address", new Address());

        return model;
    }
    
   
    
    

    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)
    public ModelAndView updateUser( @RequestParam("name") String name,
            @RequestParam("password") String password, @RequestParam("street") String street,@RequestParam("city") String city,@RequestParam("state") String state, @RequestParam("zip") String zip, @RequestParam("contactNumber") String contactNumber, HttpSession session) {
    	
    	
    	System.out.println(session.getAttribute("username"));
    	
    	String username = (String) session.getAttribute("username");
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(username));

        if (!existingUser.isPresent()) {
            System.out.println("User does not exist");

        }
        
        Address address = new Address();
        
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        
        existingUser.get().setName(name);
        existingUser.get().setPassword(password);
        existingUser.get().setAddress(address);
        existingUser.get().setContactNumber(contactNumber);

        userRepository.saveAndFlush(existingUser.get());
        System.out.println("User updated successfully");

        return new ModelAndView("redirect:gettickets/" + name);
    }

    

     @GetMapping(value = "/getusers", produces = {MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_JSON_VALUE })
     public List<User> getUsers() {
	     System.out.println("Inside Get all Users");
	     System.out.println(userRepository.findAll());
	     return userRepository.findAll();
     }
     
     
     @GetMapping(value = "/getuser/{id}", produces = {MediaType.APPLICATION_JSON_VALUE }, consumes = {MediaType.APPLICATION_JSON_VALUE })
     public User getUser(@PathVariable long id) {
    	 
    	 Optional<User> user = userRepository.findById(id);
    	 
    	 if(!user.isPresent()) {
    		 System.out.println("User not Found");
    	    
    	 }
	     return user.get();
     }

}
