package br.com.galeria.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Piso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PISO", nullable = false, unique = true)
    private Long id;

    private Integer ordenacao;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date excluido;

    @OneToMany(mappedBy = "piso", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Sala> salas;

    private boolean status;

    public Piso() {
        status = true;
    }

    public Piso(Integer ordenacao, String descricao) {
        this.ordenacao = ordenacao;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdenacao() {
        return ordenacao;
    }

    public void setOrdenacao(Integer ordenacao) {
        this.ordenacao = ordenacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Piso other = (Piso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
