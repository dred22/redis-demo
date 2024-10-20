package com.example.redis_demo.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@RedisHash("contracts")
public class ContractValidationEntity {

    @Id
    private String id;

    @Indexed
    private String contractNumber;
    private UUID userId;
    private String company;
    @TimeToLive
    private long ttl;
}
