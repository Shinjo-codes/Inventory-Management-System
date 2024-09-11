package com.example.demo;

import com.example.controller.LoginController;
import com.example.entity.User;
import com.example.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this); //initializes the mock before each test
    }

    @Test
    public void testLoginSuccess ()  {
        String userName = "testUser";
        User user = new User();

        when(loginService.login(userName)).thenReturn(user);
        ResponseEntity<?> response = loginController.login(userName);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        String userName = "Invalid user";
        when(loginService.login(userName)).thenReturn(null);
        ResponseEntity<?> response = loginController.login(userName);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());

    }

    @Test
    void testLoginWithException(){
        String userName = "test user";
        when(loginService.login(userName)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = loginController.login(userName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred", response.getBody());
    }

    @Test
    public void testAdminLogin() {
        Model model = new BindingAwareModelMap();
        String viewName = loginController.adminLogin(model);

        assertEquals("login", viewName);
        assertNull(model.getAttribute("invalid"));

    }



}