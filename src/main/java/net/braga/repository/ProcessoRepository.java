package net.braga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.braga.model.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long>{

}
