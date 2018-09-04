/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.galeria.entidades;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author subversion
 */

@Entity
@Table(name = "TAB_BAIRROS")
public class TabBairros implements Serializable{
    
        @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
        
        @Column(name="DES_NOME_BAIRRO", length = 80,  nullable=false, unique=false)
        private String nomeBairro;
        
        @Column(name="DES_NOME_BAIRRO_ABREVIADO", length = 80,  nullable=true, unique=false)
        private String nomeBairroAbreviado;
        
         @OneToMany(cascade = CascadeType.ALL , mappedBy = "bairro", fetch = FetchType.LAZY)
         private List<TabLogradouro> listaLogradouro;
         
         
         public TabBairros(){
             
         }
         
         public TabBairros(Long id, String nomeBairro, String nomeBairroAbreviado){
             this.id = id;
             this.nomeBairro = nomeBairro;
             this.nomeBairroAbreviado = nomeBairroAbreviado;
             
         }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final TabBairros other = (TabBairros) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
         
        

    public Long getId() {
        return id;
    }

    public String getNomeBairro() {
        return nomeBairro;
    }

    public String getNomeBairroAbreviado() {
        return nomeBairroAbreviado;
    }

    public List<TabLogradouro> getListaLogradouro() {
        return listaLogradouro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeBairro(String nomeBairro) {
        this.nomeBairro = nomeBairro;
    }

    public void setNomeBairroAbreviado(String nomeBairroAbreviado) {
        this.nomeBairroAbreviado = nomeBairroAbreviado;
    }

    public void setListaLogradouro(List<TabLogradouro> listaLogradouro) {
        this.listaLogradouro = listaLogradouro;
    }
         
         
    
}
