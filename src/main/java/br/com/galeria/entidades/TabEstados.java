/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.galeria.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "TAB_ESTADOS")
public class TabEstados implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length=21, unique=true)
    private Long id;
    
    @Column(name="uf", length=10, nullable=false, unique=false)
    private String uf;
    
    @Column(name="nome", length=20, nullable=false, unique=false)
    private String nome;
    
     @OneToMany(cascade = CascadeType.ALL , mappedBy = "estado", fetch = FetchType.LAZY)
    private List<TabCidades> listaCidades;

     
     public TabEstados(){
         listaCidades = new ArrayList<>();
     }
     
    public Long getId() {
        return id;
    }

    public String getUf() {
        return uf;
    }

    public String getNome() {
        return nome;
    }

    public List<TabCidades> getListaCidades() {
        return listaCidades;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setListaCidades(List<TabCidades> listaCidades) {
        this.listaCidades = listaCidades;
    }
     
     
    
    
    
}
