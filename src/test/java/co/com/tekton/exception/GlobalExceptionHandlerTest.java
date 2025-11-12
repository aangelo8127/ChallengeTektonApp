package co.com.tekton.exception;

import co.com.tekton.model.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;


class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Error interno");
        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno", response.getBody().getMessage());
    }

    @Test
    void testHandleBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Parámetro inválido");
        ResponseEntity<ErrorResponse> response = handler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Parámetro inválido", response.getBody().getMessage());
    }
}
