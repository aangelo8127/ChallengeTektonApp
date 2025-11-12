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
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setDate(LocalDateTime.now());
        historyRecord.setEndpoint(endpoint);
        historyRecord.setParams(params);
        historyRecord.setResult(result);
        historyRecord.setError(error);
        repository.save(historyRecord);
    }

    @Override
    public Page<HistoryRecord> getHistory(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
