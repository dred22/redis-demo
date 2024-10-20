package com.example.redis_demo.model;

import java.util.UUID;

public record CreateContractValidationDto(String contractNumber, UUID user, String company, int ttl) {
}
