/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.tipos.BancosEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Renan
 */
@Entity
@Table(name = "banco")
public class Banco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BANCO", nullable = false, unique = true)
    private Long id;

    private String nomeBeneficiado;
    private String agenciaNumero;
    private String agenciaDigito;
    private String contaNumero;
    private String contaDigito;

    @Enumerated(EnumType.ORDINAL)
    private BancosEnum banco;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date atualizado;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date excluido;

    private boolean status;

    public Banco() {
        status = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeBeneficiado() {
        return nomeBeneficiado;
    }

    public void setNomeBeneficiado(String nomeBeneficiado) {
        this.nomeBeneficiado = nomeBeneficiado;
    }

    public String getAgenciaNumero() {
        return agenciaNumero;
    }

    public void setAgenciaNumero(String agenciaNumero) {
        this.agenciaNumero = agenciaNumero;
    }

    public String getAgenciaDigito() {
        return agenciaDigito;
    }

    public void setAgenciaDigito(String agenciaDigito) {
        this.agenciaDigito = agenciaDigito;
    }

    public String getContaNumero() {
        return contaNumero;
    }

    public void setContaNumero(String contaNumero) {
        this.contaNumero = contaNumero;
    }

    public String getContaDigito() {
        return contaDigito;
    }

    public void setContaDigito(String contaDigito) {
        this.contaDigito = contaDigito;
    }

    public BancosEnum getBanco() {
        return banco;
    }

    public void setBanco(BancosEnum banco) {
        this.banco = banco;
    }

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }

    public Date getAtualizado() {
        return atualizado;
    }

    public void setAtualizado(Date atualizado) {
        this.atualizado = atualizado;
    }

    public Date getExcluido() {
        return excluido;
    }

    public void setExcluido(Date excluido) {
        this.excluido = excluido;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Banco other = (Banco) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
