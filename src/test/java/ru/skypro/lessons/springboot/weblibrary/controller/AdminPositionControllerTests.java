package ru.skypro.lessons.springboot.weblibrary.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class AdminPositionControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void noPositionInDatabase_whenPositionAdded_thenItExistsInList() throws Exception {

        JSONObject jsonPosition = new JSONObject();
        jsonPosition.put("name", "Developer");

        mockMvc.perform(post("/admin/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPosition.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Developer"));
    }
}
