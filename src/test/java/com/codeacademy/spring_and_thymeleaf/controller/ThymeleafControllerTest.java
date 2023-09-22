package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import com.codeacademy.spring_and_thymeleaf.service.PositionService;
import com.codeacademy.spring_and_thymeleaf.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThymeleafController.class)
class ThymeleafControllerTest {
    @MockBean
    private DeviceService deviceService;
    @MockBean
    private PositionService positionService;
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @Test
    void showIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}