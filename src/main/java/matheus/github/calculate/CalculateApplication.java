package matheus.github.calculate;

import matheus.github.calculate.security.UnprotectedEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculateApplication implements CommandLineRunner {

	@Autowired
	private UnprotectedEndpoints unprotectedEndpoints;

	public static void main(String[] args) {
		SpringApplication.run(CalculateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Testando commmand line runner");

		System.out.println(unprotectedEndpoints.getUnprotectedEndpoints().toString());
		System.out.println(unprotectedEndpoints.getUnprotectedEndpointsVector().toString());
	}
}
