package net.braga.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.braga.model.Funcionario;
import net.braga.repository.FuncionarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;


@Component
public class UserLogin {

	@Autowired
	private FuncionarioRepository userRepository;
	
	public Funcionario getUserLogin() {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String user;    

			if (principal instanceof MyUserDetails) {
			    user = (( MyUserDetails)principal).getUsername();
			} else {
			    user = principal.toString();
			}
			
			Funcionario userByBd = userRepository.getByUsername(user);
			
			if(userByBd != null) {
				return userByBd;
			}
			
			return null;
	}
}
