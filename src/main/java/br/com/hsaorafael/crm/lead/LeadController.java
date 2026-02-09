package br.com.hsaorafael.crm.lead;

import br.com.hsaorafael.crm.lead.dto.LeadContactRequestDTO;
import br.com.hsaorafael.crm.lead.dto.LeadCreateRequestDTO;
import br.com.hsaorafael.crm.lead.dto.LeadResponseDTO;
import br.com.hsaorafael.crm.lead.dto.LeadUpdateRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyRole('CALL_CENTER', 'ADMIN')")
@RestController
@RequestMapping("/api/leads")
public class LeadController {
    private final  LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }


    @PostMapping
    public ResponseEntity<LeadResponseDTO> cadastroLead(@RequestBody LeadCreateRequestDTO leadRequestDTO){
        LeadResponseDTO responseDTO = leadService.cadastroLead(leadRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<LeadResponseDTO>> listarTodosLeads(){
        return ResponseEntity.ok(leadService.listarTodosLeads());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadResponseDTO> buscarLeadPorId(@PathVariable Long id){
        return ResponseEntity.ok(leadService.buscarLeadPorId(id));
    }

    @PatchMapping("/{id}/assumir")
    public ResponseEntity<Void> assumirLead(@PathVariable Long id){
        leadService.assumirLead(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarLead(@PathVariable Long id, @RequestBody LeadUpdateRequestDTO leadUpdateRequestDTO){
        leadService.atualizarLead(id, leadUpdateRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/contato")
    public ResponseEntity<Void> registrarContato(@PathVariable Long id, @RequestBody LeadContactRequestDTO leadContactRequestDTO){
        leadService.registrarContato(id, leadContactRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/encaminhar")
    public ResponseEntity<Void> encaminharLead(@PathVariable Long id){
        leadService.encaminharLead(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hoje")
    public ResponseEntity<List<LeadResponseDTO>> listarLeadsCriadosHoje(){
        return ResponseEntity.ok(leadService.listarLeadsCriadosHoje());
    }

    @GetMapping("/ativos/count")
    public ResponseEntity<Integer> contarLeadsAtivos(){
        return ResponseEntity.ok(leadService.contarLeadsAtivos());
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarLead(@PathVariable Long id){
        leadService.desativarLead(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
