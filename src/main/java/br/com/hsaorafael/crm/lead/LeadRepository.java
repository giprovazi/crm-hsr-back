package br.com.hsaorafael.crm.lead;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Lead> findByAtivoTrue();
}
