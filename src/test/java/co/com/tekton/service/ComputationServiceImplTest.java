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
        // given
        double num1 = 10;
        double num2 = 5;
        double percentage = 10; // 10%

        when(percentageService.getDynamicPercentage()).thenReturn(percentage);

        // when
        ComputationResponse response = computationService.calculate(num1, num2);

        // then
        assertNotNull(response);
        assertEquals(10 + 5, 15);
        assertEquals(percentage, response.getPercentage());
        assertEquals(16.5, response.getResult(), 0.001); // 15 * (1 + 0.1)
        verify(historyService, times(1)).registerAsync(any(), any(), any(), isNull());
    }

    @Test
    void testCalculate_FailsWhenPercentageServiceFails() {
        // given
        when(percentageService.getDynamicPercentage()).thenThrow(new RuntimeException("External fail"));

        // when / then
        assertThrows(RuntimeException.class, () -> computationService.calculate(5, 5));
        verify(historyService, times(1)).registerAsync(any(), any(), isNull(), any());
    }
}
