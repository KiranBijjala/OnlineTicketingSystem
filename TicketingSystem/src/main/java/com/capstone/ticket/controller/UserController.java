package com.capstone.ticket.controller;

import com.capstone.ticket.model.Address;
import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Bean 
    public PasswordEncoder encoder() {
    	return new BCryptPasswordEncoder();
    }
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
    public ModelAndView loginUser() {
        User user = new User();
        ModelAndView model = new ModelAndView("login2");
        model.addObject("user", user);

        return model;
    }

    @RequestMapping(value = "/loginuser", method = RequestMethod.POST)
    public ModelAndView loginUser(@RequestParam("name") String name, @RequestParam("password") String password) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        ModelAndView model = new ModelAndView();
        if (!existingUser.isPresent()) {
            System.out.println("User does not exist");
            model.setStatus(HttpStatus.NOT_FOUND);
            model.setViewName("redirect:login2");
            return model;
            
        }else if (!encoder().matches(password, existingUser.get().getPassword())) {
        	model.setStatus(HttpStatus.UNAUTHORIZED);
            System.out.println("User exists but password is wrong");
            model.setViewName("redirect:login2");
            return model;

         }
        System.out.println("User logged in successfully");
    	model.setStatus(HttpStatus.OK);
        model.setViewName("redirect:gettickets/" + name);
        return model;	
        
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
            @RequestParam("street") String street,@RequestParam("city") String city,@RequestParam("state") String state, @RequestParam("zip") String zip, @RequestParam("contactNumber") String contactNumber) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        if (existingUser.isPresent()) {
            System.out.println("User exists, Please Login");
            
        }
        User user = new User();
        Address address = new Address();
        String encodedPassword = encoder().encode(password);
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        
        user.setName(name);
        user.setPassword(encodedPassword);
        user.setAddress(address);
        user.setContactNumber(contactNumber);

        userRepository.save(user);
        return new ModelAndView("redirect:gettickets/" + name);
    }
    
    
    

    @RequestMapping("/updateprofile")
    public ModelAndView update() {
        User user = new User();
        ModelAndView model = new ModelAndView("update");
        
        model.addObject("user", user);
        model.addObject("address", new Address());

        return model;
    }

    @RequestMapping(value = "/updateuser/{name}", method = RequestMethod.POST)
    public ModelAndView updateUser(@PathVariable String name, @RequestParam("name") String newname,
            @RequestParam("password") String password, @RequestParam("street") String street,@RequestParam("city") String city,@RequestParam("state") String state, @RequestParam("zip") String zip, @RequestParam("contactNumber") String contactNumber) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        if (!existingUser.isPresent()) {
            System.out.println("User does not exist");

        }
        
        Address address = new Address();
        
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        
        existingUser.get().setName(newname);
        existingUser.get().setPassword(password);
        existingUser.get().setAddress(address);
        existingUser.get().setContactNumber(contactNumber);

        userRepository.saveAndFlush(existingUser.get());
        System.out.println("User updated successfully");

        return new ModelAndView("redirect:gettickets/" + name);
    }

    // @PutMapping("/updateprofile/{name}")
    // public String updateUser(@PathVariable String name, @RequestBody User user) {

    // Optional<User> existingUser =
    // Optional.ofNullable(userRepository.findByName(name));

    // if (!existingUser.isPresent()) {
    // System.out.println("User does not exist");
    // return "Cannot Update the user";
    // }

    // existingUser.get().setName(user.getName());
    // existingUser.get().setPassword(user.getPassword());
    // existingUser.get().setAddress(user.getAddress());
    // existingUser.get().setContact(user.getContact());

    // System.out.println(existingUser.get());
    // userRepository.saveAndFlush(existingUser.get());
    // return "User Updated successfully";
    // }

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
