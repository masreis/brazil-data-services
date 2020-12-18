package net.brazildata;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrazilDataJavaApiConfig {

  @Bean
  public ModelMapper mapper() {
    return new ModelMapper();
  }
}
