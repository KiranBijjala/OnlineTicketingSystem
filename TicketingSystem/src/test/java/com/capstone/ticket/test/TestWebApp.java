package com.capstone.ticket.test;

import org.junit.Before;


import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.capstone.ticket.model.Ticket;
import com.capstone.ticket.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestWebApp extends TicketingSystemApplicationTests
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

//    @Test
//    public void testUserLogin() throws Exception {
//        mockMvc.perform(get("/getuser/{id}",2))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.id").value("2"))
//                .andExpect(jsonPath("$.name").value("kiran"))
//                .andExpect(jsonPath("$.address").value("1921 Kennedy Dr #202"))
//                .andExpect(jsonPath("$.name").value("4084258090"))
//                .andExpect(jsonPath("$.password").value("password"));
//    }
	
    
    @Test
    public void getUser() throws Exception 
    {
    	mockMvc.perform(get("/getuser/{id}", 1))
    	.andExpect(content().contentType("application/json;charset=UTF-8"))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
	

}