package br.com.hsaorafael.crm.lead;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Lead> findByAtivoTrue();

    List<Lead> findByResponsavelId(Long id);

    Optional<Lead> findByResponsavelIdAndId(Long idFunc, Long idLead);

    Integer countByAtivoTrue();
}
