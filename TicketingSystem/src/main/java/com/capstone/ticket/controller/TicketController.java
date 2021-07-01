package com.capstone.ticket.controller;

import com.capstone.ticket.model.Ticket;

import com.capstone.ticket.repository.TicketRepository;
import com.capstone.ticket.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@RestController
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/gettickets/{name}")
	public ModelAndView HomePage(@PathVariable String name, HttpSession session) {

		if (session.getAttribute("username")!=null && session.getAttribute("username").equals(name)) {
			List<Ticket> listTickets = ticketRepository.findByName(name);
			ModelAndView model = new ModelAndView("index");

			model.addObject("listTickets", listTickets);
			return model;
		} else {
			return new ModelAndView("redirect:/login2");
		}

	}

	@RequestMapping("/addticket")
	public ModelAndView addTicket(HttpSession session) {

		if (session.getAttribute("username") == null || session.getAttribute("username").equals("")) {

			return new ModelAndView("redirect:/login2");
		}else {
			Ticket ticket = new Ticket();
			ModelAndView model = new ModelAndView("new_ticket");
			model.addObject("ticket", ticket);

			return model;
		}
		
	}

	@RequestMapping("/checkout")
	public ModelAndView checkout() {
		Ticket ticket = new Ticket();
		ModelAndView model = new ModelAndView("checkout");
		model.addObject("ticket", ticket);

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveTicket(@ModelAttribute("ticket") Ticket ticket, HttpSession session) {

		String username = (String) session.getAttribute("username");
		
		ticket.setName(username);
		
		
		if (session.getAttribute("username")!=null && ticket.getTravelDate().compareTo(ticket.getReturnDate())<=0) {
			ticketRepository.saveAndFlush(ticket);
			String name = ticket.getName();

			return new ModelAndView("redirect:/gettickets/" + name);
		}else if(ticket.getTravelDate().compareTo(ticket.getReturnDate())>0){
			ModelAndView model = new ModelAndView("new_ticket");
			model.addObject("error","Invalid Ticket Dates");
			return model;
		}else {
			return new ModelAndView("redirect:/login2");
		}


	}

	@RequestMapping("/edit/{id}")
	public ModelAndView editTicket(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_ticket");
		Optional<Ticket> ticket = ticketRepository.findById(id);

		if (!ticket.isPresent()) {
			return new ModelAndView("redirect:/login2");
		}
		mav.addObject("ticket", ticket);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView deleteTicket(@PathVariable(name = "id") Long id) {

		Optional<Ticket> t = ticketRepository.findById(id);

		if (!t.isPresent()) {
			return new ModelAndView("redirect:/login2");
		}
		ticketRepository.delete(t.get());
		return new ModelAndView("redirect:/gettickets/" + t.get().getName());
	}
	
	@RequestMapping("/billing")

	public ModelAndView billing() {

	Ticket ticket = new Ticket();

	ModelAndView model = new ModelAndView("billing");

	model.addObject("ticket", ticket);
	
	 return model;

	}

}