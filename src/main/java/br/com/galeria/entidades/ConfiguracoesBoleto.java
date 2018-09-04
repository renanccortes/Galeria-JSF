/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author Renan
 */
@Entity
@Table(name = "config_boleto")
public class ConfiguracoesBoleto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONFIG_BOLETO", nullable = false, unique = true)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "banco")
    private Banco banco;

    private String docBeneficiado;

    private String carteira;

    private String nossoNumero;
    private String nossoNumeroDigito;

    private String numeroDocumento;
    private String localPagamento;
    private String instrucaoSacado;

    private BigDecimal multa;
    private Double taxaJuros;

    private String instrucao1;
    private String instrucao2;
    private String instrucao3;
    private String instrucao4;
    private String instrucao5;
    private String instrucao6;
    private String instrucao7;
    private String instrucao8;

    private Integer diasAntecipadoParaGeracaoDeBoleto;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date atualizado;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date excluido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocBeneficiado() {
        return docBeneficiado;
    }

    public void setDocBeneficiado(String docBeneficiado) {
        this.docBeneficiado = docBeneficiado;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public String getNossoNumeroDigito() {
        return nossoNumeroDigito;
    }

    public void setNossoNumeroDigito(String nossoNumeroDigito) {
        this.nossoNumeroDigito = nossoNumeroDigito;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getLocalPagamento() {
        return localPagamento;
    }

    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    public String getInstrucaoSacado() {
        return instrucaoSacado;
    }

    public void setInstrucaoSacado(String instrucaoSacado) {
        this.instrucaoSacado = instrucaoSacado;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public Double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(Double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public String getInstrucao1() {
        return instrucao1;
    }

    public void setInstrucao1(String instrucao1) {
        this.instrucao1 = instrucao1;
    }

    public String getInstrucao2() {
        return instrucao2;
    }

    public void setInstrucao2(String instrucao2) {
        this.instrucao2 = instrucao2;
    }

    public String getInstrucao3() {
        return instrucao3;
    }

    public void setInstrucao3(String instrucao3) {
        this.instrucao3 = instrucao3;
    }

    public String getInstrucao4() {
        return instrucao4;
    }

    public void setInstrucao4(String instrucao4) {
        this.instrucao4 = instrucao4;
    }

    public String getInstrucao5() {
        return instrucao5;
    }

    public void setInstrucao5(String instrucao5) {
        this.instrucao5 = instrucao5;
    }

    public String getInstrucao6() {
        return instrucao6;
    }

    public void setInstrucao6(String instrucao6) {
        this.instrucao6 = instrucao6;
    }

    public String getInstrucao7() {
        return instrucao7;
    }

    public void setInstrucao7(String instrucao7) {
        this.instrucao7 = instrucao7;
    }

    public String getInstrucao8() {
        return instrucao8;
    }

    public void setInstrucao8(String instrucao8) {
        this.instrucao8 = instrucao8;
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

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Integer getDiasAntecipadoParaGeracaoDeBoleto() {
        return diasAntecipadoParaGeracaoDeBoleto;
    }

    public void setDiasAntecipadoParaGeracaoDeBoleto(Integer diasAntecipadoParaGeracaoDeBoleto) {
        this.diasAntecipadoParaGeracaoDeBoleto = diasAntecipadoParaGeracaoDeBoleto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final ConfiguracoesBoleto other = (ConfiguracoesBoleto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
