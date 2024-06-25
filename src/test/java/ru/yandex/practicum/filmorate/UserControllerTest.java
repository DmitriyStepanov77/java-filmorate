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
        String userJson = "{\n" +
                "    \"id\": 14,\n" +
                "    \"email\": \"Heather80@hotmail.com\",\n" +
                "    \"login\": \"Xnd2IpJ5Ig\",\n" +
                "    \"name\": \"Belinda Kling\",\n" +
                "    \"birthday\": \"1963-02-18\"\n" +
                "}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUserFailLogin() throws Exception {
        String userJson = "{\n" +
                "    \"id\": 14,\n" +
                "    \"email\": \"Heather80@hotmail.com\",\n" +
                "    \"login\": \"\",\n" +
                "    \"name\": \"Belinda Kling\",\n" +
                "    \"birthday\": \"1963-02-18\"\n" +
                "}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserFailEmail() throws Exception {
        String userJson = "{\n" +
                "    \"id\": 14,\n" +
                "    \"email\": \"Heather80hotmail.com\",\n" +
                "    \"login\": \"Xnd2IpJ5Ig\",\n" +
                "    \"name\": \"Belinda Kling\",\n" +
                "    \"birthday\": \"1963-02-18\"\n" +
                "}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserFailBirthday() throws Exception {
        String userJson = "{\n" +
                "    \"id\": 14,\n" +
                "    \"email\": \"Heather80@hotmail.com\",\n" +
                "    \"login\": \"Xnd2IpJ5Ig\",\n" +
                "    \"name\": \"Belinda Kling\",\n" +
                "    \"birthday\": \"2963-02-18\"\n" +
                "}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser() throws Exception {
        String userJson = "{\n" +
                "    \"id\": 14,\n" +
                "    \"email\": \"Heather80@hotmail.com\",\n" +
                "    \"login\": \"Xnd2IpJ5Ig\",\n" +
                "    \"name\": \"Belinda Kling\",\n" +
                "    \"birthday\": \"1963-02-18\"\n" +
                "}";

        String userJsonUpdate = "{\n" +
                "    \"id\": 14,\n" +
                "    \"email\": \"Heather80@hotmail.com\",\n" +
                "    \"login\": \"Xnd2IpJ5Ig\",\n" +
                "    \"name\": \"Belinda Kling\",\n" +
                "    \"birthday\": \"1963-02-19\"\n" +
                "}";

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