package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;

    @Test
    void testCreateFilm() throws Exception {
        String filmJson = """
                {
                 "name": "nisi eiusmod",
                 "description": "adipisicing",
                 "releaseDate": "1967-03-25",
                 "duration": 100
                }""";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateFilmFailName() throws Exception {
        String filmJson = """
                {
                  "name": "",
                  "description": "adipisicing",
                  "releaseDate": "1967-03-25",
                  "duration": 100
                }""";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserFailDuration() throws Exception {
        String filmJson = """
                {
                  "name": "nisi eiusmod",
                  "description": "adipisicing",
                  "releaseDate": "1967-03-25",
                  "duration": 0
                }""";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserFailReleaseDate() throws Exception {
        String filmJson = """
                {
                  "name": "nisi eiusmod",
                  "description": "adipisicing",
                  "releaseDate": "1867-03-25",
                  "duration": 100
                }""";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateFilm() throws Exception {
        String filmJson = """
                {
                  "id": 1,
                  "name": "nisi eiusmod",
                  "description": "adipisicing",
                  "releaseDate": "1967-03-25",
                  "duration": 100
                }""";

        String filmJsonUpdate = """
                {
                  "id": 1,
                  "name": "nisi eiusmod",
                  "description": "adipisicing",
                  "releaseDate": "1967-03-26",
                  "duration": 100
                }""";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isOk());

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJsonUpdate))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFilms() throws Exception {

        mockMvc.perform(get("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());

    }
}