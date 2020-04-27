package org.poc.cpfap.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class DBNKTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DBNKTransactionApplication.class, args);
	}
	
	@Bean
    public RestTemplate getRestTemplate() {
      return new RestTemplate();
    }
}
