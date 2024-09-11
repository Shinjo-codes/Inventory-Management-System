package com.example.demo;

import com.example.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class) //focuses on loading the controller layer alone without loading the entire application context
class HomeControllerTest {

    @Autowired //Injects MockMvc dependency automatically
    private MockMvc mockMvc; //performs http requests and verifies the result

    @Test
    void shouldReturnIndexPage() throws Exception{ //throws an exception if the body request of is wrong

        mockMvc.perform(get("/")) //simulates http get request to the root URL

                .andExpect(status().isOk()) //Expects that status is ok

                .andExpect(view().name("index")); //Expects that the index view is returned

    }

}