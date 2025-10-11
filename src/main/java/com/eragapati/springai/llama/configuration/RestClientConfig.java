package com.eragapati.springai.llama.configuration;

import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

// enable this if you need to force internal OpenAI API to
// use HTTP_1_1 instead of HTTP_2 which might not work with vLLM engine.
//@Configuration
public class RestClientConfig {
    @Bean
    @Primary
    public RestClient.Builder restClientBuilder(RestClientBuilderConfigurer configurer) {
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory((HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .build())));
    }
}
