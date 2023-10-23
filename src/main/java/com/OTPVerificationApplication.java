package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class OTPVerificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OTPVerificationApplication.class, args);
    }

    public Docket swaggerConfiguration() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/sms/*"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "OTP Verification Api",
                "Application in JAVA",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Kanza Jamal", "https://github.com/skanzajamal", "k122060@nu.ed.pk"),
                "API License",
                "https://github.com/skanzajamal",
                Collections.emptyList());
    }

} //ENDCLASS
