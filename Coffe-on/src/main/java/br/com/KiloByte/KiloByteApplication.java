package br.com.KiloByte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.KiloByte.domain.user.userAdmin")
public class KiloByteApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiloByteApplication.class, args);
	}

}
