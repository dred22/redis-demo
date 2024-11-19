package com.example.redis_demo.service;

import com.example.redis_demo.entity.ContractValidationEntity;
import com.example.redis_demo.entity.CustomKey;
import com.example.redis_demo.exception.ValidationNotFound;
import com.example.redis_demo.model.CreateContractValidationDto;
import com.example.redis_demo.repository.ValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final ValidationRepository validationRepository;

    public List<ContractValidationEntity> getAll() {

        return validationRepository.findAll();

    }

    public ContractValidationEntity save(CreateContractValidationDto contractDto) {
        ContractValidationEntity contractValidationEntity = new ContractValidationEntity();
        contractValidationEntity.setId(new CustomKey(contractDto.contractNumber(), contractDto.userId()));
        contractValidationEntity.setCompany(contractDto.company());
        contractValidationEntity.setTtl(contractDto.ttl());
        return validationRepository.save(contractValidationEntity);
    }

    public ContractValidationEntity getValidation(String contractNumber, Integer userId) {

        return validationRepository.findById(new CustomKey(contractNumber, userId))
                .orElseThrow(ValidationNotFound::new);
    }
}
