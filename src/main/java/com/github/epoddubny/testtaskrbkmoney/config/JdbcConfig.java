package com.github.epoddubny.testtaskrbkmoney.config;

import com.github.epoddubny.testtaskrbkmoney.converter.JsonToStringReadingConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import static java.util.Collections.singletonList;

@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(singletonList(JsonToStringReadingConverter.INSTANCE));
    }
}
