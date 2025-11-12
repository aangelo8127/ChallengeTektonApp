package co.com.tekton.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import co.com.tekton.model.entity.HistoryRecord;
import co.com.tekton.repository.IHistoryRepository;
import co.com.tekton.service.interfaces.IHistoryService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements IHistoryService {

    private final IHistoryRepository repository;

    @Async
    @Override
    public void registerAsync(String endpoint, String params, String result, String error) {
        HistoryRecord record = new HistoryRecord();
        record.setDate(LocalDateTime.now());
        record.setEndpoint(endpoint);
        record.setParams(params);
        record.setResult(result);
        record.setError(error);
        repository.save(record);
    }

    @Override
    public Page<HistoryRecord> getHistory(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
