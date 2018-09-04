/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.BoletoLocal;
import br.com.galeria.entidades.ConfiguracoesBoleto;
import br.com.galeria.entidades.Contrato;
import br.com.galeria.interceptor.ChecarPermissao;
import br.com.galeria.services.ServiceBoleto;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.table.LazyTableGenerico;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

/**
 *
 * @author Renan
 */
@Named
@javax.faces.view.ViewScoped
public class BoletoMB implements Serializable {

    @EJB(beanName = "BoletoServiceImpl")
    private ServiceBoleto boletoService;

    @EJB(beanName = "ContratoServiceImpl")
    private ServiceGenerico<Contrato> contratoService;

    @EJB(beanName = "ConfiguracaoBoletoServiceImpl")
    private ServiceGenerico<ConfiguracoesBoleto> configBoletoService;

    private LazyTableGenerico<BoletoLocal> tabela;

    private BoletoLocal boleto;

    private final String DIALOG_VAR = "cadastroBoleto";
    private final String DIALOG_SELECIONA_CONTRATO = "selecionaContrato";
    private final String DIALOG_SELECIONA_LOCATARIO = "incluirLocatario";
    private final String AREA = "Configuração de Boleto";

    private Date dataVisualizacao;

    @PostConstruct
    private void init() {
        dataVisualizacao = new Date();
        boleto = new BoletoLocal();
        boleto.setContrato(new Contrato());
        tabela = new LazyTableGenerico<>(boletoService);
        atualizaFiltro();
    }

    public void atualizaFiltro() {
        tabela.removerFiltro("dataVencimento");

        Calendar inicioMes = Calendar.getInstance();
        inicioMes.setTime(dataVisualizacao);
        inicioMes.set(Calendar.DAY_OF_MONTH, 1);

        Calendar finalMes = Calendar.getInstance();
        finalMes.setTime(dataVisualizacao);
        finalMes.set(Calendar.DAY_OF_MONTH, finalMes.getMaximum(Calendar.DAY_OF_MONTH));

        Date[] filtroDatas = new Date[2];
        filtroDatas[0] = inicioMes.getTime();
        filtroDatas[1] = finalMes.getTime();

        tabela.adicionarFiltro("dataVencimento", filtroDatas);
    }

    public void onFiltrarTabela(int filtro) {
        // 1 = Abertos
        // 2 = Vencidos
        // 3 = Pagos
        tabela.limparFiltros();
        switch (filtro) {
            case 1: {
                tabela.adicionarFiltro("pago", false);
                Date[] datas = new Date[2];
                Calendar dataAtual = Calendar.getInstance();
                datas[0] = dataAtual.getTime();
                dataAtual.add(Calendar.MONTH, 1);
                datas[1] = dataAtual.getTime();
                tabela.adicionarFiltro("dataVencimento", datas);
                break;
            }
            case 2: {
                tabela.adicionarFiltro("pago", false);
                Date[] datas = new Date[2];
                Calendar dataAtual = Calendar.getInstance();
                datas[1] = dataAtual.getTime();
                dataAtual.add(Calendar.YEAR, -5);
                datas[0] = dataAtual.getTime();
                tabela.adicionarFiltro("dataVencimento", datas);
                break;
            }
            default:
                tabela.adicionarFiltro("pago", true);
                break;
        }

    }

    public void onSelecionarLocatario() {
        if (boleto.getLocatario() == null || boleto.getLocatario().getId() == null) {
            FacesMensagensUtil.adcionarMensagemErro("Selecione o locatário");
            return;
        }

        if (boleto.getLocatario().getContratos() == null || boleto.getLocatario().getContratos().isEmpty()) {
            FacesMensagensUtil.adcionarMensagemErro("O locatário selecionado não possui um contrato");
            return;
        }

        FacesUtil.mostraDialog(DIALOG_SELECIONA_CONTRATO, true);
    }

    public void onSelecionarContrato() {
        FacesUtil.mostraDialog(DIALOG_SELECIONA_CONTRATO, false);
        FacesUtil.mostraDialog(DIALOG_SELECIONA_LOCATARIO, false);
    }

    public void onGerarBoleto() {

        ConfiguracoesBoleto config = getConfig();
        if (config == null) {
            return;
        }
        //  boletoService.onGerarBoleto(boleto, config);
        //abrir em nova aba o boleto
    }

    @ChecarPermissao(acao = Acao.SALVAR, area = AreaDoSistema.BOLETOS) //analisar permissões antes
    public void onNovo() {
        //analisar permissões antes
        boleto = new BoletoLocal();
        boleto.setContrato(new Contrato());
        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    private ConfiguracoesBoleto getConfig() {
        List<ConfiguracoesBoleto> configs = configBoletoService.findAll();
        if (configs == null || configs.isEmpty()) {
            FacesMensagensUtil.adcionarMensagemErro("Para gerar boletos é necessário que as configurações de banco estejam completas");
            return null;
        }

        return configs.get(0);
    }

    public void onGerarBoletos() {

        boletoService.onGerarBoletosAutomatico();

        FacesUtil.atualiza("formBoletos");

    }

    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.BOLETOS) //analisar permissões antes
    public void onEditar() {
        //analisar permissões antes

        FacesUtil.mostraDialog(DIALOG_VAR, true);
    }

    public void onSalvar() {
        boolean editar = boleto.getId() != null;
        try {
            boletoService.onSalvar(boleto);
            FacesUtil.mostraDialog(DIALOG_VAR, false); //fechando dialog apos salvar

            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @ChecarPermissao(acao = Acao.EXCLUIR, area = AreaDoSistema.BOLETOS) //analisar permissões antes
    public void onExcluir() {

        //analisar permissoes antes
        try {
            boletoService.onExcluir(boleto);
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public LazyTableGenerico<BoletoLocal> getTabela() {
        return tabela;
    }

    public void setTabela(LazyTableGenerico<BoletoLocal> tabela) {
        this.tabela = tabela;
    }

    public BoletoLocal getBoleto() {
        return boleto;
    }

    public void setBoleto(BoletoLocal boleto) {
        this.boleto = boleto;
    }

    public Date getDataVisualizacao() {
        return dataVisualizacao;
    }

    public void setDataVisualizacao(Date dataVisualizacao) {
        this.dataVisualizacao = dataVisualizacao;
    }

}
