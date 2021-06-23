package com.capstone.ticket.controller;


import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Transactional
@RestController
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @PostMapping(value="/addticket", produces = {MediaType.APPLICATION_JSON_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE})
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
    	return ticketRepository.save(ticket);
    	
    }

    @GetMapping(value="/gettickets", produces = {MediaType.APPLICATION_JSON_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE})
    public List<Ticket> getTickets(){

        System.out.println("Inside Get all Tickets");
        return ticketRepository.findAll();

    }


    @DeleteMapping(value="/deleteticket/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE})
    public String deleteticket(@PathVariable Long id){

        System.out.println("Inside Get all Tickets");

        Optional<Ticket> t = ticketRepository.findById(id);

        if(t.isPresent()){
            ticketRepository.delete(t.get());
            return "Ticket SuccessFully Deleted";
        }
        else{
            return "Ticket not Found";
        }

    }

}