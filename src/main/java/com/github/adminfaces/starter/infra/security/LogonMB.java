package com.github.adminfaces.starter.infra.security;

import br.com.galeria.entidades.AcaoDoUsuario;
import br.com.galeria.entidades.Banco;
import br.com.galeria.entidades.Empresa;
import br.com.galeria.entidades.Endereco;
import br.com.galeria.entidades.Locatario;
import br.com.galeria.entidades.Login;
import br.com.galeria.entidades.Pessoa;
import br.com.galeria.entidades.Piso;
import br.com.galeria.entidades.Sala;
import br.com.galeria.entidades.TabCidades;
import br.com.galeria.entidades.TabEstados;
import br.com.galeria.entidades.Usuario;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.services.ServiceUsuario;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.tipos.BancosEnum;
import br.com.galeria.tipos.TipoUsuario;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login
 * page or not. By default AdminSession isLoggedIn always resolves to true so it
 * will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user
 * must be redirect to initial page or logon you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

//    private final String cookieUserName = "gide-user-default";
//    private final String cookiePassword = "gide-pass-user";
    private boolean remember = true;

    private Usuario usuarioLogado;

    @EJB(beanName = "UsuarioServiceImpl")
    private ServiceUsuario usuarioService;

    @EJB(beanName = "LocatarioServiceImpl")
    private ServiceGenerico<Locatario> locatarioService;

    @EJB(beanName = "PisoServiceImpl")
    private ServiceGenerico<Piso> pisoService;

    @EJB(beanName = "EmpresaServiceImpl")
    ServiceGenerico<Empresa> empresaService;

    //BancoServiceImpl
    @EJB(beanName = "BancoServiceImpl")
    ServiceGenerico<Banco> bancoService;

    private Acao acao;
    private AreaDoSistema areaDoSistema;

    public LogonMB() {

    }

    @PostConstruct
    private void init() {
        usuarioLogado = new Usuario();
    }

    public String getContext() {

        System.out.println("Context: " + FacesContext.getCurrentInstance().getExternalContext().getContext());

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        final String url = request.getRequestURI();

        System.out.println(Faces.getContext().getExternalContext().getRequestContextPath());
        return url;
    }

    private void cadastrarEmpresaPadrao() {

        Map<String, Object> filtro = new HashMap<>();
        filtro.put("nomeFantasia", "Empresa Padrão");
        Empresa buscaUnicaComParametros = empresaService.buscaUnicaUnicaComParametros(filtro, new HashMap<>());
        if (buscaUnicaComParametros != null) {
            FacesMensagensUtil.adcionarMensagemErro("Empresa já cadastrada");
            return;
        }

        Empresa empresa = new Empresa();
        empresa.setNomeFantasia("Empresa Padrão");
        empresa.setRazaoSocial("Empresa Padrão");

        Usuario usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.USUARIO_ADMINISTRADOR);
        Login login = new Login(usuario);
        login.setAtivo(true);
        login.setLogin("admin");
        login.setSenha("admin");
        usuario.setLogin(login);
        usuario.setEmpresa(empresa);
        empresa.setFuncionarios(new ArrayList<>());
        empresa.getFuncionarios().add(usuario);

        Locatario locatarioPadrao = new Locatario("Ronaldo", "Ronaldin", "Eu mesmo", "ronaldo@ronaldo.com.br", "17981236461", "06992622676");
        TabCidades cidade = new TabCidades();
        cidade.setNome("São José do Rio Preto");
        cidade.setUf("SP");
        TabEstados estado = new TabEstados();
        estado.setNome("São Paulo");
        estado.setUf("SP");
        cidade.setEstado(estado);
        Endereco endereco = new Endereco("Avenida José da Silva Sé", "305", "CASA 141", "Parque da Liberdade", "15056750", cidade);
        locatarioPadrao.setEndereco(endereco);
        locatarioService.onSalvar(locatarioPadrao);

        Piso pisoPadrao = new Piso(0, "Primeiro Andar");
        Sala salaPadrao = new Sala("50", "10", "25%", 101, pisoPadrao);
        pisoPadrao.setSalas(new ArrayList<>());
        pisoPadrao.getSalas().add(salaPadrao);
        pisoService.onSalvar(pisoPadrao);

        Banco bancoPadrao = new Banco();
        bancoPadrao.setAgenciaDigito("1");
        bancoPadrao.setAgenciaNumero("010203");
        bancoPadrao.setBanco(BancosEnum.ITAU);
        bancoPadrao.setContaDigito("2");
        bancoPadrao.setContaNumero("090807");
        bancoPadrao.setNomeBeneficiado("Renan Cortes Carvalho");
        bancoPadrao.setStatus(true);

        bancoService.onSalvar(bancoPadrao);

        empresaService.onSalvar(empresa);
        FacesMensagensUtil.adcionarMensagem("Empresa padrão cadastrada");
    }

    public void login() throws IOException {

        if (usuarioLogado == null) {
            return;
        }

        //PARA CADASTAR EMPRESA PADRÃO
        if (usuarioLogado.getLogin().getLogin().equals("root") && usuarioLogado.getLogin().getSenhaDescrip().equals("root")) {
            //cadastrar padrão
            cadastrarEmpresaPadrao();
            return;
        }

        try {
            usuarioLogado = usuarioService.onLogar(usuarioLogado);

            if (usuarioLogado != null && usuarioLogado.getIdPessoa() != null) {

                usuarioLogado.criarIdUnico();
                //VERIFICA O TIPO DO LOGIN, SE FOR MASTER REDIRECIONA PRO PAINEL MASTER
                if (usuarioLogado.getTipoUsuario() == TipoUsuario.USUARIO_MASTER) {
                    Faces.redirect("sistema/inicio.jsf");
                } else {
                    usuarioLogado.setPath();
                    Faces.redirect("sistema/inicio.jsf");
                }
                System.out.println("logado com sucesso...");
                System.out.println(usuarioLogado.getIdPessoa());
            } else {

                usuarioLogado = new Usuario();
                FacesMensagensUtil.adcionarMensagem("Usuário e/ou Senha inválida(s)");

            }
        } catch (Exception e) {
            e.printStackTrace();
            usuarioLogado = new Usuario();
            FacesMensagensUtil.adcionarMensagem("Usuário e/ou Senha inválida(s)");

        }

//        currentUser = email;
//        usuarioLogado = new Usuario();
//        addDetailMessage("Logged in successfully as <b>" + email + "</b>");
//        Faces.getExternalContext().getFlash().setKeepMessages(true);
//        Faces.redirect("sistema/cad_arquivos.jsf");
    }

//    public void logoff() {
//        this.setUsuarioLogado(null);
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        try {
//            Faces.redirect("/login.jsf");
//        } catch (IOException ex) {
//            Logger.getLogger(LogonMB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public boolean isPermitido(AreaDoSistema areaDoSistema) {
        Pessoa pessoa = getUsuarioLogado();//usuarioService.findOne(sessaoMB.getUsuarioLogado().getIdPessoa());

        if (getUsuarioLogado().isAdministrador()) {
            return true;
        }

        if (areaDoSistema == null) {
            return false;
        }

        if (pessoa.getPermissaoDeUsuario() == null || pessoa.getPermissaoDeUsuario().getPermissoes() == null) {
            FacesUtil.addInfoMessage("Sem permissão para " + Acao.VISUALIZAR + " na área " + areaDoSistema + ".");
            return false;
        }

        AcaoDoUsuario acoesLiberadas = pessoa.getPermissaoDeUsuario().getPermissoes().get(areaDoSistema);

        if (acoesLiberadas == null) {
            return false;
        } else if (!acoesLiberadas.isPermitido(Acao.VISUALIZAR)) {
            return false;
        }

        return true;

    }

    @Override
    public boolean isLoggedIn() {

        return usuarioLogado.getIdPessoa() != null;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public AreaDoSistema getAreaDoSistema() {
        return areaDoSistema;
    }

    public void setAreaDoSistema(AreaDoSistema areaDoSistema) {
        this.areaDoSistema = areaDoSistema;
    }

}
