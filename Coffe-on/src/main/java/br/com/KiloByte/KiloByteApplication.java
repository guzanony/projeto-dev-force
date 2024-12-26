package br.com.KiloByte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class KiloByteApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiloByteApplication.class, args);
	}

}
