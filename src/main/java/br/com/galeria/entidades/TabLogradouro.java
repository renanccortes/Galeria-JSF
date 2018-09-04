/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.galeria.entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author subversion
 */
@Entity
@Table(name = "TAB_LOGRADOURO")
public class TabLogradouro implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length=21, unique=true)
    private Integer id;
    
    @Column(name="DES_NOME_LOGRADOURO",length = 100,  nullable=true, unique=false)
    private String nomeLogradouro;
    
    @Column(name="DES_NOME_LOGRADOURO_SEM_ACENTO",length = 100,  nullable=true, unique=false)
    private String nomeLogradouroSemAcento;
    
    @Column(name="DES_TIPO_LOGRADOURO",length = 30,  nullable=true, unique=false)
    private String tipoLogradouro;
    
    @Column(name="DES_CEP_LOGRADOURO", length = 10, nullable=true, unique=false)
    private String cepLogradouro;
    
    @ManyToOne
    @JoinColumn(name = "cidade", referencedColumnName = "id", nullable = true)
    private TabCidades cidade;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bairro", referencedColumnName = "id", nullable = true)
    private TabBairros bairro;
     
    
    public TabLogradouro(){
        bairro = new TabBairros();
        
    }
    
    public TabLogradouro(String nomeLogradouro, String nomeLogradouroSemAcento, String tipoLogradouro, String cepLogradouro, String idCidade, String idBairro){
        this.nomeLogradouro = nomeLogradouro;
        this.nomeLogradouroSemAcento = nomeLogradouroSemAcento;
        this.tipoLogradouro = tipoLogradouro;
        this.cepLogradouro = cepLogradouro;
        cidade = new TabCidades();
        cidade.setId(new Long(idCidade));
        bairro = new TabBairros();
        bairro.setId(new Long(idBairro));
     
    }

    public Integer getId() {
        return id;
    }

    public String getNomeLogradouro() {
        return nomeLogradouro;
    }

    public String getNomeLogradouroSemAcento() {
        return nomeLogradouroSemAcento;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public String getCepLogradouro() {
        return cepLogradouro;
    }

    public TabCidades getCidade() {
        return cidade;
    }

    public TabBairros getBairro() {
        return bairro;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNomeLogradouro(String nomeLogradouro) {
        this.nomeLogradouro = nomeLogradouro;
    }

    public void setNomeLogradouroSemAcento(String nomeLogradouroSemAcento) {
        this.nomeLogradouroSemAcento = nomeLogradouroSemAcento;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public void setCepLogradouro(String cepLogradouro) {
        this.cepLogradouro = cepLogradouro;
    }

    public void setCidade(TabCidades cidade) {
        this.cidade = cidade;
    }

    public void setBairro(TabBairros bairro) {
        this.bairro = bairro;
    }

    
    
    
    
}
