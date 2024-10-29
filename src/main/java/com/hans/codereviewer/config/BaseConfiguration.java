package com.hans.codereviewer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class BaseConfiguration {

    @Value("${llm.baseUrl}")
    public String baseUrl;

    @Value("${llm.apiKey}")
    public String apiKey;

}
