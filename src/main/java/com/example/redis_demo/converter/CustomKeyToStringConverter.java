package com.example.redis_demo.converter;

import com.example.redis_demo.entity.CustomKey;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class CustomKeyToStringConverter implements Converter<CustomKey, String> {

    @Override
    public String convert(CustomKey key) {
        return key.contractNumber() + ":" + key.userId();
    }
}