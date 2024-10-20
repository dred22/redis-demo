package com.example.redis_demo.converter;

import com.example.redis_demo.entity.CustomKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@WritingConverter
public class CustomKeyToBitesConverter implements Converter<CustomKey, byte[]> {

    private final CustomKeyToStringConverter customKeyToStringConverter;

    @Override
    public byte[] convert(CustomKey key) {
        return customKeyToStringConverter.convert(key).getBytes();
    }
}