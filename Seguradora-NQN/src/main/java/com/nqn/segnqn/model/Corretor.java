package com.nqn.segnqn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_corretores")
public class Corretor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String susep;

    @OneToMany(mappedBy = "corretor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apolice> apolices = new ArrayList<>();

    public Corretor() {
    }

    public Corretor(Long id, String nome, String susep) {
        this.id = id;
        this.nome = nome;
        this.susep = susep;
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

    public String getSusep() {
        return susep;
    }

    public void setSusep(String susep) {
        this.susep = susep;
    }

    public List<Apolice> getApolices() {
        return apolices;
    }

    public void setApolices(List<Apolice> apolices) {
        this.apolices = apolices;
    }
}
