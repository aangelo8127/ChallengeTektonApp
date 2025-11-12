package co.com.tekton.controller;

import co.com.tekton.model.dto.ComputationResponse;
import co.com.tekton.service.interfaces.IComputationService;
import co.com.tekton.util.TestConstantsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static co.com.tekton.util.TestConstantsUtils.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@WebMvcTest(ComputationController.class)
class ComputationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IComputationService computationService;

    private ComputationResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = ComputationResponse.builder()
                .result(RESULT_VALUE)
                .percentage(PERCENTAGE_VALUE)
                .build();
    }

    @Test
    void testCalculateSuccess() throws Exception {
        when(computationService.calculate(anyDouble(), anyDouble())).thenReturn(mockResponse);
        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(RESULT_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.percentage").value(PERCENTAGE_VALUE));
        Mockito.verify(computationService).calculate(FIRST_NUMBER, SECOND_NUMBER);
    }

    @Test
    void testCalculateThrowsException() throws Exception {
        when(computationService.calculate(anyDouble(), anyDouble())).thenThrow(new RuntimeException("No se pudo obtener el porcentaje"));
        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());

        Mockito.verify(computationService).calculate(FIRST_NUMBER, SECOND_NUMBER);
    }
}
