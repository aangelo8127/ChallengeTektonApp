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

    @GetMapping("/history")
    public Page<HistoryRecord> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return historyService.getHistory(pageable);
    }
}
