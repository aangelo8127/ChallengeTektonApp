package co.com.tekton.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.com.tekton.model.dto.ComputationRequest;
import co.com.tekton.model.dto.ComputationResponse;
import co.com.tekton.service.interfaces.IComputationService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ComputationController {
    private final IComputationService calculationService;

    @PostMapping("/calculate")
    public ComputationResponse calculate(@RequestBody ComputationRequest request) {
        return calculationService.calculate(request.getFirstNumber(), request.getSecondNumber());
    }
}
