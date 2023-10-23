package com.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {

    private String accountSid;  // value store in environment variable due to confidentiality
    private String authToken;   // value store in environment variable due to confidentiality
    private String trialNumber; // value store in environment variable due to confidentiality

} //ENDCLASS
