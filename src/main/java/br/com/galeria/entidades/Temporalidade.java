/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.tipos.TipoDestino;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Bruna
 */
@Entity
@Table(name = "TEMPORALIDADE")
public class Temporalidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TEMPORALIDADE", unique = true, nullable = false)
    private Long idTemporalidade;

    @Column
    private Integer correnteMeses;

    @Column
    private Integer validadeMeses;

    @Transient
    private Integer correnteQuantidadeAnos;

    @Transient
    private Integer correnteQuantidadeMeses;

    @Transient
    private Integer validadeQuantidadeAnos;

    @Transient
    private Integer validadeQuantidadeMeses;

    @Enumerated(EnumType.ORDINAL)
    private TipoDestino tipoDestino;

    @Column(columnDefinition = "text")
    private String destino;

    public Temporalidade() {
    }

    public Long getIdTemporalidade() {
        return idTemporalidade;
    }

    public void setIdTemporalidade(Long idTemporalidade) {
        this.idTemporalidade = idTemporalidade;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public TipoDestino getTipoDestino() {
        return tipoDestino;
    }

    public void setTipoDestino(TipoDestino tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

    public Integer getCorrenteMeses() {
        return correnteMeses;
    }

    public void setCorrenteMeses(Integer correnteMeses) {
        this.correnteMeses = correnteMeses;
    }

    public Integer getValidadeMeses() {
        return validadeMeses;
    }

    public void setValidadeMeses(Integer validadeMeses) {
        this.validadeMeses = validadeMeses;
    }

    public Integer getCorrenteQuantidadeAnos() {
        return correnteQuantidadeAnos;
    }

    public void setCorrenteQuantidadeAnos(Integer correnteQuantidadeAnos) {
        this.correnteQuantidadeAnos = correnteQuantidadeAnos;
    }

    public Integer getCorrenteQuantidadeMeses() {
        return correnteQuantidadeMeses;
    }

    public void setCorrenteQuantidadeMeses(Integer correnteQuantidadeMeses) {
        this.correnteQuantidadeMeses = correnteQuantidadeMeses;
    }

    public Integer getValidadeQuantidadeAnos() {
        return validadeQuantidadeAnos;
    }

    public void setValidadeQuantidadeAnos(Integer validadeQuantidadeAnos) {
        this.validadeQuantidadeAnos = validadeQuantidadeAnos;
    }

    public Integer getValidadeQuantidadeMeses() {
        return validadeQuantidadeMeses;
    }

    public void setValidadeQuantidadeMeses(Integer validadeQuantidadeMeses) {
        this.validadeQuantidadeMeses = validadeQuantidadeMeses;
    }

    public void calcularTemporalidade() {
        Integer totalMesesCorrente = 0;
        if (correnteQuantidadeAnos != null) {
            totalMesesCorrente = correnteQuantidadeAnos / 12;
        }
        totalMesesCorrente = totalMesesCorrente + correnteQuantidadeMeses;

        Integer totalMesesValidade = 0;

        if (validadeQuantidadeAnos != null) {
            totalMesesValidade = validadeQuantidadeAnos / 12;
        }

        totalMesesValidade = totalMesesValidade + validadeQuantidadeMeses;

        this.correnteMeses = totalMesesCorrente;
        this.validadeMeses = totalMesesValidade;
    }

}
