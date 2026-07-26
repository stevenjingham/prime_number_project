package org.steve.primenumberapplication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "properties")
public class PrimeNumberConfiguration {

    private int maximumInputValue;

}
