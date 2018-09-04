/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.Banco;
import br.com.galeria.entidades.Piso;
import br.com.galeria.interceptor.ChecarPermissao;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.table.LazyTableGenerico;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.tipos.BancosEnum;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Renan
 */
@Named
@javax.faces.view.ViewScoped
public class BancoMB implements Serializable {

    @EJB(beanName = "BancoServiceImpl")
    private ServiceGenerico<Banco> bancoService;

    private LazyTableGenerico<Banco> tabela;

    private final String DIALOG_VAR = "cadastroBanco";
    private final String AREA = "Banco";

    private Banco banco;

    public BancoMB() {
    }

    @PostConstruct
    private void init() {

        banco = new Banco();
        tabela = new LazyTableGenerico<>(bancoService);
    }

    public BancosEnum[] getBancosEnum() {
        return BancosEnum.values();
    }

    public String getValor() {
        return "";
    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.BANCOS) //analisar permissões antes
    public void onNovo() {

        banco = new Banco();

        FacesUtil.mostraDialog(DIALOG_VAR, true);
        //preciso atualizar por aqui para evitar bug na view
        FacesUtil.atualiza("formCadastroBanco");
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.BANCOS) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes

        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    public void onSalvar() {
        boolean editar = banco.getId() != null;
        try {
            bancoService.onSalvar(banco);
            FacesUtil.mostraDialog(DIALOG_VAR, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.BANCOS) //analisar permissões antes
    public void onExcluir() {

        //analisar permissoes antes
        try {
            bancoService.onExcluir(banco);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public LazyTableGenerico<Banco> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<Banco> tabela) {
        this.tabela = tabela;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

}
