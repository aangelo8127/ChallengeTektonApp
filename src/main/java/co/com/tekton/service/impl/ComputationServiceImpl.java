package co.com.tekton.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import co.com.tekton.model.dto.ComputationResponse;
import co.com.tekton.service.interfaces.IComputationService;
import co.com.tekton.service.interfaces.IHistoryService;
import co.com.tekton.service.interfaces.IPercentageService;

@Service
@AllArgsConstructor
public class ComputationServiceImpl implements IComputationService {

    private final IPercentageService percentageService;
    private final IHistoryService historyService;

    @Override
    public ComputationResponse calculate(double firstNumber, double secondNumber) {
        double sum = firstNumber + secondNumber;
        double percentage;
        double result;

        try {
            percentage = percentageService.getDynamicPercentage();
        } catch (Exception e) {
            Double cached = percentageService.getLastCachedPercentage();
            if (cached == null) {
                historyService.registerAsync("calculate",
                        "num1="+firstNumber+", num2="+secondNumber, null,
                        "No se pudo obtener porcentaje");
                throw new RuntimeException("No se pudo obtener el porcentaje");
            }
            percentage = cached;
        }

        result = sum * (1 + percentage / 100);
        final var response = ComputationResponse.builder().result(result)
                .percentage(percentage)
                .build();

        historyService.registerAsync("calculate",
                "num1="+firstNumber+", num2="+secondNumber,
                "resultado="+result+", porcentaje="+percentage,
                null);

        return response;
    }
}
