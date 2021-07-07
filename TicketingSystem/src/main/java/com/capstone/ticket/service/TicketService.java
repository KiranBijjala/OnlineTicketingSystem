package com.capstone.ticket.service;

import com.capstone.ticket.model.Passenger;
import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.model.User;
import com.capstone.ticket.repository.PassengerRepository;
import com.capstone.ticket.repository.TicketRepository;
import com.capstone.ticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    PassengerRepository passengerRepository;


    public Ticket createTicket( Ticket ticket,Passenger passengers, HttpSession session){
        String username = (String) session.getAttribute("username");

        User user = userRepository.findByName(username);
        ticket.setName(username);
        ticket.setUser(user);
        Optional<Passenger> p = Optional.ofNullable(passengerRepository.findByName(passengers.getName()));

        System.out.println("Passenger Name:" + passengers.getName());

        if(!p.isPresent()){
            passengers.setTicket(ticket);
            passengerRepository.save(passengers);
        }
        List<Passenger> list = new ArrayList<>();
        list.add(passengers);
        System.out.println(ticket.getTravelDate().compareTo(ticket.getReturnDate()));
        String depart=ticket.getDepartureLocation();
        String destiny = ticket.getDestinationLocation();
        System.out.println("depart"+depart);
        System.out.println("destiny"+destiny);
        System.out.println("ticket : "+ !depart.equals(destiny));


        System.out.println("ticket1 :"+ticket);

        ticket.setStatus("Active");
        ticket.setPassengers(list);
        return ticketRepository.save(ticket);
    }


    public Ticket updateTicket( ){

        return null;
    }

}
