package com.capstone.ticket.controller;

import com.capstone.ticket.model.Passenger;
import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.PassengerRepository;
import com.capstone.ticket.repository.TicketRepository;
import com.capstone.ticket.repository.UserRepository;
import com.capstone.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {

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
			return new ModelAndView("redirect:/login2");
		}

	}


	@GetMapping("/tickets/cancelled")
	public ModelAndView cancelledTickets(HttpSession session) {

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

	
//	@PostMapping(value = "/ticket")
//	public ModelAndView saveTicket(@ModelAttribute("ticket") Ticket ticket,@ModelAttribute("passenger") Passenger passengers, HttpSession session) {
//
//		String username = (String) session.getAttribute("username");
//
//		User user = userRepository.findByName(username);
//		ticket.setName(username);
//		ticket.setUser(user);
//		Optional<Passenger> p = Optional.ofNullable(passengerRepository.findByName(passengers.getName()));
//
//		System.out.println("Passenger Name:" + passengers.getName());
//
//		if(!p.isPresent()){
//			passengers.setTicket(ticket);
//			passengerRepository.save(passengers);
//		}
//		List<Passenger> list = new ArrayList<>();
//		list.add(passengers);
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
//			}
//			else{
//				System.out.println("ticket1 :"+ticket);
//				ModelAndView model = new ModelAndView("new_ticket") ;
//				ticket.setStatus("Active");
//				ticket.setPassengers(list);
//				ticketRepository.save(ticket);
//				model.addObject("ticket",ticket);
//				model.addObject("passengers",passengers);
//				String name = ticket.getName();
//				return new ModelAndView("redirect:/tickets");
//			}
//		}else{
//			return new ModelAndView("redirect:/login2");
//		}
//
//	}



	@PostMapping(value = "/ticket")
	public ModelAndView saveTicket(@ModelAttribute("ticket") Ticket ticket,@ModelAttribute("passenger") Passenger passengers, HttpSession session) {

		if (session.getAttribute("username")!=null){
			if(ticket.getTravelDate().compareTo(ticket.getReturnDate())>0) {
				ModelAndView model = new ModelAndView("new_ticket");
				model.addObject("error", "one or more Invalid Ticket fields");
				return model;
			}
			else{
				ticketService.createTicket(ticket,passengers,session);
				ModelAndView model = new ModelAndView("new_ticket") ;
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
	public List<Ticket> tickets(@PathVariable String name) {
		
		User user = userRepository.findByName(name);
		return ticketRepository.findAll();
	}

}