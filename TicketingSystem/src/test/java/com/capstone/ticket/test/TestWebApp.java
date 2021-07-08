//package com.capstone.ticket.test;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class TestWebApp extends TicketingSystemApplicationTests
//{
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//
//    @Test
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    public void testUserLogin() throws Exception {
//        mockMvc.perform(get("/user/{id}",1))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.name").value("kiran"))
//                .andExpect(jsonPath("$.street").value("754 The Alameda"))
//                .andExpect(jsonPath("$.city").value("San Jose"))
//                .andExpect(jsonPath("$.state").value("CA"))
//                .andExpect(jsonPath("$.zip").value("95126"))
//                .andExpect(jsonPath("$.contactNumber").value("4084258090"))
//                .andExpect(jsonPath("$.password").value("password"));
//    }
//
//
////    @Test
////    public void getUser() throws Exception
////    {
////    	mockMvc.perform(get("/getuser/{id}", 1))
////    	.andExpect(content().contentType("application/json;charset=UTF-8"))
////          .andDo(print())
////          .andExpect(status().isOk())
////          .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
////    }
//
//
//}