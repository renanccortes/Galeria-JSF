/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.util;

import br.com.galeria.entidades.AcaoDoUsuario;
import br.com.galeria.tipos.Acao;
import static br.com.galeria.tipos.Acao.EDITAR;
import static br.com.galeria.tipos.Acao.EXCLUIR;
import static br.com.galeria.tipos.Acao.SALVAR;
import static br.com.galeria.tipos.Acao.VISUALIZAR;
import br.com.galeria.tipos.AreaDoSistema;
import static br.com.galeria.tipos.AreaDoSistema.BANCOS;
import static br.com.galeria.tipos.AreaDoSistema.BOLETOS;
import static br.com.galeria.tipos.AreaDoSistema.CADASTROS_GERAIS;
import static br.com.galeria.tipos.AreaDoSistema.CONFIGURACOES;
import static br.com.galeria.tipos.AreaDoSistema.CONTRATOS;
import static br.com.galeria.tipos.AreaDoSistema.LOCATARIOS;
import static br.com.galeria.tipos.AreaDoSistema.PERFIS;
import static br.com.galeria.tipos.AreaDoSistema.PISOS;
import static br.com.galeria.tipos.AreaDoSistema.RELATORIOS;
import static br.com.galeria.tipos.AreaDoSistema.SALAS;
import static br.com.galeria.tipos.AreaDoSistema.USUARIOS;
import java.util.Map;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Renan
 */
public class PermissoesUtil {

    public static final String MENU_NODE = "menu";
    public static final String ITEM_NODE = "item";
    public static final String ACAO_NODE = "acao";

    public static TreeNode getTreeNodeMenus(Map<AreaDoSistema, AcaoDoUsuario> permissoesPreSelecionadas) {

        //tipos de nos: menu, item
        TreeNode root = new CheckboxTreeNode();

        TreeNode cadastrosGerais = new CheckboxTreeNode(MENU_NODE, CADASTROS_GERAIS, root);

        TreeNode contratos = new CheckboxTreeNode(ITEM_NODE, CONTRATOS, cadastrosGerais);
        adicionarItensNoDeAcao(contratos, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode boletos = new CheckboxTreeNode(ITEM_NODE, BOLETOS, cadastrosGerais);
        adicionarItensNoDeAcao(boletos, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode relatorios = new CheckboxTreeNode(ITEM_NODE, RELATORIOS, cadastrosGerais);
        adicionarItensNoDeAcao(relatorios, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode pisos = new CheckboxTreeNode(ITEM_NODE, PISOS, cadastrosGerais);
        adicionarItensNoDeAcao(pisos, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode salas = new CheckboxTreeNode(ITEM_NODE, SALAS, cadastrosGerais);
        adicionarItensNoDeAcao(salas, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode locatarios = new CheckboxTreeNode(ITEM_NODE, LOCATARIOS, cadastrosGerais);
        adicionarItensNoDeAcao(locatarios, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode perfis = new CheckboxTreeNode(ITEM_NODE, PERFIS, cadastrosGerais);
        adicionarItensNoDeAcao(perfis, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode bancos = new CheckboxTreeNode(ITEM_NODE, BANCOS, cadastrosGerais);
        adicionarItensNoDeAcao(bancos, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode usuarios = new CheckboxTreeNode(ITEM_NODE, USUARIOS, cadastrosGerais);
        adicionarItensNoDeAcao(usuarios, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        TreeNode config = new CheckboxTreeNode(ITEM_NODE, CONFIGURACOES, cadastrosGerais);
        adicionarItensNoDeAcao(config, permissoesPreSelecionadas, SALVAR, EDITAR, EXCLUIR, VISUALIZAR);

        return root;
    }

    //o nó que representa uma ação possível naquela área do sistema
    private static void adicionarItensNoDeAcao(TreeNode noMenuItem,
            Map<AreaDoSistema, AcaoDoUsuario> permissoesPreSelecionadas,
            Acao... acoesDoUsuario) {

        //somente nodes tipo 'ITEM_NODE' podem ter nodes tipo 'ACAO_NODE'
        if (!noMenuItem.getType().equals(ITEM_NODE)) {
            throw new IllegalArgumentException("Tree Node deve ser do tipo ITEM_NODE");
        }

        //para cada tipo de ação, cria um node filho dentro do item
        for (Acao acao : acoesDoUsuario) {
            //check node que representa uma ação, se estiver marcado, significa que o perfil tem essa autorização
            CheckboxTreeNode nodeAcao = new CheckboxTreeNode(ACAO_NODE, acao, noMenuItem);
            //o param 'permissoesPreSelecionadas' é para a tree no modo edição, 
            if (permissoesPreSelecionadas != null) {
         
                //pega a área do node pai
                AreaDoSistema area = (AreaDoSistema) noMenuItem.getData();
                //ve as ações já autorizadas naquela área
                AcaoDoUsuario acoes = permissoesPreSelecionadas.get(area);
                //se o perfil tiver autorização para a acao atual no loop for, já marca o checkbox tree node
                if (acoes != null && acoes.getAcoes().contains(acao)) {
                    nodeAcao.setSelected(true);
                }
            }
        }
    }

}
