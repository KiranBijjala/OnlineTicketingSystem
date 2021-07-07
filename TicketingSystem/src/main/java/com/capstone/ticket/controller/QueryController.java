package com.capstone.ticket.controller;

import com.capstone.ticket.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class QueryController {


	@Autowired
	QueryService queryService;

	@RequestMapping(value = "/query", method = RequestMethod.POST)
    public ModelAndView queryUser(@RequestParam String query, HttpSession session) {

		String name = (String) session.getAttribute("username");
		if(name!=null ) {
			queryService.addQuery(query,session);
			return new ModelAndView("query_received");
		}
		else {
			return new ModelAndView("redirect:/login2");
		}
        
    }

}
