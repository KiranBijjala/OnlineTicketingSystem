package com.capstone.ticket.controller;

import com.capstone.ticket.model.Passenger;
import com.capstone.ticket.model.Ticket;

import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.PassengerRepository;
import com.capstone.ticket.repository.TicketRepository;
import com.capstone.ticket.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@RestController
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PassengerRepository passengerRepository;

	@RequestMapping("/gettickets/{name}")
	public ModelAndView HomePage(@PathVariable String name, HttpSession session) {

		if (session.getAttribute("username")!=null && session.getAttribute("username").equals(name)) {
			List<Ticket> listTickets = ticketRepository.findByName(name);
			ModelAndView model = new ModelAndView("index");

			model.addObject("listTickets", listTickets);
			
			for(Ticket t : listTickets) {
				List<Passenger> p = t.getPassengers();
				System.out.print(p.toString());
				model.addObject("passengers",p);
			}
			
//			model.addObject("passengers",new Passenger());
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
			Passenger passengers=new Passenger();
			ModelAndView model = new ModelAndView("new_ticket");
			model.addObject("ticket", ticket);
			model.addObject("passengers",passengers);
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
	public ModelAndView saveTicket(@ModelAttribute("ticket") Ticket ticket,@ModelAttribute("passenger") Passenger passengers, HttpSession session) {

		String username = (String) session.getAttribute("username");
		
		ticket.setName(username);
		
		Optional<Passenger> p = Optional.ofNullable(passengerRepository.findByName(passengers.getName()));
		
		if(!p.isPresent()) {
			passengers.setTicket(ticket);
			passengerRepository.save(passengers);
		}
		
		List<Passenger> list = new ArrayList<>();
		list.add(passengers);
		if (session.getAttribute("username")!=null && ticket.getTravelDate().compareTo(ticket.getReturnDate())<=0) {
			ticket.setPassengers(list);
			ticketRepository.save(ticket);
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
	
	
	
	@RequestMapping("/ticket/{id}")
	public ModelAndView editTicket(@PathVariable(name = "id") Long id, HttpSession session) {
		
		
		String username = (String) session.getAttribute("username");
		
		if(username!=null) {
			Optional<Ticket> ticket = ticketRepository.findById(id);
			if (!ticket.isPresent()) {
				return new ModelAndView("redirect:/login2");
			}
			else {
				ModelAndView mav = new ModelAndView("edit_ticket");
				mav.addObject("ticket",ticket.get());
				mav.addObject("passenger",new Passenger());
				return mav;
			}
		}else {
			return new ModelAndView("redirect:/login2");
		}
		
			
	}

	@PostMapping("/ticket/{id}")
	public ModelAndView edit(@PathVariable(name = "id") Long id,@ModelAttribute("passenger") Passenger passenger, HttpSession session ) {

		String username = (String) session.getAttribute("username");
		
//		if(username!=null) {
			ModelAndView mav = new ModelAndView("edit_ticket");
			
			Optional<Ticket> ticket = ticketRepository.findById(id);
			mav.addObject("ticket",ticket.get());		
			Optional<List<Passenger>>existingPassengers = Optional.ofNullable(ticket.get().getPassengers());
			
			Optional<Passenger> p = Optional.ofNullable(passengerRepository.findByName(passenger.getName()));
			
			System.out.println("name"+passenger.getName());
			System.out.println(passenger.getPassengerContact());
			
			
			boolean list = existingPassengers.get().stream().filter(pass->pass.getName().equals(passenger.getName())).findFirst().isPresent();
			
			if(list){
				mav.addObject("error","Ticket already has this passenger");
				return mav;
				
			}else {
				if(!p.isPresent()) {
					
					passenger.setTicket(ticket.get());
					passengerRepository.save(passenger);
					System.out.println(passengerRepository.findAll());
					
				}
				passenger.setTicket(ticket.get());
				existingPassengers.get().add(passenger);
				System.out.println("Updated Ticket:"+existingPassengers.get());
				mav.addObject("passenger", passenger);
				return new ModelAndView("redirect:/gettickets/" + username);
			}
		
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
	
	@GetMapping(value = "/gettickets/{name}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Ticket> gettickets(@PathVariable String name) {
		
		User user = userRepository.findByName(name);
		return ticketRepository.findAll();
	}

}