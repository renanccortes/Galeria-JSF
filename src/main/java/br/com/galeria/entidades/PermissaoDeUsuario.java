/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.tipos.AreaDoSistema;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

/**
 *
 * @author Renan, Mar 20, 2018 4:23:57 PM
 */
@Entity(name = "permissao_de_usuario")
public class PermissaoDeUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nomeDoPerfil;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "permissoes_usuario",
            joinColumns = @JoinColumn(name = "id_permissao_de_usuario", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_acaodousuario", referencedColumnName = "id"))
    @MapKeyColumn(name = "area_do_sistema")
    private Map<AreaDoSistema, AcaoDoUsuario> permissoes;

    public PermissaoDeUsuario() {
        permissoes = new HashMap<>();
    }

    public PermissaoDeUsuario(PerfilDeAcesso perfilDeAcesso) {
        this();
        if (perfilDeAcesso == null) {
            throw new NullPointerException("(Perfil de Acesso é null");
        }

        Map<AreaDoSistema, AcaoDoUsuario> areaAcoes = perfilDeAcesso.getPermissoes();
        //faz uma cópia das permissoes do obj perfilDeAcesso para this.permissoes
        for (Map.Entry<AreaDoSistema, AcaoDoUsuario> entry : areaAcoes.entrySet()) {
            //cria um novo obj AcaoDoUsuario identico ao referido pela chave atual no loop nas permissoes do obj perfil
            AcaoDoUsuario acaoDoUsuario = new AcaoDoUsuario(entry.getValue().getAcoes());
            this.permissoes.put(entry.getKey(), acaoDoUsuario);
        }

        this.nomeDoPerfil = perfilDeAcesso.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoPerfil() {
        return nomeDoPerfil;
    }

    public void setNomeDoPerfil(String nomeDoPerfil) {
        this.nomeDoPerfil = nomeDoPerfil;
    }

    public Map<AreaDoSistema, AcaoDoUsuario> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Map<AreaDoSistema, AcaoDoUsuario> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final PermissaoDeUsuario other = (PermissaoDeUsuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
