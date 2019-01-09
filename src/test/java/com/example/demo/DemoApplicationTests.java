package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void simpleResponseTest() throws Exception {
        mockMvc.perform(
                get("/index").param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, John"));

        mockMvc.perform(
                get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, undefined user"));
    }

    @Test
    public void simpleResponseTest_exception() throws Exception {
        mockMvc.perform(
                get("/index_noSuchUrl").param("name", "John"))
                .andExpect(status().is4xxClientError());
    }

}

