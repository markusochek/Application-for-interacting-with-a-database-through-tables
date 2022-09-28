package com.example.second.contracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;


    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public String getAll() throws JsonProcessingException {
        return contractService.getAll();
    }
}
