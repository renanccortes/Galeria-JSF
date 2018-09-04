package br.com.galeria.entidades;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String action;

    private String tabela;

    private String reference;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    protected Log() {
    }

    public Log(String action, Usuario user, Date created) {
        this.action = action;
        this.usuario = usuario;
        this.created = created;
    }

    public Log(String action, String tabela, String reference, Usuario usuario, Date created) {
        this.action = action;
        this.tabela = tabela;
        this.reference = reference;
        this.usuario = usuario;
        this.created = created;
    }

    public Log(String action, String tabela, String reference, String oldValue, String newValue, Usuario usuario) {
        this.action = action;
        this.tabela = tabela;
        this.reference = reference;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.usuario = usuario;
        this.created = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
