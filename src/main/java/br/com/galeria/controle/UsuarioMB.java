/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.AcaoDoUsuario;
import br.com.galeria.entidades.PerfilDeAcesso;
import br.com.galeria.entidades.PermissaoDeUsuario;
import br.com.galeria.entidades.Usuario;
import br.com.galeria.interceptor.ChecarPermissao;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.services.ServiceUsuario;
import br.com.galeria.table.LazyTableGenerico;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.tipos.TipoSexo;
import br.com.galeria.tipos.TipoUsuario;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import br.com.galeria.util.PermissoesUtil;
import static br.com.galeria.util.PermissoesUtil.ITEM_NODE;
import static br.com.galeria.util.PermissoesUtil.MENU_NODE;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Renan
 */
//@ManagedBean
//@ViewScoped
@Named
@javax.faces.view.ViewScoped
public class UsuarioMB implements Serializable {

    @EJB(beanName = "UsuarioServiceImpl")
    ServiceUsuario usuarioService;

    @EJB(beanName = "PerfilServiceImpl")
    private ServiceGenerico<PerfilDeAcesso> perfilService;

    private LazyTableGenerico<Usuario> tabela;

    private final String DIIALOG_CADASTRO = "cadastroUsuario";

    private Usuario usuario;
    private PerfilDeAcesso perfilSelecionado;
    private TreeNode root;
    private TreeNode[] nosSelecionados;
    private List<PerfilDeAcesso> perfisCadastrados;

    private PermissaoDeUsuario permissaoDeUsuario;

    private final String AREA = "Usuario";

    @Inject
    private LogonMB sessaoMB;

    @PostConstruct
    private void init() {
        System.out.println("managedBean criado com sucesso..");
        usuario = usuarioService.findOne(sessaoMB.getUsuarioLogado().getIdPessoa());
        usuario.setPath();
        tabela = new LazyTableGenerico<>(usuarioService);
        perfilSelecionado = new PerfilDeAcesso();
        perfilSelecionado.setPermissoes(new HashMap<>());
        permissaoDeUsuario = new PermissaoDeUsuario();
    }

    public TipoSexo[] tipoSexo() {
        return TipoSexo.values();
    }

    public TipoUsuario[] tipoUsuario() {
        return TipoUsuario.values();
    }

    private void onCarregarPerfisEArvore() {
        if (perfisCadastrados == null) {
            perfisCadastrados = perfilService.findAll();
        }

        root = PermissoesUtil.getTreeNodeMenus(permissaoDeUsuario.getPermissoes());

    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.USUARIOS) //analisar permissões antes
    public void onNovo() {
        usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.USUARIO_FUNCIONARIO);
        onCarregarPerfisEArvore();
        FacesUtil.mostraDialog(DIIALOG_CADASTRO, true);
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.USUARIOS) //analisar permissões antes
    public void onEditar() {
        usuario = usuarioService.findOne(usuario.getIdPessoa());

        permissaoDeUsuario = usuario.getPermissaoDeUsuario();

        //analisar permissões antes
        onCarregarPerfisEArvore();

        FacesUtil.mostraDialog(DIIALOG_CADASTRO, true);
    }

    public void upload(FileUploadEvent event) {

        byte[] foto = event.getFile().getContents();

        usuario.setFoto(foto);
        usuario.setPath();

    }

    public void onSalvar() {

        boolean editar = usuario.getIdPessoa() != null;

        try {
            usuario.setPermissaoDeUsuario(permissaoDeUsuario);
            usuarioService.onSalvar(usuario);
            sessaoMB.setUsuarioLogado(usuario);

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);
            FacesUtil.mostraDialog(DIIALOG_CADASTRO, false);
        } catch (Exception e) {
            e.printStackTrace();
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.USUARIOS) //analisar permissões antes
    public void onExcluir() {
        try {
            usuarioService.onExcluir(usuario);

            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            e.printStackTrace();
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public void onSelecionarPerfil() {

        if (perfilSelecionado == null || perfilSelecionado.getId() == null) {
            nosSelecionados = null;
            root = PermissoesUtil.getTreeNodeMenus(null);
            //setArvoreMenus(null);        
            return;
        }

        perfilSelecionado = perfilService.findOne(perfilSelecionado.getId());
        perfilSelecionado.getPermissoes().size();
        //copia as permissões predefinidas no perfil selecionado para obj permissaoDeUsuario
        permissaoDeUsuario = new PermissaoDeUsuario(perfilSelecionado);
        root = PermissoesUtil.getTreeNodeMenus(permissaoDeUsuario.getPermissoes());

    }

    public void onNodeSelect(NodeSelectEvent evt) {

        TreeNode node = evt.getTreeNode();
        if (permissaoDeUsuario == null) {
            permissaoDeUsuario = new PermissaoDeUsuario();
        }
        adicionarNaPermissao(node, permissaoDeUsuario);
    }

    public void onNodeUnselect(NodeUnselectEvent evt) {

        TreeNode node = evt.getTreeNode();
        if (permissaoDeUsuario == null) {
            permissaoDeUsuario = new PermissaoDeUsuario();
        }
        removerDoPerfil(node, permissaoDeUsuario);
    }

    private void adicionarNaPermissao(TreeNode noSelecionado, PermissaoDeUsuario permissaoDeUsuario) {

        if (noSelecionado.getType().equals(MENU_NODE)) {
            //varre os filhos a adiciona ao perfil todas as ações dos menus itens dentro do no de menu selecionado
            noSelecionado.getChildren().stream()
                    .forEach(treeNode -> adicionarNaPermissao(treeNode, permissaoDeUsuario));

        } else if (noSelecionado.getType().equals(ITEM_NODE)) {
            //area do sistema representada pelo no selecionado (será KEY no Map <AreaDoSistema, AcaoDoUsuario>)
            AreaDoSistema area = (AreaDoSistema) noSelecionado.getData();
            List<Acao> acoesPossiveis = new ArrayList<>();
            //os filhos de ITEM_NODE são todos do tipo ACAO_NODE
            for (TreeNode noAcao : noSelecionado.getChildren()) {
                //adiciona todas as ações 
                Acao acao = (Acao) noAcao.getData();
                acoesPossiveis.add(acao);
            }
            //cria o objeto acaoDoUsuario passando as ações possíveis no contrutor
            AcaoDoUsuario acaoDoUsuario = new AcaoDoUsuario(acoesPossiveis);
            permissaoDeUsuario.getPermissoes().put(area, acaoDoUsuario);
        } else {   //tipo ACAO_NODE
            //a área do sistema é identificada pelo menu item (parent do no de ação sekecionado)
            AreaDoSistema areaDoSistema = (AreaDoSistema) noSelecionado.getParent().getData();
            //pega a ação que o nó selecionado representa
            Acao acao = (Acao) noSelecionado.getData();
            //se essa área já estiver definida no objeto perfil, adiciona mais essa ação na área
            if (permissaoDeUsuario.getPermissoes().containsKey(areaDoSistema)) {
                permissaoDeUsuario.getPermissoes().get(areaDoSistema).getAcoes().add(acao);
            } //o perfil não tinha acesso a essa área, então adiciona a área no perfil e com acesso a respectiva ação do nó selecionado
            else {
                AcaoDoUsuario acaoDoUsuario = new AcaoDoUsuario();
                acaoDoUsuario.getAcoes().add(acao);
                permissaoDeUsuario.getPermissoes().put(areaDoSistema, acaoDoUsuario);
            }
        }
    }

    private void removerDoPerfil(TreeNode noDeselecionado, PermissaoDeUsuario permissaoDeUsuario) {

        if (noDeselecionado.getChildCount() > 0) {

            for (TreeNode treeNode : noDeselecionado.getChildren()) {

                removerDoPerfil(treeNode, permissaoDeUsuario);
            }
        } else if (noDeselecionado.getType().equals(ITEM_NODE)) {

            AreaDoSistema area = (AreaDoSistema) noDeselecionado.getData();
            permissaoDeUsuario.getPermissoes().remove(area);
        } else {   //ACAO_NODE
            //a área do sistema é identificada pelo menu item (parent do no de ação deselecionado)
            AreaDoSistema areaDoSistema = (AreaDoSistema) noDeselecionado.getParent().getData();
            //pega a ação que o nó deselecionado representa
            Acao acao = (Acao) noDeselecionado.getData();
            AcaoDoUsuario acaoDoUsuario = permissaoDeUsuario.getPermissoes().get(areaDoSistema);
            //remove a ação da área do sistema
            acaoDoUsuario.getAcoes().remove(acao);
            //se for a única ação permitida nessa área, então remove a própria área das permissões, porque não haverá mais nenhuma ação
            if (acaoDoUsuario.getAcoes().isEmpty()) {
                permissaoDeUsuario.getPermissoes().remove(areaDoSistema);
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean ativaBotoes() {
        return usuario != null && usuario.getIdPessoa() != null;
    }

    public LazyTableGenerico<Usuario> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<Usuario> tabela) {
        this.tabela = tabela;
    }

    public PerfilDeAcesso getPerfilSelecionado() {
        return perfilSelecionado;
    }

    public void setPerfilSelecionado(PerfilDeAcesso perfilSelecionado) {
        this.perfilSelecionado = perfilSelecionado;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode[] getNosSelecionados() {
        return nosSelecionados;
    }

    public void setNosSelecionados(TreeNode[] nosSelecionados) {
        this.nosSelecionados = nosSelecionados;
    }

    public List<PerfilDeAcesso> getPerfisCadastrados() {
        return perfisCadastrados;
    }

    public void setPerfisCadastrados(List<PerfilDeAcesso> perfisCadastrados) {
        this.perfisCadastrados = perfisCadastrados;
    }

    public PermissaoDeUsuario getPermissaoDeUsuario() {
        return permissaoDeUsuario;
    }

    public void setPermissaoDeUsuario(PermissaoDeUsuario permissaoDeUsuario) {
        this.permissaoDeUsuario = permissaoDeUsuario;
    }

}
