package co.com.tekton.service;

import co.com.tekton.external.PercentageClient;
import co.com.tekton.model.dto.PercentageResponse;
import co.com.tekton.service.impl.PercentageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.SimpleKey;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PercentageServiceImplTest {

    @Mock
    private PercentageClient percentageClient;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private PercentageServiceImpl percentageService;

    @Mock
    private Call<PercentageResponse> mockCall;

    private Cache mockCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockCache = new ConcurrentMapCache("percentage");
        when(cacheManager.getCache("percentage")).thenReturn(mockCache);
    }

    @Test
    void testGetDynamicPercentage_Success() throws Exception {
        // given
        PercentageResponse response = PercentageResponse.builder().percentage(10.0).build();
        when(percentageClient.getPercentage()).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(Response.success(response));

        // when
        double result = percentageService.getDynamicPercentage();

        // then
        assertEquals(10.0, result);
    }

    @Test
    void testGetDynamicPercentage_UsesCachedValueOnFailure() throws Exception {
        // given
        // Simulamos valor previo cacheado
        mockCache.put(SimpleKey.EMPTY, 8.5);

        when(percentageClient.getPercentage()).thenReturn(mockCall);
        when(mockCall.execute()).thenThrow(new RuntimeException("External service down"));

        // when
        double result = percentageService.getDynamicPercentage();

        // then
        assertEquals(8.5, result);
    }

    @Test
    void testGetDynamicPercentage_FailsIfNoCacheAvailable() throws Exception {
        // given
        when(percentageClient.getPercentage()).thenReturn(mockCall);
        when(mockCall.execute()).thenThrow(new RuntimeException("External down"));

        // when / then
        assertThrows(RuntimeException.class, () -> percentageService.getDynamicPercentage());
    }
}