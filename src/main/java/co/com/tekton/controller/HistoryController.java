package co.com.tekton.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.com.tekton.model.entity.HistoryRecord;
import co.com.tekton.service.interfaces.IHistoryService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HistoryController {

    private final IHistoryService historyService;
    private static final String DEFAULT_VALUE_INIT = "0";
    private static final String DEFAULT_VALUE_FINISH = "10";
    private static final String PROPERTIES_DATE = "date";

    @GetMapping("/history")
    public Page<HistoryRecord> getHistory(
            @RequestParam(defaultValue = DEFAULT_VALUE_INIT) int page,
            @RequestParam(defaultValue = DEFAULT_VALUE_FINISH) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PROPERTIES_DATE).descending());
        return historyService.getHistory(pageable);
    }
}
