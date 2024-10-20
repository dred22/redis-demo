package com.example.redis_demo.model;

public record CreateContractValidationDto(String contractNumber, Integer userId, String company, int ttl) {
}
