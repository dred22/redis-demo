package com.example.redis_demo.repository;

import com.example.redis_demo.entity.ContractValidationEntity;
import com.example.redis_demo.entity.CustomKey;
import org.springframework.data.repository.ListCrudRepository;

public interface ValidationRepository extends ListCrudRepository<ContractValidationEntity, CustomKey> {

    ContractValidationEntity findByContractNumber(String contractNumber);
}
