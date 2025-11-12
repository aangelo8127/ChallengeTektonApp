package co.com.tekton.service.impl;

import co.com.tekton.external.PercentageClient;
import co.com.tekton.model.dto.PercentageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;
import co.com.tekton.service.interfaces.IPercentageService;
import org.springframework.cache.annotation.Cacheable;
import retrofit2.Response;

@Service
@AllArgsConstructor
@Slf4j
public class PercentageServiceImpl implements IPercentageService {
    private final PercentageClient percentageClient;
    private final CacheManager cacheManager;

    @Override
    @Cacheable(value = "percentage")
    public double getDynamicPercentage() {
        try {
            Response<PercentageResponse> response = percentageClient.getPercentage().execute();
            if (response.isSuccessful() && response.body() != null) {
                double percentage = response.body().getPercentage();
                log.info("Porcentaje obtenido del servicio externo: {}", percentage);
                return percentage;
            }
            throw new RuntimeException("Respuesta inválida del servicio externo");
        } catch (Exception e) {
            log.warn("Error al obtener porcentaje externo: {}", e.getMessage());
            var cache = cacheManager.getCache("percentage");
            if (cache != null) {
                Double cachedValue = cache.get(SimpleKey.EMPTY, Double.class);
                if (cachedValue != null) {
                    log.info("Usando porcentaje cacheado: {}", cachedValue);
                    return cachedValue;
                }
            }
            throw new RuntimeException("No se pudo obtener porcentaje ni hay valor cacheado");
        }
    }
}
