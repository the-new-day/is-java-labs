package org.dealership.infrastructure.rest.config;

import org.dealership.application.port.in.common.dto.MoneyDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, MoneyDto.class,
                source -> new MoneyDto(new BigDecimal(source)));
    }
}
