package net.braga.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import net.braga.model.Funcionario;
import net.braga.repository.FuncionarioRepository;
import net.braga.security.MyUserDetails;

@Controller
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository userRepository;
	
	
	@PostMapping(value = "/user/editar" )
    public ModelAndView editar(Funcionario user) {
		   ModelAndView mv = new ModelAndView("user/edit-user"); 
			System.out.println(">>>>>>>>>>> "+user);
		   if(!senhaForte(user.getPassword())) {
			   
			   mv.addObject("msg", "errorSenhaFraca");
			   return mv;
			   
		   }else if(confirmPassword(user.getConfirmPassword())) {
			   String password = new BCryptPasswordEncoder().encode(user.getPassword());
			   user.setPassword(password);
			   userRepository.saveAndFlush(user);
					
				  mv.addObject("user", user);
				  mv.addObject("msg", "success");
					
				return mv;
		   }
		   
		   mv.addObject("msg", "error");
		   return mv;
		   
	}
	

	@GetMapping("/user/edit-user")
	public ModelAndView editar() {
		Funcionario u = getUserLogin();
		Optional<Funcionario> user = userRepository.findById(u.getId());
		ModelAndView mv = new ModelAndView("user/edit-user");
		mv.addObject("user", user.get());
		String senha = "";
		mv.addObject("senha", senha);
			
		return mv;
	}
	
	private boolean confirmPassword(String senha) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
		boolean senhaAtualValida =  passwordEncoder.matches(senha, getUserLogin().getPassword());
		
		return senhaAtualValida;
	}
	
	public static boolean senhaForte(String senha) {
	    if (senha.length() < 6) return false;

	    boolean achouNumero = false;
	    boolean achouMaiuscula = false;
	    boolean achouMinuscula = false;
	    boolean achouSimbolo = false;
	    for (char c : senha.toCharArray()) {
	         if (c >= '0' && c <= '9') {
	             achouNumero = true;
	         } else if (c >= 'A' && c <= 'Z') {
	             achouMaiuscula = true;
	         } else if (c >= 'a' && c <= 'z') {
	             achouMinuscula = true;
	         } else if (c == '@' || c == '#' || c == '$' || c == '%' || c == '&') {
	        	
	             achouSimbolo = true;
	         }
	    }
	    return achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo;
	}
	
private Funcionario getUserLogin() {
		
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
