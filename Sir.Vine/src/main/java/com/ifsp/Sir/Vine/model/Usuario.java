package com.ifsp.Sir.Vine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {
    
@Id
private String email;

@Column(name = "senha")
private String senha;

@Column(name = "ADMIN")
private boolean admin;

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;

}
public String getSenha() {
    return senha;
}
public void setSenha(String senha) {
    this.senha = senha;
}

public boolean isAdmin() {
    return admin;
}
public void setAdmin(boolean admin) {
    this.admin = admin;
}

}
