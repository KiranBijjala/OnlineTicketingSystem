package com.capstone.ticket.test;

import com.capstone.ticket.model.Address;
import com.capstone.ticket.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// In this test, the full Spring application context is started but without the server
// Spring handles the incoming HTTP request and hands it off to our controller
@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void shouldLoadLandingPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index1"));
    }
    @Test
    public void shouldLoadLoginPage() throws Exception {
        this.mockMvc.perform(get("/login2"))
                .andExpect(status().isOk())
                .andExpect(view().name("login2"));
    }
    @Test
    public void shouldLoadSignUpPage() throws Exception {
        this.mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("signup"));
    }

    // this test creates a new user in the db so this test can only be run once
    // then you need to change the name param
    @Test
    public void shouldSignUpUser() throws Exception {
        this.mockMvc.perform(post("/signup")
                .param("name", "jeff")
                .param("password", "password")
                .param("contactNumber", "4084313363")
                .param("street", "Erikson")
                .param("city", "Cupertino")
                .param("state", "CA")
                .param("zip", "95014")
                .flashAttr("user", new User())
                .flashAttr("address", new Address()))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    public void shouldLoginExistingUser() throws Exception {
        this.mockMvc.perform(post("/login2")
                .param("name", "steven")
                .param("password", "password")
                .flashAttr("user", new User()))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    public void shouldLoadExistingUserTicketPage() throws Exception {
        this.mockMvc.perform(get("/tickets").sessionAttr("username", "kiran"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("listTickets"))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("index"));
    }

    @Test
    public void shouldLogoutLoggedInUser() throws Exception {
        HttpSession session = this.mockMvc.perform(get("/logout2").sessionAttr("username", "kiran"))
                .andExpect(status().is3xxRedirection())
                .andReturn()
                .getRequest()
                .getSession();

        assertThat(session.getAttribute("username")).isNull();
    }
}

