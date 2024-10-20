package com.example.redis_demo.converter;

import com.example.redis_demo.entity.CustomKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
@RequiredArgsConstructor
public class BitesToCustomKeyConverter implements Converter<byte[], CustomKey> {


    @Override
    public CustomKey convert(byte[] source) {
        String[] split = new String(source).split(":");
        return new CustomKey(split[0], Integer.valueOf(split[1]));
    }
}