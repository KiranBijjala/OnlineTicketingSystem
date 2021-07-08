package com.capstone.ticket.controller;

import com.capstone.ticket.model.Feedback;
import com.capstone.ticket.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class FeedbackController {

	@Autowired
	FeedbackService feedbackService;

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ModelAndView feedbackUser(@ModelAttribute("feedback") Feedback feedback,HttpSession session) {
		String name = (String) session.getAttribute("username");
		if(name!=null) {
			feedbackService.addFeedback(feedback,session);
			return new ModelAndView("feedback_received");
		}else {
			return new ModelAndView("redirect:/login2");
		}
    }

}
