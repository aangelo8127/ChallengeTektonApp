package co.com.tekton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.com.tekton.model.entity.HistoryRecord;

public interface IHistoryRepository extends JpaRepository<HistoryRecord, Long> {}

