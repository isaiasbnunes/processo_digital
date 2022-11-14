package net.braga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.braga.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
