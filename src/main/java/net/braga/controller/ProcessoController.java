package net.braga.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import net.braga.model.Funcionario;
import net.braga.model.Processo;
import net.braga.model.Tramitar;
import net.braga.repository.FuncionarioRepository;
import net.braga.repository.ProcessoRepository;
import net.braga.repository.SetorRepository;
import net.braga.repository.TramitarRepository;
import net.braga.security.UserLogin;

@Controller
public class ProcessoController {

	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private TramitarRepository tramitarRepository;
	
	@Autowired
	private UserLogin userLogin;
	
	private Processo processoNumber;
	
	@GetMapping("/processo/listar")
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("processo/listar");
		mv.addObject("listaProcesso", processoRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/processo/consultar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("processo/consultar");
		mv.addObject("listaProcesso", processoRepository.findAll());
		return mv;
	}
	
	@GetMapping("/processo/cadastrar")
	public ModelAndView cadastrar(Processo processo) {
		ModelAndView mv = new ModelAndView("processo/cadastrar"); 
		mv.addObject("processo", processo);
		mv.addObject("listFuncionarios", funcionarioRepository.findAll());
		mv.addObject("listSetor", setorRepository.findAll());
		return mv;
	}
	
	
	@PostMapping("/processo/tramitar/salvar")
	public ModelAndView tramitar(Tramitar tramitar) {
		
		
		tramitar.setProcesso(processoNumber);
		Tramitar t = tramitarRepository.saveAndFlush(tramitar);
		
		if(t.getDestino().equals(tramitar.getDestino())) {
			ModelAndView mv = new ModelAndView("processo/consultar"); 
			mv.addObject("listaProcesso", processoRepository.findAll());
			mv.addObject("msg", "success");
			return mv;
		}
		
		return cadastrar(new Processo());
	}
	
	@PostMapping("/processo/salvar")
	public ModelAndView salvar(Processo processo) {
		
		Processo p = processoRepository.saveAndFlush(processo);
	
			ModelAndView mv = new ModelAndView("processo/listar"); 
			mv.addObject("listaProcesso", processoRepository.findAll());
			mv.addObject("msg", "success");
			return mv;
	}
	
	
	@GetMapping("/processo/tramitar/{id}")
	public ModelAndView tramitar(@PathVariable("id") Long id) {
		
		Optional<Processo> processo = processoRepository.findById(id);
		processoNumber = processo.get();
		ModelAndView mv = new ModelAndView("processo/tramitar"); 
		mv.addObject("processo", processo.get());
		mv.addObject("tramitar", new Tramitar());
		mv.addObject("numero", processo.get().getNumero());
		mv.addObject("assunto", processo.get().getAssunto());
		mv.addObject("responsavel", processo.get().getResponsavel());
		mv.addObject("listSetor", setorRepository.findAll());
		mv.addObject("setor", processo.get().getSetor());
		mv.addObject("dataAbertura", processo.get().getDataAbertura());
		return mv;
	}
	
	
	@GetMapping("/processo/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		Optional<Processo> processo = processoRepository.findById(id);
		List<Tramitar> listTramitacoes = tramitarRepository.findByProcesso(processo.get());
		
		ModelAndView mv = new ModelAndView("processo/editar"); 
		mv.addObject("processo", processo);
		mv.addObject("numero", processo.get().getNumero());
		mv.addObject("id", processo.get().getId());
		mv.addObject("assunto", processo.get().getAssunto());
		mv.addObject("responsavel", processo.get().getResponsavel());
		mv.addObject("setor", processo.get().getSetor());
		mv.addObject("dataAbertura", processo.get().getDataAbertura());
		mv.addObject("tramitacoes", listTramitacoes);
		mv.addObject("isOwner_processo", funcionarioPodeTramitar(listTramitacoes));
		
		return mv;
	}
	
	
	private boolean funcionarioPodeTramitar(List<Tramitar> list ) {
		 Tramitar t = list.get(list.size()-1);
		 Funcionario user = userLogin.getUserLogin();
		 
		 if(t.getDestino() == user.getSetor()) {
			 return true;
		 }
		 
		 System.out.println(">>>>>>>> ultimo processo: "+t.getProcesso().getNumero());
		 System.out.println(">>>>>>>> destino processo: "+t.getDestino());
		 System.out.println(">>>>>>>> setor usuario: "+user.getSetor());
		return false;
	}
}







