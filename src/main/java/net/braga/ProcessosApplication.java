package net.braga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProcessosApplication {

	public static void main(String[] args) {
		 //String senhaCriptografado = new BCryptPasswordEncoder().encode("341200");
	     //System.out.println(senhaCriptografado);
		SpringApplication.run(ProcessosApplication.class, args);
	}

}
