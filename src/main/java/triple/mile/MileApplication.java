package triple.mile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MileApplication {

	public static void main(String[] args) {
		SpringApplication.run(MileApplication.class, args);
	}

}
