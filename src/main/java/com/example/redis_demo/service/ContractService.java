package com.example.redis_demo.service;

import com.example.redis_demo.entity.ContractValidationEntity;
import com.example.redis_demo.model.CreateContractValidationDto;
import com.example.redis_demo.repository.ValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ValidationRepository validationRepository;

    public List<ContractValidationEntity> getAll() {

        return StreamSupport
                .stream(validationRepository.findAll().spliterator(), false)
                .toList();

    }

    public ContractValidationEntity save(CreateContractValidationDto contractDto) {
        ContractValidationEntity contractValidationEntity = new ContractValidationEntity();
        contractValidationEntity.setContractNumber(contractDto.contractNumber());
        contractValidationEntity.setUserId(contractDto.user());
        contractValidationEntity.setCompany(contractDto.company());
        return validationRepository.save(contractValidationEntity);
    }

    public ContractValidationEntity getContract(String contractNumber) {

        return validationRepository.findByContractNumber(contractNumber);
    }
}
