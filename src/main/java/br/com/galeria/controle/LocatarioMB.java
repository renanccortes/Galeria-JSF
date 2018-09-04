/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.Locatario;
import br.com.galeria.entidades.Sala;
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
public class LocatarioMB implements Serializable {

    @EJB(beanName = "LocatarioServiceImpl")
    private ServiceGenerico<Locatario> locatarioService;

    @EJB(beanName = "SalaServiceImpl")
    private ServiceGenerico<Sala> salaService;

    private LazyTableGenerico<Locatario> tabela;

    private final String DIALOG_VAR = "cadastroLocatario";
    private final String AREA = "Locatário";

    private Locatario locatario;

    @PostConstruct
    private void init() {
        locatario = new Locatario();
        tabela = new LazyTableGenerico<>(locatarioService);

    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.LOCATARIOS) //analisar permissões antes
    public void onNovo() {
        //analisar permissões antes
        locatario = new Locatario();
        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.LOCATARIOS) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes

        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    public void onSalvar() {
        boolean editar = locatario.getId() != null;
        try {
            locatarioService.onSalvar(locatario);
            FacesUtil.mostraDialog(DIALOG_VAR, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }
     //analisar permissoes antes
    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.LOCATARIOS) //analisar permissões antes
    public void onExcluir() {

       
        try {
            locatarioService.onExcluir(locatario);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public LazyTableGenerico<Locatario> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<Locatario> tabela) {
        this.tabela = tabela;
    }

    public Locatario getLocatario() {
        return locatario;
    }

    public void setLocatario(Locatario locatario) {
        this.locatario = locatario;
    }

}
