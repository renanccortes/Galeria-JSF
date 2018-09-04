package br.com.galeria.entidades;

import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Endereco
 *
 */
@Embeddable
public class Endereco implements Serializable {

    @Column(name = "DES_LOGRADOURO", length = 100)
    @Basic(fetch = FetchType.LAZY)
    private String desLogradouro;

    @Column(name = "DES_NUMERO", length = 20)
    @Basic(fetch = FetchType.LAZY)
    private String desNumero;

    @Column(name = "DES_COMPLEMENTO", length = 100)
    @Basic(fetch = FetchType.LAZY)
    private String desComplemento;

    @Column(name = "DES_BAIRRO", length = 100)
    @Basic(fetch = FetchType.LAZY)
    private String desBairro;

    @Column(name = "DES_CEP", length = 20)
    @Basic(fetch = FetchType.LAZY)
    private String desCep;

    @Column(name = "DES_CIDADE", length = 150)
    @Basic(fetch = FetchType.LAZY)
    private String cidade;

    @Column(name = "DES_UF", length = 70)
    @Basic(fetch = FetchType.LAZY)
    private String uf;

    private String codUf;

    @Transient
    private boolean encontrado = true;

    //valores antigos para cancelamento na view
    @Transient
    private String cepAntigo;

    @Transient
    private String logradouroAntigo;

    @Transient
    private String complementoAntigo;

    @Transient
    private String bairroAntigo;

    @Transient
    private String numeroAntigo;

    @Transient
    private String cidadeAntiga;

    @Transient
    private String ufAntiga;

    private static final long serialVersionUID = 1L;

    public Endereco() {

        super();

    }

    public Endereco(String desLogradouro, String desNumero, String desComplemento, String desBairro, String desCep, TabCidades cidade) {
        super();
        this.desLogradouro = desLogradouro;
        this.desNumero = desNumero;
        this.desComplemento = desComplemento;
        this.desBairro = desBairro;
        this.desCep = desCep;
        this.cidade = cidade.getNome();
        this.uf = cidade.getEstado().getNome();
        this.codUf = cidade.getEstado().getUf();

    }

    public void cancelarMudancas() {
        desCep = cepAntigo;
        desLogradouro = logradouroAntigo;
        desBairro = bairroAntigo;
        desNumero = numeroAntigo;
        cidade = cidadeAntiga;
        desComplemento = complementoAntigo;
        uf = ufAntiga;
        cidade = cidadeAntiga;
    }

    private void setDadosAntigos() {
        if (desCep == null || desCep.equals("")) {
            encontrado = false;
        }

        cepAntigo = desCep;
        logradouroAntigo = desLogradouro;
        bairroAntigo = desBairro;
        numeroAntigo = desNumero;
        cidadeAntiga = cidade;
        complementoAntigo = desComplemento;
        cidadeAntiga = cidade;
        ufAntiga = uf;
    }

    private void clearCampos() {
        this.cidade = "";
        this.uf = "";
        this.desLogradouro = "";
        this.desBairro = "";
//            this.desNumero = "";
//            this.desComplemento = "";

    }

    public String getDesLogradouro() {
        return desLogradouro;
    }

    public void setDesLogradouro(String desLogradouro) {
        this.desLogradouro = desLogradouro;
    }

    public String getDesNumero() {
        return desNumero;
    }

    public void setDesNumero(String desNumero) {
        this.desNumero = desNumero;
    }

    public String getDesComplemento() {
        return desComplemento;
    }

    public void setDesComplemento(String desComplemento) {
        this.desComplemento = desComplemento;
    }

    public String getDesBairro() {
        return desBairro;
    }

    public void setDesBairro(String desBairro) {
        this.desBairro = desBairro;
    }

    public String getDesCep() {
        if (cepAntigo == null || cepAntigo.equals("")) {
            setDadosAntigos();
        }

        if (desCep == null) {
            desCep = "";
        }

        return desCep;
    }

    public void setDesCep(String desCep) {
        this.desCep = desCep;
    }

    public String getCidade() {

        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        String enderecoQualificado = desLogradouro + ", " + desNumero + " - " + desBairro + " - CEP: " + desCep + " - " + cidade + "-" + uf;
        return enderecoQualificado;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCodUf() {
        return codUf;
    }

    public void setCodUf(String codUf) {
        this.codUf = codUf;
    }

    public String getCepAntigo() {
        return cepAntigo;
    }

    public void setCepAntigo(String cepAntigo) {
        this.cepAntigo = cepAntigo;
    }

    public String getLogradouroAntigo() {
        return logradouroAntigo;
    }

    public void setLogradouroAntigo(String logradouroAntigo) {
        this.logradouroAntigo = logradouroAntigo;
    }

    public String getComplementoAntigo() {
        return complementoAntigo;
    }

    public void setComplementoAntigo(String complementoAntigo) {
        this.complementoAntigo = complementoAntigo;
    }

    public String getBairroAntigo() {
        return bairroAntigo;
    }

    public void setBairroAntigo(String bairroAntigo) {
        this.bairroAntigo = bairroAntigo;
    }

    public String getNumeroAntigo() {
        return numeroAntigo;
    }

    public void setNumeroAntigo(String numeroAntigo) {
        this.numeroAntigo = numeroAntigo;
    }

    public String getCidadeAntiga() {
        return cidadeAntiga;
    }

    public void setCidadeAntiga(String cidadeAntiga) {
        this.cidadeAntiga = cidadeAntiga;
    }

    public String getUfAntiga() {
        return ufAntiga;
    }

    public void setUfAntiga(String ufAntiga) {
        this.ufAntiga = ufAntiga;
    }

}
