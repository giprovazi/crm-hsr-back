package br.com.hsaorafael.crm.lead;

import br.com.hsaorafael.crm.common.enums.LeadStatus;
import br.com.hsaorafael.crm.common.exceptions.BusinessException;
import br.com.hsaorafael.crm.funcionario.Funcionario;
import br.com.hsaorafael.crm.lead.dto.LeadContactRequestDTO;
import br.com.hsaorafael.crm.lead.dto.LeadCreateRequestDTO;
import br.com.hsaorafael.crm.lead.dto.LeadUpdateRequestDTO;
import br.com.hsaorafael.crm.lead.dto.LeadResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeadService {
    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }


    public LeadResponseDTO cadastroLead(LeadCreateRequestDTO leadRequestDTO){
        Lead lead = new Lead();
        lead.setNome(leadRequestDTO.nome());
        lead.setTelefone(leadRequestDTO.telefone());
        lead.setEmail(leadRequestDTO.email());
        lead.setProcedimentoInteresse(leadRequestDTO.procedimentoInteresse());
        lead.setOrigem(leadRequestDTO.origem());
        lead.setObservacoes(leadRequestDTO.observacoes());

        lead.setStatus(LeadStatus.NOVO);
        lead.setDataCriacao(LocalDateTime.now());
        lead.setAtivo(true);
        lead.setResponsavel(null);
        lead.setDataUltimoContato(null);

        leadRepository.save(lead);

        return LeadResponseDTO.fromEntity(lead);
    }

    public List<LeadResponseDTO> listarTodosLeads(){
        return leadRepository.findAll()
                .stream()
                .map(LeadResponseDTO::fromEntity)
                .toList();
    }

    public LeadResponseDTO buscarLeadPorId(Long id){
        return LeadResponseDTO.fromEntity(leadRepository.findById(id).orElseThrow(() -> new BusinessException("Lead não encontrado.")));
    }

    public void assumirLead(Long id){
        Lead lead = leadRepository.findById(id).orElseThrow(() -> new BusinessException("Lead não encontrado."));

        if (lead.getResponsavel() != null){
            throw new BusinessException("Lead já tem responsável.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Funcionario funcionario = (Funcionario) authentication.getPrincipal();
        lead.setResponsavel(funcionario);
        lead.setStatus(LeadStatus.EM_ATENDIMENTO);
        lead.setDataUltimoContato(LocalDateTime.now());
        leadRepository.save(lead);
    }

    public void atualizarLead(Long id, LeadUpdateRequestDTO lead){
        Lead leadAntigo = leadRepository.findById(id).orElseThrow(() -> new BusinessException("Lead não encontrado."));
        leadAntigo.setNome(lead.nome());
        leadAntigo.setTelefone(lead.telefone());
        leadAntigo.setEmail(lead.email());
        leadAntigo.setProcedimentoInteresse(lead.procedimentoInteresse());
        leadAntigo.setObservacoes(lead.observacoes());
        leadAntigo.setDataUltimoContato(LocalDateTime.now());
        leadRepository.save(leadAntigo);
    }

    public void registrarContato(Long id, LeadContactRequestDTO leadContact){
        Lead lead = leadRepository.findById(id).orElseThrow(()-> new BusinessException("Lead não encontrado."));
        lead.setObservacoes(leadContact.observacoes());
        lead.setDataUltimoContato(LocalDateTime.now());
        leadRepository.save(lead);
    }

    public void encaminharLead(Long id){
        Lead lead = leadRepository.findById(id).orElseThrow(()-> new BusinessException("Lead não encontrado."));
        lead.setStatus(LeadStatus.ENCAMINHADO_VENDAS);
        lead.setResponsavel(null);
        lead.setDataUltimoContato(LocalDateTime.now());
    }

    public List<LeadResponseDTO> listarLeadsCriadosHoje(){
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicio = hoje.atStartOfDay();
        LocalDateTime fim = hoje.atTime(23, 59, 59);

        List<Lead> leads = leadRepository.findByDataCriacaoBetween(inicio,fim);

        return leads.stream()
                .map(LeadResponseDTO::fromEntity)
                .toList();
    }

    public Integer contarLeadsAtivos(){
        return leadRepository.findByAtivoTrue().toArray().length;
    }

    public void desativarLead(Long id){
        Lead lead = leadRepository.findById(id).orElseThrow(()-> new BusinessException("Lead não encontrado."));
        lead.setAtivo(false);
        lead.setResponsavel(null);
        lead.setDataUltimoContato(LocalDateTime.now());
        lead.setStatus(LeadStatus.PERDIDO);
    }
}
