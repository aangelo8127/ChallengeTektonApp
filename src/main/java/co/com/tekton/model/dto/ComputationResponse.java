package co.com.tekton.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComputationResponse {
    private double result;
    private double percentage;
}
