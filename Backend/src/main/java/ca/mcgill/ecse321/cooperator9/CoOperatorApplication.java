package ca.mcgill.ecse321.cooperator9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@SpringBootApplication
public class CoOperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoOperatorApplication.class, args);
	}
	
	@RequestMapping("/")
	public String greeting(){
		return "Aeiou!";
	}

}

