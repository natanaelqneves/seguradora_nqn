package com.nqn.segnqn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_corretores")
public class Corretor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String usuario;

    @NotBlank
    @Column(nullable = false)
    private String senha;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String susep;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "corretor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apolice> apolices = new ArrayList<>();

    public Corretor() {
    }

    public Corretor(Long id, String nome, String usuario, String senha, String susep) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.susep = susep;
        this.ativo = false;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSusep() {
        return susep;
    }

    public void setSusep(String susep) {
        this.susep = susep;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Apolice> getApolices() {
        return apolices;
    }

    public void setApolices(List<Apolice> apolices) {
        this.apolices = apolices;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
