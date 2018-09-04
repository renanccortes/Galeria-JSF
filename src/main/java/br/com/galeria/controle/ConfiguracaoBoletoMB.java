/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.Banco;
import br.com.galeria.entidades.ConfiguracoesBoleto;
import br.com.galeria.interceptor.ChecarPermissao;
import br.com.galeria.services.ServiceGenerico;
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
public class ConfiguracaoBoletoMB implements Serializable {

    @EJB(beanName = "ConfiguracaoBoletoServiceImpl")
    private ServiceGenerico<ConfiguracoesBoleto> configBoletoService;

    @EJB(beanName = "BancoServiceImpl")
    private ServiceGenerico<Banco> bancoService;

    private List<ConfiguracoesBoleto> tabela;

    private ConfiguracoesBoleto config;

    private List<Banco> bancos;

    private final String DIALOG_VAR = "cadastroConfig";
    private final String AREA = "Configuração de Boleto";

    @PostConstruct
    private void init() {
        bancos = new ArrayList<>();
        config = new ConfiguracoesBoleto();
        tabela = configBoletoService.findAll();
        if (tabela == null || tabela.isEmpty()) {
            configBoletoService.onSalvar(config);
            tabela = configBoletoService.findAll();
        }

        config = tabela.get(0);
    }

    private void carregarBancos() {
        bancos = bancoService.findAll();
    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.CONFIGURACOES) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes
        carregarBancos();
        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    public void onSalvar() {
        boolean editar = config.getId() != null;
        try {
            configBoletoService.onSalvar(config);
            FacesUtil.mostraDialog(DIALOG_VAR, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.CONFIGURACOES) //analisar permissões antes
    public void onExcluir() {

        //analisar permissoes antes
        try {
            configBoletoService.onExcluir(config);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public List<ConfiguracoesBoleto> getTabela() {
        return tabela;
    }

    public void setTabela(List<ConfiguracoesBoleto> tabela) {
        this.tabela = tabela;
    }

    public ConfiguracoesBoleto getConfig() {
        return config;
    }

    public void setConfig(ConfiguracoesBoleto config) {
        this.config = config;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

}
