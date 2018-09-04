/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.tipos.Acao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 *
 * @author Renan
 */
@Entity(name = "acaodousuario")
public class AcaoDoUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valorVazio;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "acaodousuario_acoes", joinColumns = {
        @JoinColumn(name = "id_acaodousuario")})
    @Column(name = "tipo_acao")
    private List<Acao> acoes;

    public AcaoDoUsuario() {
        acoes = new ArrayList<>();
    }

    public AcaoDoUsuario(List<Acao> acoes) {
        this.acoes = acoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Acao> getAcoes() {
        return acoes;
    }

    public boolean isPermitido(Acao acao) {

        if (acoes != null) {
            return acoes.contains(acao);
        }

        return false;
    }

    public void setAcoes(List<Acao> acoes) {
        this.acoes = acoes;
    }

    public String getValorVazio() {
        return valorVazio;
    }

    public void setValorVazio(String valorVazio) {
        this.valorVazio = valorVazio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final AcaoDoUsuario other = (AcaoDoUsuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
