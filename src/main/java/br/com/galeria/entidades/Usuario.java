/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.tipos.TipoUsuario;
import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Renan
 */
@Entity
@Table(name = "usuario_sistema")
@DiscriminatorValue(value = "0")
public class Usuario extends Pessoa implements Serializable {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_LOGIN")
    private Login login;

    @ManyToOne //PODE SER NULL PQ VAMOS UTILIZAR A ENTIDADE PARA O MASTER QUE NÃO CONTEM EMPRESA VINCULADA
    @JoinColumn(name = "id_empresa", nullable = true)
    private Empresa empresa;

    @Enumerated(EnumType.ORDINAL)
    private TipoUsuario tipoUsuario;

    @Transient //usado para criar idUnico de  sessao...
    private String idUnico;

    @Transient
    private boolean admin; //usado na view para controlar o tipo de usuário

    public Usuario() {
        this.login = new Login(this);
     
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Login getLogin() {
        if (login == null) {
            this.login = new Login(this);
        }
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public void criarIdUnico() {
        this.idUnico = this.getIdPessoa() + FacesUtil.converterDataHoraPasta(new Date());
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdministrador() { //ENCAPSULADO PARA FACILITAR
        return tipoUsuario == TipoUsuario.USUARIO_ADMINISTRADOR;
    }
    
    public boolean isMaster() { //ENCAPSULADO PARA FACILITAR
        return tipoUsuario == TipoUsuario.USUARIO_MASTER;
    }

}
