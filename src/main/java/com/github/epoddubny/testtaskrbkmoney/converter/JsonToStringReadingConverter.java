package com.github.epoddubny.testtaskrbkmoney.converter;

import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public enum JsonToStringReadingConverter implements Converter<PGobject, String> {
    INSTANCE;

    @Override
    public String convert(PGobject pgObject) {
        return pgObject.getValue();
    }
}
