package co.com.tekton.service;

import co.com.tekton.model.dto.ComputationResponse;
import co.com.tekton.service.impl.ComputationServiceImpl;
import co.com.tekton.service.interfaces.IHistoryService;
import co.com.tekton.service.interfaces.IPercentageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static co.com.tekton.util.TestConstantsUtils.DELTA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComputationServiceImplTest {

    @Mock
    private IPercentageService percentageService;

    @Mock
    private IHistoryService historyService;

    @InjectMocks
    private ComputationServiceImpl computationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculate_Success() {
        double firstNumber = 10;
        double secondNumber = 5;
        double percentage = 10;

        when(percentageService.getDynamicPercentage()).thenReturn(percentage);
        ComputationResponse response = computationService.calculate(firstNumber, secondNumber);

        assertNotNull(response);
        assertEquals(percentage, response.getPercentage());
        assertEquals(16.5, response.getResult(), DELTA);
        verify(historyService, times(1)).registerAsync(any(), any(), any(), isNull());
    }

    @Test
    void testCalculate_FailsWhenPercentageServiceFails() {
        when(percentageService.getDynamicPercentage()).thenThrow(new RuntimeException("External fail"));

        assertThrows(RuntimeException.class, () -> computationService.calculate(5, 5));
        verify(historyService, times(1)).registerAsync(any(), any(), isNull(), any());
    }
}
