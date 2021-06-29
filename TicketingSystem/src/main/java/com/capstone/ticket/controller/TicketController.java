package com.capstone.ticket.controller;

import com.capstone.ticket.model.Ticket;

import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;

	@PostMapping(value = "/addticket", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Ticket addTicket(@RequestBody Ticket ticket, @RequestParam Optional<String> passenger) {
		if (!passenger.isPresent()) {
			return ticketRepository.save(ticket);
		}
		StringBuilder sb = new StringBuilder();
		sb = sb.append(ticket.getName()).append(",").append(passenger.get());
		ticket.setName(sb.toString());

		ticket.setPassengers(passenger.get());
		return ticketRepository.save(ticket);

	}

	@RequestMapping("/gettickets/{name}")
	public ModelAndView HomePage(@PathVariable String name) {
		List<Ticket> listTickets = ticketRepository.findByName(name);
		ModelAndView model = new ModelAndView("index");

		model.addObject("listTickets", listTickets);
		return model;

	}

	@RequestMapping("/addticket")
	public ModelAndView addTicket() {
		Ticket ticket = new Ticket();
		ModelAndView model = new ModelAndView("new_ticket");
		model.addObject("ticket", ticket);

		return model;
	}

	@RequestMapping("/checkout")
	public ModelAndView checkout() {
		Ticket ticket = new Ticket();
		ModelAndView model = new ModelAndView("checkout");
		model.addObject("ticket", ticket);

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveTicket(@ModelAttribute("ticket") Ticket ticket) {

		if (ticket.getTravelDate().compareTo(ticket.getReturnDate()) > 0) {
			System.out.println("Ticket");

		}

		ticketRepository.saveAndFlush(ticket);
		String name = ticket.getName();

		return new ModelAndView("redirect:/gettickets/" + name);

	}

	@RequestMapping("/edit/{id}")
	public ModelAndView editTicket(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_ticket");
		Optional<Ticket> ticket = ticketRepository.findById(id);

		mav.addObject("ticket", ticket);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView deleteTicket(@PathVariable(name = "id") Long id) {

		Optional<Ticket> t = ticketRepository.findById(id);

		ticketRepository.delete(t.get());
		return new ModelAndView("redirect:/gettickets/" + t.get().getName());
	}

}