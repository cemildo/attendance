package ch.cemil.info.yoklama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
		basePackages = {"ch.cemil.info.yoklama"}
)
@EntityScan("ch.cemil.info.yoklama.domain.entity")
public class YoklamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoklamaApplication.class, args);
	}
}
