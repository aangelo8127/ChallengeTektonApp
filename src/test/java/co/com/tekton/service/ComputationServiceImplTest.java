package co.com.tekton.service;

import co.com.tekton.model.dto.ComputationResponse;
import co.com.tekton.service.impl.ComputationServiceImpl;
import co.com.tekton.service.interfaces.IHistoryService;
import co.com.tekton.service.interfaces.IPercentageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputationServiceImplTest {

    private IPercentageService percentageService;
    private IHistoryService historyService;
    private ComputationServiceImpl computationService;

    @BeforeEach
    void setUp() {
        percentageService = mock(IPercentageService.class);
        historyService = mock(IHistoryService.class);
        computationService = new ComputationServiceImpl(percentageService, historyService);
    }

    @Test
    void testComputationSuccess() {
        when(percentageService.getDynamicPercentage()).thenReturn(10.0);

        ComputationResponse response = computationService.calculate(100, 50);

        assertEquals(165, response.getResult(), 0.001); // (100+50)*1.1
        assertEquals(10.0, response.getPercentage());
        verify(historyService, times(1)).registerAsync(anyString(), anyString(), anyString(), isNull());
    }

    @Test
    void testComputationFallbackToCache() {
        when(percentageService.getDynamicPercentage()).thenThrow(new RuntimeException());
        when(percentageService.getLastCachedPercentage()).thenReturn(15.0);

        ComputationResponse response = computationService.calculate(200, 100);

        assertEquals(345, response.getResult(), 0.001); // (200+100)*1.15
        assertEquals(15.0, response.getPercentage());
        verify(historyService, times(1)).registerAsync(anyString(), anyString(), anyString(), isNull());
    }

    @Test
    void testCalculateFailNoCache() {
        when(percentageService.getDynamicPercentage()).thenThrow(new RuntimeException());
        when(percentageService.getLastCachedPercentage()).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> computationService.calculate(50, 50));

        assertTrue(ex.getMessage().contains("No se pudo obtener el porcentaje"));
        verify(historyService, times(1)).registerAsync(anyString(), anyString(), isNull(), anyString());
    }

}
