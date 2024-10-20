package com.example.redis_demo.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@RedisHash("contracts")
public class ContractValidationEntity {

    @Id
    private CustomKey id;
    private String company;
    @TimeToLive
    private long ttl;
}
