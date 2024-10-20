package com.example.redis_demo.repository;

import com.example.redis_demo.entity.ContractValidationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ValidationRepository extends CrudRepository<ContractValidationEntity, String> {

    ContractValidationEntity findByContractNumber(String contractNumber);
}
