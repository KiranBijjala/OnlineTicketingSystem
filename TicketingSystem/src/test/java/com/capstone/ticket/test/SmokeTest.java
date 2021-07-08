package com.capstone.ticket.test;
import com.capstone.ticket.controller.FeedbackController;
import com.capstone.ticket.controller.QueryController;
import com.capstone.ticket.controller.TicketController;
import com.capstone.ticket.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class SmokeTest {
    @Autowired
    private FeedbackController feedbackController;

    @Autowired
    private QueryController queryController;

    @Autowired
    private TicketController ticketController;

    @Autowired
    private UserController userController;
    //context is creating our controllers
    @Test
    public void contextLoads() throws Exception {
        assertThat(feedbackController).isNotNull();
        assertThat(queryController).isNotNull();
        assertThat(ticketController).isNotNull();
        assertThat(userController).isNotNull();
    }
}
