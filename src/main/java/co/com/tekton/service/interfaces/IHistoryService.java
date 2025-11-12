package co.com.tekton.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import co.com.tekton.model.entity.HistoryRecord;

public interface IHistoryService {
    void registerAsync(String endpoint, String params, String result, String error);
    Page<HistoryRecord> getHistory(Pageable pageable);
}
