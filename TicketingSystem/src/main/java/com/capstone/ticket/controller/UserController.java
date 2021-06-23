package com.capstone.ticket.controller;

import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

	 @Autowired
	 UserRepository userRepository;

	@PostMapping("/login")
    public String login(@RequestBody User user) {
        System.out.println("Body sent : " + user.getName());


        User existingUser = userRepository.findByName(user.getName());

        System.out.println(existingUser);
        if (existingUser == null) {
            System.out.println("User does not exist");
            return "Cannot login. User is not registered, first sign up..!!";
        }

        System.out.println("User logged in successfully");
        return "User logged in successfully";
    }

    @PutMapping("/updateprofile/{name}")
    public String updateUser(@PathVariable String name, @RequestBody User user) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        if (!existingUser.isPresent()) {
            System.out.println("User does not exist");
            return "Cannot Update the user";
        }

        existingUser.get().setName(user.getName());
        existingUser.get().setPassword(user.getPassword());
        existingUser.get().setAddress(user.getAddress());
        existingUser.get().setContact(user.getContact());

        System.out.println(existingUser.get());
        userRepository.saveAndFlush(existingUser.get());
        return "User Updated successfully";
    }


    @GetMapping(value="/getusers", produces = {MediaType.APPLICATION_JSON_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers(){

        System.out.println("Inside Get all Users");
        System.out.println(userRepository.findAll());
        return userRepository.findAll();

    }

}
