package com.capstone.ticket.service;

import com.capstone.ticket.model.Feedback;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.FeedbackRepository;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    public Feedback addFeedback(Feedback feedback, HttpSession session){
        String name = (String) session.getAttribute("username");
        Optional<User> user = Optional.ofNullable(userRepository.findByName(name));
        Feedback f = new Feedback();
        f.setPurchaseRating(feedback.getPurchaseRating());
        f.setSupportRating(feedback.getSupportRating());
        f.setRefundRating(feedback.getRefundRating());
        f.setUser(user.get());
        return feedbackRepository.saveAndFlush(f);
    }
}
