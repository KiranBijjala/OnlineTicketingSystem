package com.capstone.ticket.service;

import com.capstone.ticket.model.Query;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.QueryRepository;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class QueryService {

    @Autowired
    QueryRepository queryRepository;

    @Autowired
    UserRepository userRepository;

    public Query addQuery(String query, HttpSession session){
        String name = (String) session.getAttribute("username");

        Optional<User> user = Optional.ofNullable(userRepository.findByName(name));
        Query q = new Query();

        q.setQuery(query);
        q.setUser(user.get());

        return queryRepository.saveAndFlush(q);
    }
}
