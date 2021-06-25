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

	 @PostMapping(value="/addticket", produces ={MediaType.APPLICATION_JSON_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE})
	 public Ticket addTicket(@RequestBody Ticket ticket,@RequestParam Optional<String> passenger){
		 if(!passenger.isPresent()){
			 return ticketRepository.save(ticket);
		 }
		 System.out.println(ticket.toString());
		 System.out.println("Hi");
		 StringBuilder sb = new StringBuilder();
		 System.out.println(passenger.get());
		 sb = sb.append(ticket.getName()).append(",").append(passenger.get());
		 System.out.println("String: " + sb.toString());
		 ticket.setName(sb.toString());
		 
		 ticket.setPassengers(passenger.get());
		 return ticketRepository.save(ticket);
	
	 }

	// @GetMapping(value="/gettickets", produces =
	// {MediaType.APPLICATION_JSON_VALUE},consumes=
	// {MediaType.APPLICATION_JSON_VALUE})
	// public List<Ticket> getTickets(){
	//
	// System.out.println("Inside Get all Tickets");
	// return ticketRepository.findAll();
	//
	// }

	// @RequestMapping("/")
	// public ModelAndView viewHomePage() {
	// List<Ticket> listTickets = ticketRepository.findAll();
	//
	// System.out.println(listTickets);
	// ModelAndView model = new ModelAndView("index");
	//
	// model.addObject("listTickets",listTickets);
	// return model;
	//
	// }

	@RequestMapping("/gettickets/{name}")
	public ModelAndView HomePage(@PathVariable String name) {
		List<Ticket> listTickets = ticketRepository.findByName(name);
		System.out.println(listTickets);
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

	@RequestMapping(value = "/save", method = RequestMethod.POST,params= "action=submit")
	public ModelAndView saveTicket(@ModelAttribute("ticket") Ticket ticket) {
		
		ticketRepository.saveAndFlush(ticket);
		String name = ticket.getName();
		System.out.println(ticket);
		System.out.println("name" + name);

		return new ModelAndView("redirect:/gettickets/" + name);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "action=cancel")
	public ModelAndView cancel(@ModelAttribute("ticket") Ticket ticket, 
	  final BindingResult result) {
	
//	    model.addAttribute("message", "You clicked cancel, please re-enter employee details:");
	    ModelAndView model = new ModelAndView("new_ticket");
		model.addObject("ticket", ticket);

		return model;
	}

	// @DeleteMapping(value="/deleteticket/{id}", produces =
	// {MediaType.APPLICATION_JSON_VALUE},consumes=
	// {MediaType.APPLICATION_JSON_VALUE})
	// public String deleteticket(@PathVariable Long id){
	//
	// System.out.println("Inside Get all Tickets");
	//
	// Optional<Ticket> t = ticketRepository.findById(id);
	//
	// if(t.isPresent()){
	// ticketRepository.delete(t.get());
	// return "Ticket SuccessFully Deleted";
	// }
	// else{
	// return "Ticket not Found";
	// }
	//
	// }

	@RequestMapping("/edit/{id}")
	public ModelAndView editTicket(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_ticket");
		Optional<Ticket> ticket = ticketRepository.findById(id);

		if (ticket.isPresent()) {
			System.out.println("Ticket Not found");
		}
		mav.addObject("ticket", ticket);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView deleteTicket(@PathVariable(name = "id") Long id) {

		Optional<Ticket> t = ticketRepository.findById(id);

		if (!t.isPresent()) {
			System.out.println("Ticket Not found");
		}
		ticketRepository.delete(t.get());
		return new ModelAndView("redirect:/gettickets/" + t.get().getName());
	}

}