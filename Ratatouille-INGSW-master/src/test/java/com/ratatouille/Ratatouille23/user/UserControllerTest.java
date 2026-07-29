package com.ratatouille.Ratatouille23.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserControllerTest.class)
class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;



    @Test
    void getUsers() {
    }

    @Test
    void researchUsers() {
    }

    @Test
    void getOrderByUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUser() throws Exception {
        // Given
        Long userId = 1L;
        willDoNothing().given(userService).deleteUser(userId);

        // When
        ResultActions response = mockMvc.perform(delete("/api/v1/user/{id}", userId));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateUser() {
    }
}