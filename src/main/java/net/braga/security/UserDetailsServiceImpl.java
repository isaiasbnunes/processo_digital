package net.braga.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.braga.model.Funcionario;
import net.braga.repository.FuncionarioRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private FuncionarioRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Funcionario user = userRepository.getByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}else {
			System.out.println("Gravar log... ");
		}
		
		return new MyUserDetails(user);
	}
}
