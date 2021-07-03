package com.capstone.ticket.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.capstone.ticket.model.Feedback;
import com.capstone.ticket.model.Query;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.FeedbackRepository;
import com.capstone.ticket.repository.UserRepository;

@RestController
public class FeedbackController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FeedbackRepository feedbackRepository;

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ModelAndView feedbackUser(@ModelAttribute("feedback") Feedback feedback,HttpSession session) {
		
		String name = (String) session.getAttribute("username");
		
		ModelAndView model = new ModelAndView();
		
		if(name!=null) {
			
			Optional<User> user = Optional.ofNullable(userRepository.findByName(name));
			
			Feedback f = new Feedback();
			
			f.setPurchaseRating(feedback.getPurchaseRating());
			f.setSupportRating(feedback.getSupportRating());
			f.setRefundRating(feedback.getRefundRating());
			f.setUser(user.get());
			
			feedbackRepository.saveAndFlush(f);
			return new ModelAndView("feedback_received");
		}else {
			
			return new ModelAndView("redirect:/login2");
		}
		
		
    }

}
