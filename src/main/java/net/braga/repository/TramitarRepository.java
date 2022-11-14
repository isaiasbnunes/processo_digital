package net.braga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.braga.model.Processo;
import net.braga.model.Tramitar;

public interface TramitarRepository extends JpaRepository<Tramitar, Long>{

	@Query(value = "SELECT t FROM Tramitar t where t.processo = :processo" )
	public List<Tramitar> findByProcesso(@Param("processo") Processo processo);
}
