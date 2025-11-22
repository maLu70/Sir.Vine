package com.ifsp.Sir.Vine.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario implements UserDetails {

    @Id
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "ADMIN")
    private boolean admin;

    @Column(name = "nome")
    private String nome;

    @Column(name = "CPF")
    private String CPF;

    public Usuario() {}

    public Usuario(String nome, String cPF, String email, String senha) {
        this.nome = nome;
        this.CPF = cPF;
        this.email = email;
        this.senha = senha;
        this.admin = false;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCPF() { return CPF; }
    public void setCPF(String cPF) { this.CPF = cPF; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> admin ? "ROLE_ADMIN" : "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
