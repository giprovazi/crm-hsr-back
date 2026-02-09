package br.com.hsaorafael.crm.lead;

import br.com.hsaorafael.crm.common.enums.LeadOrigem;
import br.com.hsaorafael.crm.common.enums.LeadStatus;
import br.com.hsaorafael.crm.funcionario.Funcionario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    private String email;

    @Column(nullable = false)
    private String procedimentoInteresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeadOrigem origem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeadStatus status;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Funcionario responsavel;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataUltimoContato;

    private LocalDateTime proximoContatoEm;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false)
    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataUltimoContato() {
        return dataUltimoContato;
    }

    public void setDataUltimoContato(LocalDateTime dataUltimoContato) {
        this.dataUltimoContato = dataUltimoContato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LeadOrigem getOrigem() {
        return origem;
    }

    public void setOrigem(LeadOrigem origem) {
        this.origem = origem;
    }

    public String getProcedimentoInteresse() {
        return procedimentoInteresse;
    }

    public void setProcedimentoInteresse(String procedimentoInteresse) {
        this.procedimentoInteresse = procedimentoInteresse;
    }

    public LocalDateTime getProximoContatoEm() {
        return proximoContatoEm;
    }

    public void setProximoContatoEm(LocalDateTime proximoContatoEm) {
        this.proximoContatoEm = proximoContatoEm;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public LeadStatus getStatus() {
        return status;
    }

    public void setStatus(LeadStatus status) {
        this.status = status;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
