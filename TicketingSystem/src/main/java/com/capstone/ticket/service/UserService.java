package com.capstone.ticket.service;

import com.capstone.ticket.model.Address;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	 @Autowired
	 UserRepository userRepository;


    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


	 public User registerUser(User user1, Address address, HttpSession session){
//         User user = new User();
         String encodedPassword = encoder().encode(user1.getPassword());
         ModelAndView model = new ModelAndView();
         Address address1 = new Address();

         address1.setStreet(address.getStreet());
         address1.setCity(address.getCity());
         address1.setState(address.getState());
         address1.setZip(address.getZip());

//         user.setName(user1.getName());
         user1.setPassword(encodedPassword);
         user1.setAddress(address1);
//         user.setContactNumber(user1.getContactNumber());

         session.setAttribute("address", address1);
         session.setAttribute("contactNumber", user1.getContactNumber());

         model.addObject("user",user1);
         model.addObject("address",address1);

         return userRepository.save(user1);
     }


     public User updateUser( String password,String street,String city,String state,String zip,String contactNumber,HttpSession session){
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

         return userRepository.saveAndFlush(existingUser.get());
     }


     public List<User> getUsersList(){
         return userRepository.findAll();
     }

}
