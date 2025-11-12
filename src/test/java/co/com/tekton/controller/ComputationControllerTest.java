package co.com.tekton.controller;

import co.com.tekton.model.dto.ComputationResponse;
import co.com.tekton.service.interfaces.IComputationService;
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

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@WebMvcTest(ComputationController.class)
public class ComputationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IComputationService computationService;

    private ComputationResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = ComputationResponse.builder()
                .result(123.45)
                .percentage(10.0)
                .build();
    }

    @Test
    void testCalculateSuccess() throws Exception {
        when(computationService.calculate(anyDouble(), anyDouble())).thenReturn(mockResponse);

        String requestJson = "{\"firstNumber\":100,\"secondNumber\":50}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(123.45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.percentage").value(10.0));

        // Verifica que el servicio fue llamado con los parámetros correctos
        Mockito.verify(computationService).calculate(100.0, 50.0);
    }

    @Test
    void testCalculateThrowsException() throws Exception {
        when(computationService.calculate(anyDouble(), anyDouble())).thenThrow(new RuntimeException("No se pudo obtener el porcentaje"));

        String requestJson = "{\"firstNumber\":100,\"secondNumber\":50}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());

        // Verifica que el servicio fue llamado con los parámetros correctos
        Mockito.verify(computationService).calculate(100.0, 50.0);
    }
}
