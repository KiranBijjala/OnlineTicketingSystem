package com.capstone.ticket.controller;

import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

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

    // @PostMapping("/login")
    // public String login(@RequestBody User user) {
    //
    // Optional<User> existingUser =
    // Optional.ofNullable(userRepository.findByName(user.getName()));
    //
    //
    // if(!existingUser.isPresent()) {
    // System.out.println("User does not exist");
    // return "Cannot login. User is not registered, first sign up..!!";
    // }else{
    // if(!existingUser.get().getPassword().equals(user.getPassword())){
    // System.out.println("User exists but password is wrong");
    // return "Invalid Credentials!!";
    // }
    //
    // }
    // System.out.println("User logged in successfully");
    // return "User logged in successfully";
    //
    //
    // }

    @RequestMapping("/")
    public ModelAndView viewHomePage() {
        ModelAndView model = new ModelAndView("index1");

        return model;

    }

    @RequestMapping("/login")
    public ModelAndView loginUser() {
        User user = new User();
        ModelAndView model = new ModelAndView("login");
        model.addObject("user", user);

        return model;
    }

    @RequestMapping(value = "/loginuser", method = RequestMethod.POST)
    public ModelAndView loginUser(@RequestParam("name") String name, @RequestParam("password") String password) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        if (!existingUser.isPresent()) {
            System.out.println("User does not exist");

        } else {
            if (!existingUser.get().getPassword().equals(password)) {
                System.out.println("User exists but password is wrong");

            }

        }
        System.out.println("User logged in successfully");

        return new ModelAndView("redirect:gettickets/" + name);
    }

    @RequestMapping("/signup")
    public ModelAndView signUp() {
        User user = new User();
        ModelAndView model = new ModelAndView("signup");
        model.addObject("user", user);

        return model;
    }

    @RequestMapping(value = "/signupuser", method = RequestMethod.POST)
    public ModelAndView signUpUser(@RequestParam("name") String name, @RequestParam("password") String password,
            @RequestParam("address") String address, @RequestParam("contact") String contact) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        if (existingUser.isPresent()) {
            System.out.println("User exists, Please Login");

        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setAddress(address);
        user.setContact(contact);

        userRepository.save(user);
        return new ModelAndView("redirect:gettickets/" + name);
    }

    @RequestMapping("/updateprofile")
    public ModelAndView update() {
        User user = new User();
        ModelAndView model = new ModelAndView("update");
        model.addObject("user", user);

        return model;
    }

    @RequestMapping(value = "/updateuser/{name}", method = RequestMethod.PUT)
    public ModelAndView updateUser(@PathVariable String name, @RequestParam("name") String newname,
            @RequestParam("password") String password, @RequestParam("address") String address,
            @RequestParam("contact") String contact) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByName(name));

        if (!existingUser.isPresent()) {
            System.out.println("User does not exist");

        }
        existingUser.get().setName(newname);
        existingUser.get().setPassword(password);
        existingUser.get().setAddress(address);
        existingUser.get().setContact(contact);

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

    // @GetMapping(value = "/getusers", produces = {
    // MediaType.APPLICATION_JSON_VALUE }, consumes = {
    // MediaType.APPLICATION_JSON_VALUE })
    // public List<User> getUsers() {

    // System.out.println("Inside Get all Users");
    // System.out.println(userRepository.findAll());
    // return userRepository.findAll();

    // }

}
