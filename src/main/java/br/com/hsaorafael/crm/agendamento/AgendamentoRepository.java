package br.com.hsaorafael.crm.agendamento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByFuncionarioId(Long funcionarioId);

    List<Agendamento> findTop5ByFuncionarioIdOrderByDiaAgendamentoAscHoraAgendamentoAsc(Long id);

    Optional<Agendamento> findByIdAndFuncionarioId(Long id, Long funcionarioId);
}
