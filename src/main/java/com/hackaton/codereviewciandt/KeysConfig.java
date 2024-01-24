package com.hackaton.codereviewciandt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeysConfig {

    private final String GITHUB_TOKEN;
    private final String OPENAI_API_KEY;
    private final String OPENAI_ENDPOINT;

    public KeysConfig(@Value("${token_github}") String GITHUB_TOKEN,
                      @Value("${token_ia}") String OPENAI_API_KEY,
                      @Value("${endpoint_ia}") String OPENAI_ENDPOINT) {
        this.GITHUB_TOKEN = GITHUB_TOKEN;
        this.OPENAI_API_KEY = OPENAI_API_KEY;
        this.OPENAI_ENDPOINT = OPENAI_ENDPOINT;
    }

    public String getGITHUB_TOKEN() {
        return GITHUB_TOKEN;
    }
    public String getOPENAI_API_KEY() {
        return OPENAI_API_KEY;
    }
    public String getOPENAI_ENDPOINT() {
        return OPENAI_ENDPOINT;
    }
}