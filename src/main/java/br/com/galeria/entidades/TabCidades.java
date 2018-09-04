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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author subversion
 */
@Entity
@Table(name = "TAB_CIDADES")
public class TabCidades implements Serializable {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
    
    @Column(name = "DES_CODIGO_IBGE",length = 7,  nullable = true)
    private String codigoIBGE;
   
   @Column(name = "DES_NOME_CIDADE", length = 75, nullable = false) 
   private String nome;
   @Column(name = "DES_NOME_MUNICIPIO", length = 150, nullable = false) 
   private String nomeMunicipio;
   @Column(name = "DES_CEP", length = 10, nullable = false) 
   private String cep;
   @Column(name = "DES_UF", length = 2, nullable = false) 
   private String uf;
   @Column(name = "DES_TIPO", length = 1,  nullable = false) 
   private String tipo;
   
   
   @ManyToOne
   @JoinColumn(name = "estado", referencedColumnName = "id", nullable = true)
   private TabEstados estado;
   
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "cidade", fetch = FetchType.LAZY)
    private List<TabLogradouro> listaLogradouro;
   
   public TabCidades(){
       
   }
   
   public TabCidades(String nome, String codigoIbge, String uf){
        this.nome = nome;
        this.codigoIBGE = codigoIbge;
        this.uf = uf;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final TabCidades other = (TabCidades) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
   
   
   public TabCidades(String nomeCidade, String nomeMunicipio, String cep, String uf, String tipo, Long id){
       this.nome = nomeCidade;
       this.nomeMunicipio = nomeMunicipio;
       this.cep = cep;
       this.uf = uf;
       this.tipo = tipo;
       this.id = id;
       
   }
   
    @Override
   public String toString(){
       return nome+", "+nomeMunicipio+ ", "+cep+", "+uf+", "+tipo;
   }

    public Long getId() {
        return id;
    }

    public String getCodigoIBGE() {
        return codigoIBGE;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public String getCep() {
        return cep;
    }

    public String getUf() {
        return uf;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public TabEstados getEstado() {
        return estado;
    }

    public void setEstado(TabEstados estado) {
        this.estado = estado;
    }

    public List<TabLogradouro> getListaLogradouro() {
        return listaLogradouro;
    }

    public void setListaLogradouro(List<TabLogradouro> listaLogradouro) {
        this.listaLogradouro = listaLogradouro;
    }

    
   
    
    
    
    
}
