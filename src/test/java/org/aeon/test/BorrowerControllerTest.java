package org.aeon.test;

import org.aeon.test.controller.BorrowerController;
import org.aeon.test.entity.Borrower;
import org.aeon.test.service.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BorrowerController.class)
@Import(BorrowerService.class)  // Import the actual service if needed
class BorrowerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BorrowerService borrowerService;

    @InjectMocks
    private BorrowerController borrowerController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(borrowerController).build();
    }

    @Test
    void testRegisterBorrower() throws Exception {
        Borrower borrower = new Borrower("John Doe", "john.doe@example.com");

        when(borrowerService.registerBorrower(any(Borrower.class))).thenReturn(borrower);

        mockMvc.perform(post("/api/borrowers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(borrower)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetAllBorrowers() throws Exception {
        when(borrowerService.getAllBorrowers()).thenReturn(List.of(new Borrower("John Doe", "john.doe@example.com")));

        mockMvc.perform(get("/api/borrowers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}


