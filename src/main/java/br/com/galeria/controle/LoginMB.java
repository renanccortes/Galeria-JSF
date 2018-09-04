///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.galeria.controle;
//
//import br.com.galeria.entidades.Empresa;
//import br.com.galeria.entidades.Login;
//import br.com.galeria.entidades.Usuario;
//import br.com.galeria.services.ServiceGenerico;
//import br.com.galeria.services.ServiceUsuario;
//import br.com.galeria.tipos.TipoUsuario;
//import br.com.galeria.util.FacesMensagensUtil;
//import com.github.adminfaces.starter.infra.security.LogonMB;
//import com.github.adminfaces.template.session.AdminSession;
//import com.xpert.faces.utils.FacesUtils;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
//import javax.inject.Named; 
//
///**
// *
// * @author Renan
// */
//@Named
//@RequestScoped
//public class LoginMB implements Serializable {
//    
//    private final String cookieUserName = "gide-user-default";
//    private final String cookiePassword = "gide-pass-user";
//
//    private final String CHANNEL_PRIVATE = "/private/";
//    private Usuario usuario;
//
//    @EJB(beanName = "UsuarioServiceImpl")
//    private ServiceUsuario usuarioService;
//
//    @EJB(beanName = "EmpresaServiceImpl")
//    ServiceGenerico<Empresa> empresaService;
//
//    @Inject
//    private LogonMB sessaoMB;
//
//    @Inject
//    private AdminSession adminSession;
//    
//    @Inject
//    private LogonMB logonMB;
//
////    @PostConstruct
//    public void init() { 
//                 try {
//            if (FacesUtils.getCookieValue(cookieUserName) != null && !FacesUtils.getCookieValue(cookieUserName).isEmpty()) {
//                logonMB.getUsuarioLogado().getLogin().setLogin(FacesUtils.getCookieValue(cookieUserName));
//            } else {
//                return;
//            }
//            if (FacesUtils.getCookieValue(cookiePassword) != null && !FacesUtils.getCookieValue(cookiePassword).isEmpty()) {
//                logonMB.getUsuarioLogado().getLogin().setSenhaCriptografada(FacesUtils.getCookieValue(cookiePassword));
//            } else {
//                return;
//            }
//
//            logonMB.login();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void cadastrarEmpresaPadrao() {
//
//        Map<String, Object> filtro = new HashMap<>();
//        filtro.put("nomeFantasia", "Empresa Padrão");
//        Empresa buscaUnicaComParametros = empresaService.buscaUnicaUnicaComParametros(filtro, new HashMap<>());
//        if (buscaUnicaComParametros != null) {
//            FacesMensagensUtil.adcionarMensagem("Empresa já cadastrada");
//            return;
//        }
//
//        Empresa empresa = new Empresa();
//        empresa.setNomeFantasia("Empresa Padrão");
//        empresa.setRazaoSocial("Empresa Padrão");
//
//        Usuario usuario = new Usuario();
//        usuario.setTipoUsuario(TipoUsuario.USUARIO_ADMINISTRADOR);
//        Login login = new Login(usuario);
//        login.setAtivo(true);
//        login.setLogin("admin");
//        login.setSenha("admin");
//        usuario.setLogin(login);
//        usuario.setEmpresa(empresa);
//        empresa.setFuncionarios(new ArrayList<>());
//        empresa.getFuncionarios().add(usuario);
//
//        empresaService.onSalvar(empresa);
//        FacesMensagensUtil.adcionarMensagem("Empresa padrão cadastrada");
//    }
//
//    public String onLogin() {
//
//        //PARA CADASTAR EMPRESA PADRÃO
//        if (usuario.getLogin().getLogin().equals("root") && usuario.getLogin().getSenhaDescrip().equals("root")) {
//            //cadastrar padrão
//            cadastrarEmpresaPadrao();
//            return "";
//        }
//
//        try {
//            usuario = usuarioService.onLogar(usuario);
//            if (usuario != null && usuario.getIdPessoa() != null) {
//                usuario.criarIdUnico();
//                sessaoMB.setUsuarioLogado(usuario);
//                adminSession.setIsLoggedIn(true); 
//                System.out.println("adminSession9999999999: "+ adminSession.isLoggedIn());
//                //VERIFICA O TIPO DO LOGIN, SE FOR MASTER REDIRECIONA PRO PAINEL MASTER
//                if (usuario.getTipoUsuario() == TipoUsuario.USUARIO_MASTER) {
//                  //  Faces.redirect("sistema/index.jsf");
//                } else {
//                    desconectarOutrasSessoes();
//                    usuario.setPath();
//                  //  Faces.redirect("sistema/index.jsf");
//                }
//                System.out.println("adminSession2: "+ adminSession.isLoggedIn());
//            } else {
//                adminSession.setIsLoggedIn(false);
//                usuario = new Usuario();
//                FacesMensagensUtil.adcionarMensagem("Usuário e/ou Senha inválida(s)");
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            usuario = new Usuario();
//            FacesMensagensUtil.adcionarMensagem("Usuário e/ou Senha inválida(s)");
//            adminSession.setIsLoggedIn(false);
//            return null;
//        }
//        
//        System.out.println("adminSession3: "+ adminSession.isLoggedIn());
//        
//        return null;
//
//    }
//
//    public String onLogout() {
//        sessaoMB.setUsuarioLogado(null);
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        return "logout";
//    }
//
//    private void desconectarOutrasSessoes() {
////        EventBus eventBus = EventBusFactory.getDefault().eventBus();
////        eventBus.publish(CHANNEL_PRIVATE + usuario.getIdPessoa(), usuario.getIdUnico());
//    }
//
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }
//
//   
//}
