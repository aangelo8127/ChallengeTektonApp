package co.com.tekton.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class HistoryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String endpoint;
    @Column(columnDefinition = "TEXT")
    private String params;
    @Column(columnDefinition = "TEXT")
    private String result;
    @Column(columnDefinition = "TEXT")
    private String error;
}
