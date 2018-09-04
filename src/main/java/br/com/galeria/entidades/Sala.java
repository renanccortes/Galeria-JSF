package br.com.galeria.entidades;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.galeria.constantes.Status;
import java.io.Serializable;
import javax.persistence.Column;

@Entity
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SALA", nullable = false, unique = true)
    private Long id;

    private String nome;

    private String metros;

    private String fracao;

    private String local;

    private Integer numero;

    private String complemento;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "piso")
    private Piso piso;

    @Temporal(TemporalType.TIMESTAMP)
    private Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date excluido;

    private boolean status;

    public Sala() {
        status = true;
    }

    public Sala(String metros, String fracao, String local, Integer numero, Piso piso) {

        this.metros = metros;
        this.fracao = fracao;
        this.local = local;
        this.numero = numero;
        this.piso = piso;
        this.status = Status.ACTIVE;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetros() {
        return metros;
    }

    public void setMetros(String metros) {
        this.metros = metros;
    }

    public String getFracao() {
        return fracao;
    }

    public void setFracao(String fracao) {
        this.fracao = fracao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
