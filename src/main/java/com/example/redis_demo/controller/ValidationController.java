package com.example.redis_demo.controller;


import com.example.redis_demo.entity.ContractValidationEntity;
import com.example.redis_demo.model.CreateContractValidationDto;
import com.example.redis_demo.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validations")
@Slf4j
public class ValidationController {

    private final ValidationService validationService;

    @GetMapping
    List<ContractValidationEntity> getValidations() {
        log.info("Get all");
        return validationService.getAll();
    }

    @GetMapping("/{id}")
    ContractValidationEntity getValidation(@PathVariable("id") String contractNumber, @RequestParam Integer userId) {
        log.info("Get contract {}", contractNumber);
        return validationService.getValidation(contractNumber, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ContractValidationEntity save(@RequestBody CreateContractValidationDto contractDto) {
        log.info("Create {}", contractDto);
        return validationService.save(contractDto);
    }
}
