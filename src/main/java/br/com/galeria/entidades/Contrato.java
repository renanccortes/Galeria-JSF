package br.com.galeria.entidades;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.galeria.constantes.Status;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Contrato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTRATO", nullable = false, unique = true)
    private Long id;

    private String code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room")
    private Sala sala;

    @Transient //usado apenas para filtragem na view
    private Piso piso;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "locatario")
    private Locatario locatario;

    private String fiador;

    private String atividade;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimo_ajuste")
    private Date ultimoAjuste;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validade;

    @Column(name = "dia_vencimento")
    private int diaVencimento;

    private BigDecimal valor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date excluido;

    private boolean status;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<BoletoLocal> boletos;

    public Contrato() {
        status = true;
    }

    public Contrato(String code, Sala sala, Locatario locatario, String fiador, String atividade, Date inicio,
            Date ultimoAjuste, Date validade, BigDecimal valor) {
        this.code = code;
        this.sala = sala;
        this.locatario = locatario;
        this.fiador = fiador;
        this.atividade = atividade;
        this.inicio = inicio;
        this.ultimoAjuste = ultimoAjuste;
        this.validade = validade;
        this.valor = valor;
        this.criado = new Date();
        this.status = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Locatario getLocatario() {
        return locatario;
    }

    public void setLocatario(Locatario locatario) {
        this.locatario = locatario;
    }

    public String getFiador() {
        return fiador;
    }

    public void setFiador(String fiador) {
        this.fiador = fiador;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getUltimoAjuste() {
        return ultimoAjuste;
    }

    public void setUltimoAjuste(Date ultimoAjuste) {
        this.ultimoAjuste = ultimoAjuste;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    public Piso getPiso() {
        if(piso != null) {
            System.out.println("PISO: "+ piso.getDescricao());
            System.out.println("Salas:" + piso.getSalas().size());
        }
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    public List<BoletoLocal> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<BoletoLocal> boletos) {
        this.boletos = boletos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Contrato other = (Contrato) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
