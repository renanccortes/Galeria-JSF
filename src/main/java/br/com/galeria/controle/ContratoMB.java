/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.Contrato;
import br.com.galeria.entidades.Piso;
import br.com.galeria.entidades.Sala;
import br.com.galeria.interceptor.ChecarPermissao;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.table.LazyTableGenerico;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

/**
 *
 * @author Renan
 */
@Named
@javax.faces.view.ViewScoped
public class ContratoMB implements Serializable {

    @EJB(beanName = "ContratoServiceImpl")
    private ServiceGenerico<Contrato> contratoService;

    @EJB(beanName = "PisoServiceImpl")
    private ServiceGenerico<Piso> pisoService;

    private LazyTableGenerico<Contrato> tabela;

    private final String DIALOG_VAR = "cadastroContrato";
    private final String AREA = "Contrato";

    private List<Piso> pisosCadastrados;

    private Contrato contrato;

    @PostConstruct
    private void init() {
        contrato = new Contrato();
        tabela = new LazyTableGenerico<>(contratoService);
        pisosCadastrados = pisoService.findAll();
    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.CONTRATOS) //analisar permissões antes
    public void onNovo() {
        //analisar permissões antes
        contrato = new Contrato();
        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.CONTRATOS) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes

        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    public void onSalvar() {
        boolean editar = contrato.getId() != null;
        try {
            contratoService.onSalvar(contrato);
            FacesUtil.mostraDialog(DIALOG_VAR, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.CONTRATOS) //analisar permissões antes
    public void onExcluir() {

        //analisar permissoes antes
        try {
            contratoService.onExcluir(contrato);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public LazyTableGenerico<Contrato> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<Contrato> tabela) {
        this.tabela = tabela;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Piso> getPisosCadastrados() {
        return pisosCadastrados;
    }

    public void setPisosCadastrados(List<Piso> pisosCadastrados) {
        this.pisosCadastrados = pisosCadastrados;
    }

}
