package co.com.tekton.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComputationRequest {
    private double firstNumber;
    private double secondNumber;
}
