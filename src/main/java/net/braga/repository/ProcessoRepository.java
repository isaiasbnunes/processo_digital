package net.braga.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.braga.model.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long>{

	 Optional<Processo> findByNumero(String numero);
	 Optional<Processo> findByAssuntoAndDataAbertura(String numero, Date dataAbertura);
}
