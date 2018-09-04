package br.com.galeria.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.galeria.constantes.Status;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Locatario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOCATARIO", nullable = false, unique = true)
    private Long id;

    private String nome;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    private String responsavel;

    private String email;

    private String telefone;

    private String documento;

    @OneToMany(mappedBy = "locatario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Contrato> contratos;

    @Embedded
    private Endereco endereco;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date created;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date updated;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date deleted;

    protected boolean status;

    public Locatario() {
        status = true;
    }

    public Locatario(String nome, String nomeFantasia, String responsavel, String email, String telefone, String documento) {
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.responsavel = responsavel;
        this.email = email;
        this.telefone = telefone;
        this.documento = documento;
        this.status = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Locatario other = (Locatario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
