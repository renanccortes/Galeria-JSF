/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.galeria.emails.ConteudoEmail;
import br.com.galeria.emails.EmailSender;
import br.com.galeria.emails.impl.ConteudoEmailUsuarios;
import br.com.galeria.emails.impl.EmailSenderImpl;
import br.com.galeria.entidades.Empresa;
import br.com.galeria.entidades.Pessoa;
import br.com.galeria.entidades.Usuario;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.table.LazyTableGenerico;
import br.com.galeria.tipos.TipoSexo;
import br.com.galeria.tipos.TipoUsuario;
import br.com.galeria.util.ArquivoUtil;
import br.com.galeria.util.ConstantesDiretorios;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author subversion
 */
@Named
@javax.faces.view.ViewScoped
public class EmpresaClienteMB implements Serializable {

    @EJB(beanName = "EmpresaServiceImpl")
    private ServiceGenerico<Empresa> empresaService;

    private Empresa empresaClienteSelecionada;
    private Usuario usuario;
    private List<Part> logoEnviado;
    private List<Part> fotoLocalEnviado;

//    private EmpresaCliente empresaClienteAtual;
    public EmpresaClienteMB() {
    }

    @PostConstruct
    private void init() {
        List<Empresa> empresas = empresaService.findAll();
        empresaClienteSelecionada = (empresas != null && !empresas.isEmpty()) ? empresas.get(0) : new Empresa();
        empresaClienteSelecionada.setPath();

        usuario = new Usuario();
    }

    public void onNovo() {
        empresaClienteSelecionada = new Empresa();
        FacesUtil.mostraDialog("cadastroEmpresaCliente", true);
    }

    public TipoSexo[] tipoSexo() {
        return TipoSexo.values();
    }

    public void onNovoFuncionario() {

        usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.USUARIO_FUNCIONARIO);
        usuario.setAdmin(false);
        usuario.setEmpresa(empresaClienteSelecionada);
    }

    public void onExcluirUsuario() {
        empresaClienteSelecionada.getFuncionarios().remove(usuario);
    }

    public void mudaTipoUsuario() {

        if (usuario != null) {
            if (usuario.isAdmin()) {
                usuario.setTipoUsuario(TipoUsuario.USUARIO_ADMINISTRADOR);
            } else {
                usuario.setTipoUsuario(TipoUsuario.USUARIO_FUNCIONARIO);
            }
        }
    }

    public void onEditarUsuario() {
        if (usuario != null) {
            usuario.setEdicao(true);
            usuario.setAdmin(usuario.getTipoUsuario() == TipoUsuario.USUARIO_ADMINISTRADOR);
        }
    }

    public void onIncluirUsuario() {
        if (!usuario.isEdicao()) {
            for (Usuario f : empresaClienteSelecionada.getFuncionarios()) {
                if (usuario.getCpf().equals(f.getCpf())) {
                    FacesUtil.addErrorMessage("CPFs não pode ser repetido.");
                    return;
                }

                if (usuario.getLogin().getLogin().equals(f.getLogin().getLogin())) {
                    FacesUtil.addErrorMessage("Login não pode ser repetido.");
                    return;
                }
            }

            usuario.setCriado(new Date());
            usuario.getLogin().setAtivo(true);
            usuario.setEmpresa(empresaClienteSelecionada);
            empresaClienteSelecionada.getFuncionarios().add(usuario);

        } else {
            empresaClienteSelecionada.getFuncionarios().remove(usuario);
            empresaClienteSelecionada.getFuncionarios().add(usuario);
        }
      
        FacesUtil.mostraDialog("cadastroFuncionarios", false);
    }

    private void onEnviarEmailPessoa(Pessoa pessoa) {

        EmailSender emailSender = new EmailSenderImpl();
        ConteudoEmail conteudoEmail = new ConteudoEmailUsuarios();
        conteudoEmail.configuraDados(pessoa, "Bem vindo ao Sistema da Galeria", usuario.getEmail());
        try {
            emailSender.enviarEmail(conteudoEmail);
            FacesUtil.addInfoMessage("Email enviado");
        } catch (MessagingException ex) {
            FacesUtil.addInfoMessage("Falha ao enviar e-mail. " + ex.getMessage());
        }
    }

    public void salvar() {
        try {
            if (empresaClienteSelecionada.getCnpj() == null || empresaClienteSelecionada.getCnpj().equals("")) {
                FacesMensagensUtil.adcionarMensagemCNpjInvalido();
                return;
            }
            if (empresaClienteSelecionada.getIdEmpresa() == null) {

                empresaClienteSelecionada.setDataCadastro(new Date());
                empresaClienteSelecionada = empresaService.onSalvar(empresaClienteSelecionada);
//               listaEmpresaClientes.add(empresaClienteSelecionada);
                FacesUtil.addInfoMessage("Empresa Cliente salva com sucesso");
            } else {
                empresaService.onSalvar(empresaClienteSelecionada);
                FacesUtil.addInfoMessage("Empresa Cliente atualizada com sucesso");
            }

            FacesUtil.mostraDialog("cadastroEmpresaCliente", false);
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage("Erro ao salvar Empresa Cliente: " + e.getMessage());
        }

    }

    public boolean isAtivaBotoes() {

        return empresaClienteSelecionada != null && empresaClienteSelecionada.getIdEmpresa() != null;
    }

    public void logoUpload(FileUploadEvent event) {

        try {
            UploadedFile logoUpload = event.getFile();
            if (logoUpload == null || logoUpload.getContents() == null) {
                FacesUtil.addErrorMessage("Escolha um arquivo");
                return;
            }

            //  byte[] ass = ArquivoUtil.inputStreamToByte(assinaturaUpload.getInputstream());
            byte[] logo = logoUpload.getContents();
            empresaClienteSelecionada.setLogomarca(logo);
            String pathAleatorio = ArquivoUtil.geraStringAleatoria();
            ArquivoUtil.gravaArquivo(FacesUtil.getCaminhoAbsoluto(ConstantesDiretorios.CAMINHO_IMAGENS_TEMP) + "/" + pathAleatorio, logo);
            empresaClienteSelecionada.setPathLogomarca(pathAleatorio);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removerLogo() {
        empresaClienteSelecionada.setLogomarca(null);
        empresaClienteSelecionada.setPath();
    }

    public void sessaoFinalizada() {
        FacesUtil.addErrorMessage("Sua sessão expirou, faça a consulta novamente.");
    }

    public boolean ativaBotoesUsuario() {
        return usuario != null && usuario.getCpf() != null && !usuario.getCpf().equals("");
    }

    public List<Part> getLogoEnviado() {
        return logoEnviado;
    }

    public void setLogoEnviado(List<Part> logoEnviado) {
        this.logoEnviado = logoEnviado;
    }

    public Empresa getEmpresaClienteSelecionada() {
        return empresaClienteSelecionada;
    }

    public void setEmpresaClienteSelecionada(Empresa empresaClienteSelecionada) {
        this.empresaClienteSelecionada = empresaClienteSelecionada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Part> getFotoLocalEnviado() {
        return fotoLocalEnviado;
    }

    public void setFotoLocalEnviado(List<Part> fotoLocalEnviado) {
        this.fotoLocalEnviado = fotoLocalEnviado;
    }

}
