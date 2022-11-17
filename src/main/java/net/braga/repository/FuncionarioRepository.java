package net.braga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.braga.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	@Query("SELECT f FROM Funcionario f WHERE f.username = :username and f.active = true")
	public Funcionario getByUsername(@Param("username") String username);
	
}
