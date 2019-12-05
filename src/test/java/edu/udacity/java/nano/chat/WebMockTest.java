package edu.udacity.java.nano.chat;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.udacity.java.nano.WebSocketChatApplication;
import edu.udacity.java.nano.chat.WebSocketChatServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.net.UnknownHostException;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    //to test login you can perfrom a /index?username=fuyang and then look for fuyang in the page.

    @Test
    public void userJoin() throws Exception {
        this.mockMvc.perform(get("/index?username=Fuyang"))
                .andExpect(content().string(containsString("Fuyang")));
    }

    @Test
    public void userChat() throws Exception {
        this.mockMvc.perform(get("/index?username=Fuyang"))
                .andExpect(view().name("chat"));
    }
    @Test
    public void userLogin() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("login"));
    }

    @Test
    public void leave() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("login"));
    }

}
