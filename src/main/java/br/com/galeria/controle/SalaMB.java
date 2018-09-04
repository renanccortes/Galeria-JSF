/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

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
import java.util.ArrayList;
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
public class SalaMB implements Serializable {

    @EJB(beanName = "SalaServiceImpl")
    private ServiceGenerico<Sala> salaService;

    @EJB(beanName = "PisoServiceImpl")
    private ServiceGenerico<Piso> pisoService;

    private LazyTableGenerico<Sala> tabela;

    private final String DIALOG_VAR = "cadastroSala";
    private final String AREA = "Sala";

    private List<Piso> pisosCadastrados;

    private Sala sala;

    @PostConstruct
    private void init() {
        sala = new Sala();
        pisosCadastrados = new ArrayList<>();
        tabela = new LazyTableGenerico<>(salaService);
        pisosCadastrados = pisoService.findAll();
    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.SALAS) //analisar permissões antes
    public void onNovo() {
        //analisar permissões antes
        sala = new Sala();
        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.SALAS) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes

        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    public void onSalvar() {
        boolean editar = sala.getId() != null;
        try {
            salaService.onSalvar(sala);
            FacesUtil.mostraDialog(DIALOG_VAR, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.SALAS) //analisar permissões antes
    public void onExcluir() {

        //analisar permissoes antes
        try {
            salaService.onExcluir(sala);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public LazyTableGenerico<Sala> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<Sala> tabela) {
        this.tabela = tabela;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Piso> getPisosCadastrados() {
        return pisosCadastrados;
    }

    public void setPisosCadastrados(List<Piso> pisosCadastrados) {
        this.pisosCadastrados = pisosCadastrados;
    }

}
