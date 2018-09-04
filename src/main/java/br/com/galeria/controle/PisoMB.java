/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.Piso;
import br.com.galeria.interceptor.ChecarPermissao;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.table.LazyTableGenerico;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

/**
 *
 * @author Renan
 */
@Named
@javax.faces.view.ViewScoped
public class PisoMB implements Serializable {
 

    @EJB(beanName = "PisoServiceImpl")
    private ServiceGenerico<Piso> pisoService;

    private LazyTableGenerico<Piso> tabela;

    private final String DIALOG_VAR = "cadastroPiso";
    private final String AREA = "Piso";

    private Piso piso;

    @PostConstruct
    private void init() {

        piso = new Piso();
        tabela = new LazyTableGenerico<>(pisoService);
    }

    public String getValor() {
        return "";
    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.PISOS) //analisar permissões antes
    public void onNovo() {

        piso = new Piso();
        FacesUtil.mostraDialog(DIALOG_VAR, true);
        //preciso atualizar por aqui para evitar bug na view
        FacesUtil.atualiza("formCadastroPiso");
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.PISOS) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes

        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    public void onSalvar() {
        boolean editar = piso.getId() != null;
        try {
            pisoService.onSalvar(piso);
            FacesUtil.mostraDialog(DIALOG_VAR, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.PISOS) //analisar permissões antes
    public void onExcluir() {

        //analisar permissoes antes
        try {
            pisoService.onExcluir(piso);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public LazyTableGenerico<Piso> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<Piso> tabela) {
        this.tabela = tabela;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

}
