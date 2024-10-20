package com.example.redis_demo.controller;


import com.example.redis_demo.entity.ContractValidationEntity;
import com.example.redis_demo.model.CreateContractValidationDto;
import com.example.redis_demo.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validations")
@Slf4j
public class ValidationController {

    private final ContractService contractService;

    @GetMapping
    List<ContractValidationEntity> getContracts(){
        log.info("Get all");
        return contractService.getAll();
    }

    @GetMapping("/{id}")
    ContractValidationEntity getContract(@PathVariable("id") String contractNumber){
        log.info("Get contract {}", contractNumber);
        return contractService.getContract(contractNumber);
    }

    @PostMapping
    ContractValidationEntity save(@RequestBody CreateContractValidationDto contractDto){
        log.info("Create {}", contractDto);
        return contractService.save(contractDto);
    }
}
