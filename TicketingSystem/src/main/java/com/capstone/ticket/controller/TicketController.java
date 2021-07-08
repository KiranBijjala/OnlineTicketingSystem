package com.capstone.ticket.controller;

import com.capstone.ticket.model.Passenger;
import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.repository.PassengerRepository;
import com.capstone.ticket.repository.TicketRepository;
import com.capstone.ticket.repository.UserRepository;
import com.capstone.ticket.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {

	Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	TicketService ticketService;

	@GetMapping("/tickets")
	public ModelAndView HomePage(HttpSession session) {

		String username = (String) session.getAttribute("username");
		if (session.getAttribute("username")!=null) {
			logger.info("Inside User Tickets");
			Optional<List<Ticket>> listTickets = ticketRepository.findTicketsByName(username);
			ModelAndView model = new ModelAndView("index");

			model.addObject("listTickets", listTickets.get());
			model.addObject("user",username);
			for(Ticket t : listTickets.get()) {
				List<Passenger> p = t.getPassengers();
				model.addObject("passengers",p);
			}
//			model.addObject("passengers",new Passenger());
			return model;
		} else {
			logger.error("User not logged in");
			return new ModelAndView("redirect:/login2");
		}

	}


	@GetMapping("/tickets/cancelled")
	public ModelAndView cancelledTickets(HttpSession session) {

		logger.info("Inside Cancelled Tickets ");
		String username = (String) session.getAttribute("username");
		if (session.getAttribute("username")!=null) {
			Optional<List<Ticket>> listTickets = ticketRepository.findTicketsByStatus(username);
			ModelAndView model = new ModelAndView("cancel_tickets");

			model.addObject("listTickets", listTickets.get());
			return model;
		} else {
			return new ModelAndView("redirect:/login2");
		}

	}

	@GetMapping("/ticket")
	public ModelAndView addTicket(HttpSession session) {

		String username = (String) session.getAttribute("username");
		if (session.getAttribute("username") == null || session.getAttribute("username").equals("")) {

			return new ModelAndView("redirect:/login2");
		}else {
			Ticket ticket = new Ticket();
			Passenger passengers=new Passenger();
			ModelAndView model = new ModelAndView("new_ticket");
			model.addObject("ticket", ticket);
			model.addObject("user",username);
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


	@PostMapping(value = "/ticket")
	public ModelAndView saveTicket(@ModelAttribute("ticket") Ticket ticket,@ModelAttribute("passenger") Passenger passengers, HttpSession session) {

		ModelAndView model = new ModelAndView("new_ticket");
		if (session.getAttribute("username")!=null){
			if(ticket.getTravelDate().compareTo(ticket.getReturnDate())>0) {
				logger.error("Invalid Ticket Fields");
				model.addObject("error", "one or more Invalid Ticket fields");
				model.addObject("passengers",passengers);
				return model;
			}
			else{
				logger.info("Inside Ticket creation : Ticket Successfully Booked");
				ticketService.createTicket(ticket,passengers,session);
				model.addObject("ticket",ticket);
				model.addObject("passengers",passengers);
				return new ModelAndView("redirect:/tickets");
			}
		}else{
			return new ModelAndView("redirect:/login2");
		}

	}



//	@PostMapping(value = "/ticket",consumes = "application/x-www-form-urlencoded")
//	public ModelAndView saveTicket(@ModelAttribute Ticket ticket, @ModelAttribute Passenger passenger1, @ModelAttribute Passenger passenger2, @ModelAttribute Passenger passenger3,HttpSession session) throws IOException {
//
//		String username = (String) session.getAttribute("username");
//		System.out.println(ticket);
//		User user = userRepository.findByName(username);
//
//		System.out.println(ticket.toString());
//		ticket.setName(username);
//		ticket.setUser(user);
//
//		Optional<Passenger> p1 = Optional.ofNullable(passengerRepository.findByName(passenger1.getName()));
//		Optional<Passenger> p2 = Optional.ofNullable(passengerRepository.findByName(passenger2.getName()));
//		Optional<Passenger> p3 = Optional.ofNullable(passengerRepository.findByName(passenger3.getName()));
//
//
//		if(!p1.isPresent()){
//			passenger1.setTicket(ticket);
//			passengerRepository.save(passenger1);
//		}
//
//
//		if(!p2.isPresent()){
//			passenger2.setTicket(ticket);
//			passengerRepository.save(passenger2);
//		}
//
//		if(!p3.isPresent()){
//			passenger3.setTicket(ticket);
//			passengerRepository.save(passenger3);
//		}
//
//		List<Passenger> passengers = new ArrayList<>();
//
//		passengers.add(passenger1);
//		passengers.add(passenger2);
//		passengers.add(passenger3);
//
//
//		System.out.println("passengers"+passengers);
//
//
////		List<Passenger> list = new ArrayList<>();
////		list.add(passengers);
//		System.out.println(ticket.getTravelDate().compareTo(ticket.getReturnDate()));
//		String depart=ticket.getDepartureLocation();
//		String destiny = ticket.getDestinationLocation();
//		System.out.println("depart"+depart);
//		System.out.println("destiny"+destiny);
//		System.out.println("ticket : "+ !depart.equals(destiny));
//		if (session.getAttribute("username")!=null){
//			if(ticket.getTravelDate().compareTo(ticket.getReturnDate())>0 || depart.equals(destiny)) {
//				ModelAndView model = new ModelAndView("new_ticket");
//				model.addObject("error", "one or more Invalid Ticket fields");
//				return model;
////				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//			}
//			else{
//				System.out.println("ticket1 :"+ticket);
//				ModelAndView model = new ModelAndView("new_ticket") ;
//				ticket.setStatus("Active");
//				ticket.setPassengers(passengers);
//				ticketRepository.save(ticket);
//				model.addObject("ticket",ticket);
//				model.addObject("passengers1",passenger1);
//				model.addObject("passengers2",passenger2);
//				model.addObject("passengers3",passenger3);
//				String name = ticket.getName();
//				return new ModelAndView("redirect:/tickets");
////				return ResponseEntity.ok(ticket);
//			}
//		}else{
//			return new ModelAndView("redirect:/login2");
////			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//		}
//
//	}
	
	
	
	@GetMapping("/ticket/{id}")
	public ModelAndView editTicket(@PathVariable(name = "id") Long id, HttpSession session) {
		
		
		String username = (String) session.getAttribute("username");
		ModelAndView mav = new ModelAndView("edit_ticket");
		if(username!=null) {
			Optional<Ticket> ticket = ticketRepository.findById(id);
			if (!ticket.isPresent()) {
				return new ModelAndView("redirect:/login2");
			}
			else {

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
			
			boolean list = existingPassengers.get().stream().filter(pass->pass.getName().equals(passenger.getName())).findFirst().isPresent();
			
			if(list){
				mav.addObject("error","This passenger cannot be added");
				return mav;
				
			}else {
				if(!p.isPresent()) {
					
					passenger.setTicket(ticket.get());
					passengerRepository.save(passenger);
					
				}
				passenger.setTicket(ticket.get());
				existingPassengers.get().add(passenger);
				mav.addObject("passenger", passenger);
				return new ModelAndView("redirect:/tickets");
			}
		
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteTicket(@PathVariable(name = "id") Long id) {
		Optional<Ticket> t = ticketRepository.findById(id);

		if (!t.isPresent()) {
			return new ModelAndView("redirect:/login2");
		}
//		ticketRepository.delete(t.get());
		t.get().setStatus("Cancelled");
		ticketRepository.save(t.get());
		return new ModelAndView("redirect:/tickets");
	}
	
	@GetMapping(value = "/tickets/{name}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Optional<List<Ticket>> tickets(@PathVariable String name) {

		return ticketRepository.findTicketsByName(name);
	}

}