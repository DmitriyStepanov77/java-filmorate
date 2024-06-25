package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;

    @Test
    void testCreateUser() throws Exception {
        String userJson = """
                {
                    "id": 1,
                    "email": "Halie59@yahoo.com",
                    "login": "P7CzhChZFQ",
                    "name": "Irma Daugherty",
                    "birthday": "1995-12-23"
                }""";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUserFailLogin() throws Exception {
        String userJson = """
                {
                    "id": 1,
                    "email": "Halie59@yahoo.com",
                    "login": "",
                    "name": "Irma Daugherty",
                    "birthday": "1995-12-23"
                }""";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserFailEmail() throws Exception {
        String userJson = """
                {
                    "id": 1,
                    "email": "Halie59yahoo.com",
                    "login": "sdfdsfsdf",
                    "name": "Irma Daugherty",
                    "birthday": "1995-12-23"
                }""";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserFailBirthday() throws Exception {
        String userJson = """
                {
                    "id": 1,
                    "email": "Halie59@yahoo.com",
                    "login": "sdfdsfsdf",
                    "name": "Irma Daugherty",
                    "birthday": "2995-12-23"
                }""";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser() throws Exception {
        String userJson = """
                {
                    "id": 1,
                    "email": "Halie59@yahoo.com",
                    "login": "P7CzhChZFQ",
                    "name": "Irma Daugherty",
                    "birthday": "1995-12-23"
                }""";

        String userJsonUpdate = """
                {
                    "id": 1,
                    "email": "Halie59@yahoo.com",
                    "login": "P7CzhChZFQ",
                    "name": "Irma Daugherty",
                    "birthday": "1995-12-24"
                }""";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonUpdate))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUsers() throws Exception {

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());

    }
}