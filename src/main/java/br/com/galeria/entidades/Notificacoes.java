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
import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author subversion
 */
@Entity
@Table(name = "NOTIFICACOES_ENVIO")
public class Notificacoes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICACOES")
    private Long idNotificacoes;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataMudancaDestino;

    private String nomeCliente;

    public static int VENCIMENTO_ANTECEDENCIA = 0;
    public static int VENCIMENTO_DOCUMENTO = 1;
    public static int VENCIMENTO_ARMAZENAMENTO = 2;

    private int tipoNotificacao;

    public Notificacoes() {

    }

    public Notificacoes(Date data, Date dataMudancaDestino, int tipoNotificacao) {
        this.data = data;
        this.dataMudancaDestino = dataMudancaDestino;
        this.tipoNotificacao = tipoNotificacao;
    }

    public Long getIdNotificacoes() {
        return idNotificacoes;
    }

    public void setIdNotificacoes(Long idNotificacoes) {
        this.idNotificacoes = idNotificacoes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataMudancaDestino() {
        return dataMudancaDestino;
    }

    public void setDataMudancaDestino(Date dataMudancaDestino) {
        this.dataMudancaDestino = dataMudancaDestino;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(int tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public boolean isTipoVencimento() {
        return tipoNotificacao == VENCIMENTO_DOCUMENTO;
    }

    public boolean isTipoVencimentoAntecipado() {
        return tipoNotificacao == VENCIMENTO_ANTECEDENCIA;
    }

    public boolean isTipoVencimentoArmazenamento() {
        return tipoNotificacao == VENCIMENTO_ARMAZENAMENTO;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.idNotificacoes);
        hash = 67 * hash + Objects.hashCode(this.data);
        hash = 67 * hash + this.tipoNotificacao;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notificacoes other = (Notificacoes) obj;
        if (!Objects.equals(FacesUtil.converterData(this.data), FacesUtil.converterData(other.data))) {
            return false;
        }

        if (!Objects.equals(this.tipoNotificacao, other.tipoNotificacao)) {
            return false;
        }

        return true;
    }

    public boolean isMesmaNotificacao(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notificacoes other = (Notificacoes) obj;
        if (!Objects.equals(FacesUtil.converterData(this.data), FacesUtil.converterData(other.data))) {
            return false;
        }

        if (!Objects.equals(this.tipoNotificacao, other.tipoNotificacao)) {
            return false;
        }

        return true;
    }

}
