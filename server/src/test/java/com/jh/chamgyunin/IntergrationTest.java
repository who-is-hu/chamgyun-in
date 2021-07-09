package com.jh.chamgyunin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class IntergrationTest {
    @Autowired protected MockMvc mvc;
    @Autowired protected ObjectMapper objectMapper;
    protected MockHttpSession session;
}
