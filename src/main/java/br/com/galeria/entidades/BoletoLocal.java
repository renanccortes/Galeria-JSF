/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

/**
 *
 * @author Renan
 */
import br.com.galeria.constantes.Status;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "boleto")
public class BoletoLocal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BOLETO", nullable = false, unique = true)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "contrato")
    private Contrato contrato;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_processamento")
    private Date dataProcessamento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private Date dataVencimento;

    private Integer mes;

    private Integer ano;

    private String codigoBarras;

    private BigDecimal fine;

    @Column(name = "taxa_juro")
    private Double taxaJuro;

    private String path;

    private String link;

    private BigDecimal valor;

    private boolean regerado;

    private boolean pago;

    @Transient//usado apenas para manipulação na view
    private Locatario locatario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_pagamento")
    private Date dataPagamento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deletado;

    private boolean status;

    public BoletoLocal() {
    }

    public BoletoLocal(Contrato contrato, Date dataProcessamento, Date dataVencimento, Integer mes, Integer ano, BigDecimal valor) {
        this.contrato = contrato;
        this.dataProcessamento = dataProcessamento;
        this.dataVencimento = dataVencimento;
        this.mes = mes;
        this.ano = ano;
        this.valor = valor;
        this.status = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;

        if (contrato != null) {
            preencheInformacoesDoBoleto();
        }
    }

    private void preencheInformacoesDoBoleto() {
        if (dataProcessamento == null) {
            dataProcessamento = new Date();
        }

        if (dataVencimento == null) {
            Calendar dataAtual = Calendar.getInstance();
            dataAtual.set(Calendar.DAY_OF_MONTH, contrato.getDiaVencimento());
            dataVencimento = dataAtual.getTime();
        }

        Calendar dataVencimentoCalendar = Calendar.getInstance();
        dataVencimentoCalendar.setTime(dataVencimento);

        mes = dataVencimentoCalendar.get(Calendar.MONTH) + 1;
        ano = dataVencimentoCalendar.get(Calendar.YEAR);

    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public Double getTaxaJuro() {
        return taxaJuro;
    }

    public void setTaxaJuro(Double taxaJuro) {
        this.taxaJuro = taxaJuro;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isRegerado() {
        return regerado;
    }

    public void setRegerado(boolean regerado) {
        this.regerado = regerado;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
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

    public Date getDeletado() {
        return deletado;
    }

    public void setDeletado(Date deletado) {
        this.deletado = deletado;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Locatario getLocatario() {
        return locatario;
    }

    public void setLocatario(Locatario locatario) {
        this.locatario = locatario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final BoletoLocal other = (BoletoLocal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
