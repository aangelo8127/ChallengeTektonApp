package co.com.tekton.service.interfaces;

import co.com.tekton.model.dto.ComputationResponse;

public interface IComputationService {
    ComputationResponse calculate(double firstNumber, double secondNumber);
}
