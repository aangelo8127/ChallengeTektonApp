package co.com.tekton.controller;

import co.com.tekton.model.entity.HistoryRecord;
import co.com.tekton.service.interfaces.IHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IHistoryService historyService;

    private HistoryRecord record;

    @BeforeEach
    void setUp() {
        record = new HistoryRecord();
        record.setId(1L);
        record.setEndpoint("calculate");
        record.setParams("num1=1, num2=2");
        record.setResult("resultado=3, porcentaje=10");
        record.setError(null);
        record.setDate(java.time.LocalDateTime.now());
    }

    @Test
    void testGetHistorySuccess() throws Exception {
        List<HistoryRecord> records = Collections.singletonList(record);
        Page<HistoryRecord> page = new PageImpl<>(records, PageRequest.of(0, 10), 1);
        when(historyService.getHistory(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/history?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].endpoint").value("calculate"));

        Mockito.verify(historyService).getHistory(any(Pageable.class));
    }
}
