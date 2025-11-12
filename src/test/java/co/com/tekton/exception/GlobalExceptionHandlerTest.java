package co.com.tekton.exception;

import co.com.tekton.model.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static co.com.tekton.util.TestConstantsUtils.BAD_REQUEST_MESSAGE;
import static co.com.tekton.util.TestConstantsUtils.ERROR_INTERNAL_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;


class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException(ERROR_INTERNAL_MESSAGE);
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_INTERNAL_MESSAGE, response.getBody().getMessage());
    }

    @Test
    void testHandleBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException(BAD_REQUEST_MESSAGE);
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(BAD_REQUEST_MESSAGE, response.getBody().getMessage());
    }
}
