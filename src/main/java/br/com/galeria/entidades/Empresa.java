/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.util.ArquivoUtil;
import br.com.galeria.util.ConstantesDiretorios;
import br.com.galeria.util.FacesUtil;
import static br.com.galeria.util.FacesUtil.formatFileSize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * CLASSE ABSTRATA COM OS ATRIBUTOS BÁSICOS DE UMA EMPRESA 1 = Empresa
 * Administradora. 2 = Empresa Cliente, 3 = Empresa Fornecedor , 4 Empresa
 * Convenio
 */
@Entity
@Table(name = "empresa")
//@DiscriminatorColumn(name = "TIPO_EMPRESA", discriminatorType = DiscriminatorType.INTEGER)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA", nullable = false, unique = true)
    private Long idEmpresa;

    @Column(name = "DES_CNPJ", length = 20)
    private String cnpj = "";

    @Column(name = "DES_NOME_FANTASIA", nullable = false, length = 70)
    private String nomeFantasia;

    @Column(name = "DES_RAZAO", nullable = false, length = 70)
    private String razaoSocial;

    @Column(name = "DES_INSC_MUNICIPAL", length = 30)
    @Basic(fetch = FetchType.LAZY)
    private String inscricaoMunicipal;

    @Column(name = "DES_INSC_ESTADUAL", length = 30)
    private String inscricaoEstadual;

    @Column(name = "DES_LOGOMARCA")
    private byte[] logomarca;

    @Embedded
    private Endereco endereco;

    @Column(name = "FLG_ATIVO")
    private boolean ativo = Boolean.TRUE;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "EMAIL_CONTATO")
    private String emailContato;

    @Column(name = "DES_SITE")
    private String site;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "CELULAR")
    private String celular;

    @Column(name = "DES_SKYPE")
    private String skype;

    @Column(name = "DES_OBS", length = 2000)
    private String obs;

    @Column(name = "TEM_DATA_CADASTRO")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    @Transient //USADA APENAS PARA CONTROLE NA VIEW
    protected boolean edicao;

    private String codigo;

    @Transient
    private String pathLogomarca;

    @Transient
    private String pathMarcaAgua;

    @Column(name = "FLG_JURIDICA", columnDefinition = "boolean default true")
    protected boolean juridica = true;

    @Column(name = "FLG_WHATSAP", columnDefinition = "boolean default false")
    private boolean whatsAp;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Usuario> funcionarios;

    public Empresa() {
        pathLogomarca = "semimagem.jpg";
        pathMarcaAgua = "semimagem.jpg";
        //data de cadastro default, data atual
        dataCadastro = new Date();
        funcionarios = new ArrayList<>();
    }

    public void setPath() {
        if (getLogomarca() != null) {
            String pathAleatorio = ArquivoUtil.geraStringAleatoria();
            ArquivoUtil.gravaArquivo(FacesUtil.getCaminhoAbsoluto(ConstantesDiretorios.CAMINHO_IMAGENS_TEMP) + "/" + pathAleatorio, getLogomarca());
            this.setPathLogomarca(pathAleatorio);
        } else {
            this.setPathLogomarca("semimagem.jpg");
        }
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public byte[] getLogomarca() {
        return logomarca;
    }

    public void setLogomarca(byte[] logomarca) {
        this.logomarca = logomarca;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }

        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPathLogomarca() {
        return pathLogomarca;
    }

    public void setPathLogomarca(String pathLogomarca) {
        this.pathLogomarca = pathLogomarca;
    }

    public String getPathMarcaAgua() {
        return pathMarcaAgua;
    }

    public void setPathMarcaAgua(String pathMarcaAgua) {
        this.pathMarcaAgua = pathMarcaAgua;
    }

    public boolean isModoEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public boolean isJuridica() {
        return juridica;
    }

    public void setJuridica(boolean juridica) {
        this.juridica = juridica;
    }

    public boolean isWhatsAp() {
        return whatsAp;
    }

    public void setWhatsAp(boolean whatsAp) {
        this.whatsAp = whatsAp;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<Usuario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Usuario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idEmpresa);
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
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.idEmpresa, other.idEmpresa)) {
            return false;
        }
        return true;
    }

}
