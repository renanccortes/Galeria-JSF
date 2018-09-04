/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.entidades;

import br.com.galeria.tipos.TipoSexo;
import br.com.galeria.util.ArquivoUtil;
import br.com.galeria.util.ConstantesDiretorios;
import br.com.galeria.util.FacesUtil;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * CLASSE ABSTRATA COM OS ATRIBUTOS BÁSICOS DE UMA PESSOA //0 = Usuário Master,
 * 1 - Funcionario, 2 = Profissionais da Saude, 3 = Paciente, 4 = RESPONSAVEL
 * CLIENTE, 5 = Segurança do Trabalho 6 - ResponsavelConvenio,
 *
 * @author Renan Cortes
 */
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_PESSOA", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOA", nullable = false, unique = true)
    private Long idPessoa;

    @Column(name = "DES_NOME", length = 150)
    private String nome;

    @Column(name = "DES_CPF", length = 18)
    private String cpf;

    @Column(name = "DAT_DATA_NASCIMENTO")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Column(name = "DES_IDENTIDADE", length = 18)
    private String identidade;

    @Column(name = "DES_SEXO", length = 1)
    private String sexo;

    @Column(name = "DES_CEL", length = 15)
    private String cel;

    @Column(name = "DES_CELCORP", length = 15)
    private String celParticular;

    @Column(name = "DES_TEL", length = 15)
    private String tel;

    private String telObs;

    @Column(name = "DES_EMAIL", length = 70)
    private String email;

    @Column(name = "DES_EMAIL_2", length = 70)
    private String email2;

    @Column
    private byte[] foto;

    @Transient
    private String pathFoto;

    @Column
    private String matricula;

    @Column(length = 1000)
    private String observacoes;

    @Column(name = "FLG_RECEBE_INFO_POR_EMAIL", columnDefinition = "boolean default false")
    private boolean recebeInfoPorEmail = false;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.ORDINAL)
    private TipoSexo tipoSexo;

    @Transient
    private String cpfAntigo;

    @Transient
    private boolean edicao = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date criado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date excluido;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_PERMISSAO_USER")
    private PermissaoDeUsuario permissaoDeUsuario;

    public Pessoa() {
        endereco = new Endereco();

        setPath();

    }

    public void setPath() {
        if (getFoto() != null) {
            String pathAleatorio = ArquivoUtil.geraStringAleatoria();
            ArquivoUtil.gravaArquivo(FacesUtil.getCaminhoAbsoluto(ConstantesDiretorios.CAMINHO_IMAGENS_TEMP) + "/" + pathAleatorio, getFoto());
            this.setPathFoto(pathAleatorio);
        } else {
            this.setPathFoto("semimagem.jpg");
        }
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCpfFormatado() {
        if (cpf == null) {
            return "";
        }
        return FacesUtil.formatar(cpf, "###.###.###-##");
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getDataNascimentoFormatado() {
        if (dataNascimento == null) {
            return "";
        }
        return FacesUtil.converterData(dataNascimento);
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        //new Date( FacesUtil.converterStringAmericanoToStringBrasilData(dataNascimento));
        this.dataNascimento = new Date();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getSexo() {
        if (sexo == null || sexo.equals("")) {
            return "";
        }

        if (sexo.equals("M")) {
            return "Masculino";
        } else {
            return "Feminino";
        }

    }

    public void setSexo(String sexo) {
        if (sexo == null) {
            return;
        }
        if (sexo.length() != 1) {
            if (sexo.equalsIgnoreCase("MASCULINO")) {
                sexo = "M";
            } else {
                sexo = "F";
            }
        }
        this.sexo = sexo;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idPessoa);
        hash = 53 * hash + Objects.hashCode(this.cpf);
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
        final Pessoa other = (Pessoa) obj;
        if (Objects.equals(this.idPessoa, other.idPessoa)) {
            return true;
        }
        if (Objects.equals(this.cpf, other.cpf)) {
            return true;
        }
        return false;
    }

    public TipoSexo getTipoSexo() {
        if (tipoSexo == null) {
            tipoSexo = TipoSexo.MASCULINO;
        }
        return tipoSexo;
    }

    public void setTipoSexo(TipoSexo tipoSexo) {
        this.tipoSexo = tipoSexo;
    }

    public String getCelParticular() {
        return celParticular;
    }

    public void setCelParticular(String celParticular) {
        this.celParticular = celParticular;
    }

    public boolean isRecebeInfoPorEmail() {
        return recebeInfoPorEmail;
    }

    public void setRecebeInfoPorEmail(boolean recebeInfoPorEmail) {
        this.recebeInfoPorEmail = recebeInfoPorEmail;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public byte[] getFoto() {
       
//        if (foto != null) {
//            return foto;
//        }
//
//        if (this.getIdPessoa() == null) {
//            return null;
//        }
//
//        AwsService awsAmazon = new AwsAmazon();
//        InputStream receberArquivo = null;
//        try {
//            awsAmazon.conectar();
//
//            receberArquivo = awsAmazon.receberArquivo(this.getIdPessoa().toString(), AwsAmazon.PASTA_FOTO_USUARIO, AwsAmazon.BUCKET_ARQUIVOS);
//
//        } catch (AwsExcpetions ex) {
//
//        }
//
//        if (receberArquivo == null) {
//            return new byte[0];
//        }
//
//        foto = ArquivoUtil.inputStreamToByte(receberArquivo);

        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getCpfAntigo() {
        return cpfAntigo;
    }

    public void setCpfAntigo(String cpfAntigo) {
        this.cpfAntigo = cpfAntigo;
    }

    public String getTelObs() {
        return telObs;
    }

    public void setTelObs(String telObs) {
        this.telObs = telObs;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public PermissaoDeUsuario getPermissaoDeUsuario() {
        return permissaoDeUsuario;
    }

    public void setPermissaoDeUsuario(PermissaoDeUsuario permissaoDeUsuario) {
        this.permissaoDeUsuario = permissaoDeUsuario;
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

}
