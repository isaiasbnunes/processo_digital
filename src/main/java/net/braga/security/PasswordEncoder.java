package net.braga.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {


	public static void main(String[] args) {
		 String senhaCriptografado = new BCryptPasswordEncoder().encode("341200");
	     System.out.println(">>>>>>> teste: "+ senhaCriptografado);
	
	}
	
}
