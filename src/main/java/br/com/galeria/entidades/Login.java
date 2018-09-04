/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.tipos.TipoUsuario;
import br.com.galeria.util.CriptografaSenha;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Renan Cortes
 */
@Entity
@Table(name = "login")
public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOGIN", nullable = false, unique = true)
    private Long idLogin;

    @Column(name = "DES_LOGIN", nullable = false, length = 50)
    private String login;
    @Column(name = "DES_SENHA", nullable = false, length = 100)
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "FLG_ATIVO", columnDefinition = "boolean default true")
    private boolean ativo = true;

    @Transient
    private String senhaAtual;
    @Transient
    private String novaSenha;
    @Transient
    private String confirmaSenha;

    @Transient
    private boolean sucesso;

    @Transient
    private String causa;

    public Login() {

    }

    public Login(Usuario usuario) {
        this.usuario = usuario;
    }

    public Login(String usuario, String senha, TipoUsuario tipo, Usuario usuario1) {
        this.usuario = usuario1;
        setSenha(senha);
    }

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public String getSenhaDescrip() {
        return CriptografaSenha.desCriptografa(senha);
    }

    public void setSenha(String senha) { 
        this.senha = CriptografaSenha.criptografa(senha);

    }
    public void setSenhaCriptografada(String senha) { 
        this.senha = senha;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public String getSenhaAtualDescrip() {
        return CriptografaSenha.desCriptografa(senhaAtual);
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;

    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getAtivoFormatado() {
        if (ativo) {
            return "Sim";
        } else {
            return "Não";
        }
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
