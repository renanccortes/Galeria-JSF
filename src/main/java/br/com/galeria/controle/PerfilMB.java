/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.AcaoDoUsuario;
import br.com.galeria.entidades.PerfilDeAcesso;
import br.com.galeria.interceptor.ChecarPermissao;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.table.LazyTableGenerico;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.tipos.TipoUsuario;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import br.com.galeria.util.PermissoesUtil;
import static br.com.galeria.util.PermissoesUtil.ITEM_NODE;
import static br.com.galeria.util.PermissoesUtil.MENU_NODE;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Renan
 */
@ViewScoped
@Named
public class PerfilMB implements Serializable {

    @Inject
    private LogonMB sessaoMB;

    @EJB(beanName = "PerfilServiceImpl")
    private ServiceGenerico<PerfilDeAcesso> perfilService;

    private PerfilDeAcesso perfilDeAcesso;

    private LazyTableGenerico<PerfilDeAcesso> tabela;

    private final String DIALOG_CADASTRO = "cadastroPerfil";
    private final String AREA = "Perfil";

    private boolean desabilitarCampos = false;

    private TreeNode root;
    private TreeNode[] nosSelecionados;

    @PostConstruct
    public void init() {

        tabela = new LazyTableGenerico<>(perfilService);
    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.PERFIS) //analisar permissões antes
    public void onNovo() {
        perfilDeAcesso = new PerfilDeAcesso();
        perfilDeAcesso.setTipoUsuario(TipoUsuario.USUARIO_FUNCIONARIO);
        root = PermissoesUtil.getTreeNodeMenus(null);
        FacesUtil.mostraDialog(DIALOG_CADASTRO, true);
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.PERFIS) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes 
        if (perfilDeAcesso == null) {
            FacesMensagensUtil.adcionarMensagemErro("Nenhum Perfil foi selecionado");
            return;
        }
        perfilDeAcesso = perfilService.findOne(perfilDeAcesso.getId());
        perfilDeAcesso.getPermissoes().size();
        perfilDeAcesso.setModoEdicao(true);
        //passa as permissões como parâmetro para que os respectivos itens já venham selecionados na árvore (já que é modo edição)
        //setArvoreMenus(perfilDeAcesso.getPermissoes());
        root = PermissoesUtil.getTreeNodeMenus(perfilDeAcesso.getPermissoes());

        FacesUtil.mostraDialog(DIALOG_CADASTRO, true);
    }

    public void onSalvar() {
        boolean editar = perfilDeAcesso.getId() != null;
        try {
            if (!perfilDeAcesso.isModoEdicao()) {
                for (TreeNode noSelecionado : nosSelecionados) {
                    adicionarAoPerfil(noSelecionado, perfilDeAcesso);
                }
            }

            perfilService.onSalvar(perfilDeAcesso);

            FacesUtil.mostraDialog(DIALOG_CADASTRO, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.PERFIS) //analisar permissões antes
    public void onExcluir() {

        //analisar permissoes antes
        try {
            perfilService.onExcluir(perfilDeAcesso);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public boolean ativaBotoes() {

        return false;
    }

    public String onVisualizar() {

        return null;
    }

    public void onCarregarPerfil() {

    }

    public void onFecharDialogCadPerfil(CloseEvent evt) {

        perfilDeAcesso = null;
    }

    public void onNodeSelect(NodeSelectEvent evt) {

        if (!perfilDeAcesso.isModoEdicao()) //id = null    
        {
            return;
        }

        TreeNode node = evt.getTreeNode();
        adicionarAoPerfil(node, perfilDeAcesso);
    }

    public void onNodeUnselect(NodeUnselectEvent evt) {

        if (!perfilDeAcesso.isModoEdicao()) {
            return;
        }

        TreeNode node = evt.getTreeNode();
        removerDoPerfil(node, perfilDeAcesso);
    }

    private void adicionarAoPerfil(TreeNode noSelecionado, PerfilDeAcesso perfilDeAcesso) {

        if (noSelecionado.getType().equals(MENU_NODE)) {
            //varre os filhos a adiciona ao perfil todas as ações dos menus itens dentro do no de menu selecionado
            for (TreeNode treeNode : noSelecionado.getChildren()) {

                adicionarAoPerfil(treeNode, perfilDeAcesso);
            }
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
            perfilDeAcesso.getPermissoes().put(area, acaoDoUsuario);
        } else {   //tipo ACAO_NODE
            //a área do sistema é identificada pelo menu item (parent do no de ação sekecionado)
           
            AreaDoSistema areaDoSistema = (AreaDoSistema) noSelecionado.getParent().getData();
            //pega a ação que o nó selecionado representa
            Acao acao = (Acao) noSelecionado.getData();
            //se essa área já estiver definida no objeto perfil, adiciona mais essa ação na área
            if (perfilDeAcesso.getPermissoes().containsKey(areaDoSistema)) {
                perfilDeAcesso.getPermissoes().get(areaDoSistema).getAcoes().add(acao);
            } //o perfil não tinha acesso a essa área, então adiciona a área no perfil e com acesso a respectiva ação do nó selecionado
            else {
                AcaoDoUsuario acaoDoUsuario = new AcaoDoUsuario();
                acaoDoUsuario.getAcoes().add(acao);
                perfilDeAcesso.getPermissoes().put(areaDoSistema, acaoDoUsuario);
            }
        }
    }

    private void removerDoPerfil(TreeNode noDeselecionado, PerfilDeAcesso perfilDeAcesso) {

        if (noDeselecionado.getChildCount() > 0) {

            for (TreeNode treeNode : noDeselecionado.getChildren()) {

                removerDoPerfil(treeNode, perfilDeAcesso);
            }
        } else if (noDeselecionado.getType().equals(ITEM_NODE)) {

            AreaDoSistema area = (AreaDoSistema) noDeselecionado.getData();
            perfilDeAcesso.getPermissoes().remove(area);
        } else {   //ACAO_NODE
            //a área do sistema é identificada pelo menu item (parent do no de ação deselecionado)
            
            AreaDoSistema areaDoSistema = (AreaDoSistema) noDeselecionado.getParent().getData();
            //pega a ação que o nó deselecionado representa
            Acao acao = (Acao) noDeselecionado.getData();
            AcaoDoUsuario acaoDoUsuario = perfilDeAcesso.getPermissoes().get(areaDoSistema);
            //remove a ação da área do sistema
            acaoDoUsuario.getAcoes().remove(acao);
            //se for a única ação permitida nessa área, então remove a própria área das permissões, porque não haverá mais nenhuma ação
            if (acaoDoUsuario.getAcoes().isEmpty()) {
                perfilDeAcesso.getPermissoes().remove(areaDoSistema);
            }
        }
    }

    //----------------- getters & setters
    public TreeNode getRoot() {
        return root;
    }

    public TreeNode[] getNosSelecionados() {
        return nosSelecionados;
    }

    public void setNosSelecionados(TreeNode[] nosSelecionados) {
        this.nosSelecionados = nosSelecionados;
    }

    public boolean isDesabilitarCampos() {
        return desabilitarCampos;
    }

    public void setDesabilitarCampos(boolean desabilitarCampos) {
        this.desabilitarCampos = desabilitarCampos;
    }

    public PerfilDeAcesso getPerfilDeAcesso() {
        return perfilDeAcesso;
    }

    public void setPerfilDeAcesso(PerfilDeAcesso perfilDeAcesso) {
        this.perfilDeAcesso = perfilDeAcesso;
    }

    public LazyTableGenerico<PerfilDeAcesso> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<PerfilDeAcesso> tabela) {
        this.tabela = tabela;
    }

}

abstract class ComponenteMenu {

    AreaDoSistema areaDoSistema;
    Acao acao;
    String nome;

    public AreaDoSistema getAreaDoSistema() {
        return areaDoSistema;
    }

    public void setAreaDoSistema(AreaDoSistema areaDoSistema) {
        this.areaDoSistema = areaDoSistema;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

class Menu extends ComponenteMenu {

    List<ComponenteMenu> submenus;

    public Menu() {
    }

    public List<ComponenteMenu> getSubmenus() {
        return submenus;
    }

    public void setSubmenus(List<ComponenteMenu> submenus) {
        this.submenus = submenus;
    }
}

class MenuItem extends ComponenteMenu {

}
