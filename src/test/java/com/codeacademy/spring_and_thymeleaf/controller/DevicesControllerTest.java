package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Role;
import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DevicesController.class)
class DevicesControllerTest {
    @MockBean
    private DeviceService deviceService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void showAllDevicesPageable_forAllUsers_returnsDevicePage() throws Exception {

        Device givenDeviceInDb = new Device();
        givenDeviceInDb.setId(1L);
        givenDeviceInDb.setDeviceId(1000L);
        givenDeviceInDb.setTransportNr("ZZZ 999");
        givenDeviceInDb.setComment("car");
        givenDeviceInDb.setCreateDate(LocalDate.parse("2023-09-09"));
        givenDeviceInDb.setUserId(0);

        Device givenDeviceInDb2 = new Device();
        givenDeviceInDb2.setId(2L);
        givenDeviceInDb2.setDeviceId(2000L);
        givenDeviceInDb2.setTransportNr("XXX 777");
        givenDeviceInDb2.setComment("plane");
        givenDeviceInDb2.setCreateDate(LocalDate.parse("2023-09-09"));
        givenDeviceInDb2.setUserId(1);

        PageRequest pageRequest = PageRequest.of(1, 10);
        List<Device> deviceList = Arrays.asList(givenDeviceInDb, givenDeviceInDb2, new Device(), new Device());
        Page<Device> devicePage = new PageImpl<>(deviceList, pageRequest, deviceList.size());

        when(deviceService.findPaginated(any(), any())).thenReturn(devicePage);

        mockMvc.perform(get("/devices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("devices"))
                .andExpect(model().attributeExists("device"))
                .andExpect(content().string(containsString("ZZZ 999")))
                .andExpect(content().string(containsString("XXX 777")));
    }

    @Test
    void addDevice_userNotLoggedIn_redirectsToLogin() throws Exception {
        Device givenDeviceInDb = new Device();
        givenDeviceInDb.setId(1L);
        givenDeviceInDb.setDeviceId(1000L);
        givenDeviceInDb.setTransportNr("ZZZ 999");
        givenDeviceInDb.setComment("car");
        givenDeviceInDb.setCreateDate(LocalDate.parse("2023-09-09"));
        givenDeviceInDb.setUserId(0);

        mockMvc.perform(post("/devices/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("deviceId", "1000")
                        .param("transportNr", "ZZZ 999")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void addDevice_userLoggedIn_redirectToDevice() throws Exception {
        Device givenDeviceInDb = new Device();
        givenDeviceInDb.setId(1L);
        givenDeviceInDb.setDeviceId(1000L);
        givenDeviceInDb.setTransportNr("ZZZ 999");
        givenDeviceInDb.setComment("car");
        givenDeviceInDb.setCreateDate(LocalDate.parse("2023-09-09"));
        givenDeviceInDb.setUserId(0);

        User userDetails = new User();
        userDetails.setActive(true);
        userDetails.setVerified(true);
        userDetails.setRoles(Set.of(Role.USER));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/devices/add")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("deviceId", "1000")
                .param("transportNr", "ZZZ 999");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/devices"));
    }


    @Test
    void showFilteredDevices() {
    }

    @Test
    void updateDevice() {
    }

    @Test
    void deleteDevice() {
    }
}