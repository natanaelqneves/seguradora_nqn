package com.nqn.segnqn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_apolices")
@EntityListeners(AuditingEntityListener.class)
public class Apolice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_apolice", nullable = false, unique = true, length = 20)
    private String numeroApolice;

    @Column(name = "valor_premio", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorPremio;

    @Column(name = "valor_cobertura", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCobertura;

    @Column(name = "inicio_vigencia", columnDefinition = "DATE")
    private LocalDate inicioVigencia;

    @Column(name = "fim_vigencia", columnDefinition = "DATE")
    private LocalDate fimVigencia;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "segurado_id", nullable = false)
    private Segurado segurado;

    @CreatedDate
    @Column(name = "data_emissao", nullable = false, updatable = false, columnDefinition = "DATETIME2")
    private LocalDateTime dataEmissao;

    @LastModifiedDate
    @Column(name = "data_atualizacao", columnDefinition = "DATETIME2")
    private LocalDateTime dataAtualizacao;

    public Apolice() {
    }

    public Apolice(Long id, String numeroApolice, BigDecimal valorPremio, BigDecimal valorCobertura, LocalDate inicioVigencia, LocalDate fimVigencia, Segurado segurado) {
        this.id = id;
        this.numeroApolice = numeroApolice;
        this.valorPremio = valorPremio;
        this.valorCobertura = valorCobertura;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = fimVigencia;
        this.segurado = segurado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public BigDecimal getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(BigDecimal valorPremio) {
        this.valorPremio = valorPremio;
    }

    public BigDecimal getValorCobertura() {
        return valorCobertura;
    }

    public void setValorCobertura(BigDecimal valorCobertura) {
        this.valorCobertura = valorCobertura;
    }

    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(LocalDate inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public LocalDate getFimVigencia() {
        return fimVigencia;
    }

    public void setFimVigencia(LocalDate fimVigencia) {
        this.fimVigencia = fimVigencia;
    }

    public Segurado getSegurado() {
        return segurado;
    }

    public void setSegurado(Segurado segurado) {
        this.segurado = segurado;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
