package co.com.tekton.service.impl;

import org.springframework.stereotype.Service;
import co.com.tekton.service.interfaces.IPercentageService;
import org.springframework.cache.annotation.Cacheable;

@Service
public class PercentageServiceImpl implements IPercentageService {
    private double lastValue = 10.0;

    @Override
    @Cacheable(value = "percentage")
    public double getDynamicPercentage() {
        lastValue = 10.0;
        return lastValue;
    }

    @Override
    @Cacheable(value = "percentage")
    public Double getLastCachedPercentage() {
        return lastValue;
    }
}
