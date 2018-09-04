/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services.impl;

import br.com.galeria.dao.AbstractDao;
import br.com.galeria.entidades.BoletoLocal;
import br.com.galeria.entidades.ConfiguracoesBoleto;
import br.com.galeria.entidades.Contrato;
import br.com.galeria.entidades.Usuario;
import br.com.galeria.services.ServiceBoleto;
import br.com.galeria.services.ServiceGenerico;
import br.com.galeria.tipos.BancosEnum;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

/**
 *
 * @author Renan
 */
@Stateless
@EJB(beanName = "BoletoServiceImpl", name = "BoletoServiceImpl", beanInterface = ServiceBoleto.class)
public class BoletoServiceImpl extends AbstractDao<BoletoLocal> implements ServiceBoleto {

    @PersistenceContext(name = "PU")
    private EntityManager em;

    @EJB(beanName = "ContratoServiceImpl")
    private ServiceGenerico<Contrato> contratoService;

    @EJB(beanName = "ConfiguracaoBoletoServiceImpl")
    private ServiceGenerico<ConfiguracoesBoleto> configBoletoService;

    public BoletoServiceImpl() {
        super(BoletoLocal.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public BoletoLocal onSalvar(BoletoLocal boleto, Usuario usuarioLogado) {
        if (boleto.getId() == null) {
            boleto.setCriado(new Date());
            boleto = this.save(boleto);
        } else {
            boleto.setAtualizado(new Date());
            this.update(boleto);
        }

        return boleto;
    }

    @Override
    public BoletoLocal onGerarBoleto(Contrato contrato, ConfiguracoesBoleto config) {

        /*
		 * I VALIDANDO A DATA PARA GERAR CONFORME CONFIGURADO
         */
        int diaVencimento = contrato.getDiaVencimento();
        Calendar dataHj = Calendar.getInstance();

        //SE O DIA DO VENCIMENTO DO BOLETO FOR MENOR OU IGUAL
        // A DATA DE HJ GERA NORMAL, SE NÃO ELE GERA PRO PROXIMO MES
        if (dataHj.get(Calendar.DAY_OF_MONTH) <= diaVencimento) {

        } else {
            dataHj.add(Calendar.MONTH, 1);
        }

        dataHj.set(Calendar.DAY_OF_MONTH, diaVencimento);
        Calendar dataMaxima = Calendar.getInstance();
        dataMaxima.add(Calendar.DAY_OF_MONTH, 20);

        //SE A DATA FOR MAIOS QUE O MÁXIMO CONFIGURADO NÃO GERA NADA.
        if (dataHj.after(dataMaxima)) {
            System.out.println("data depois de 20 dias");
            return null;
        }


        /*
		 * INFORMANDO DADOS SOBRE O CEDENTE.
         */
        Cedente cedente = new Cedente(config.getBanco().getNomeBeneficiado(), config.getDocBeneficiado());

        /*
		 * INFORMANDO DADOS SOBRE O SACADO.
         */
        Sacado sacado = new Sacado(contrato.getLocatario().getNome(), contrato.getLocatario().getDocumento());

        // Informando o endereço do sacado.
        org.jrimum.domkee.comum.pessoa.endereco.Endereco enderecoSac = new org.jrimum.domkee.comum.pessoa.endereco.Endereco();
        enderecoSac.setUF(UnidadeFederativa.valueOfSigla(contrato.getLocatario().getEndereco().getCodUf()));
        enderecoSac.setLocalidade(contrato.getLocatario().getEndereco().getCidade());
        enderecoSac.setCep(new CEP(contrato.getLocatario().getEndereco().getDesCep()));
        enderecoSac.setBairro(contrato.getLocatario().getEndereco().getDesBairro());
        enderecoSac.setLogradouro(contrato.getLocatario().getEndereco().getDesLogradouro());
        enderecoSac.setNumero(contrato.getLocatario().getEndereco().getDesNumero());
        sacado.addEndereco(enderecoSac);

        /*
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
         */
        // Informando dados sobre a conta bancária do título.
        ContaBancaria contaBancaria = null;
        if (config.getBanco().getBanco() == BancosEnum.ITAU) {
            contaBancaria = new ContaBancaria(BancosSuportados.BANCO_ITAU.create());
        }

        if (contaBancaria == null) {
            System.out.println("Conta bancária não cadastrada");
            return null;
        }

        contaBancaria.setNumeroDaConta(new NumeroDaConta(Integer.parseInt(config.getBanco().getContaNumero()), config.getBanco().getContaDigito()));
        contaBancaria.setCarteira(new Carteira(Integer.parseInt(config.getCarteira())));
        contaBancaria.setAgencia(new Agencia(Integer.parseInt(config.getBanco().getAgenciaNumero()), config.getBanco().getAgenciaDigito()));

        Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
        titulo.setNumeroDoDocumento(config.getNumeroDocumento());
        titulo.setNossoNumero(config.getNossoNumero());
        titulo.setDigitoDoNossoNumero(config.getNossoNumeroDigito());
        titulo.setValor(contrato.getValor());
        titulo.setDataDoDocumento(new Date());

        titulo.setDataDoVencimento(dataHj.getTime());
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Aceite.A);
//		titulo.setDesconto(BigDecimal.ZERO);
//		titulo.setDeducao(BigDecimal.ZERO);
//		titulo.setMora(BigDecimal.ZERO);
//		titulo.setAcrecimo(BigDecimal.ZERO);
//		titulo.setValorCobrado(BigDecimal.ZERO);

        /*
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
         */
        Boleto boletoBank = new Boleto(titulo);

        boletoBank.setLocalPagamento(config.getLocalPagamento());
        boletoBank.setInstrucaoAoSacado(config.getInstrucaoSacado());
        boletoBank.setInstrucao1(config.getInstrucao1());
        boletoBank.setInstrucao2(config.getInstrucao2());
        boletoBank.setInstrucao3(config.getInstrucao3());
        boletoBank.setInstrucao4(config.getInstrucao4());
        boletoBank.setInstrucao5(config.getInstrucao5());
        boletoBank.setInstrucao6(config.getInstrucao6());
        boletoBank.setInstrucao7(config.getInstrucao7());
        boletoBank.setInstrucao8(config.getInstrucao8());

        /*
		 * GERANDO O BOLETO BANCÁRIO.
         */
        // Instanciando um objeto "BoletoViewer", classe responsável pela
        // geração do boleto bancário.
        BoletoViewer boletoViewer = new BoletoViewer(boletoBank);

        // Alterado para pegar o path do sistema automático , gerando dentro
        // do projeto.
        // Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
        // pasta do projeto. Outros exemplos:
        // WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
        // LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
        String pasta = "/boletos";

        String caminhoCompletoPasta = FacesUtil.getExternalContext().getRealPath(pasta) + "/";
        BoletoLocal boletoLocal = new BoletoLocal();
        contrato.getBoletos().add(boletoLocal);
        boletoLocal.setContrato(contrato);

        String nomeArquivoPDF = contrato.getLocatario().getNome().replace(" ", "_") + boletoLocal.getMes() + boletoLocal.getAno() + ".pdf";

        System.out.println(caminhoCompletoPasta + nomeArquivoPDF);
        File arquivoPdf = boletoViewer.getPdfAsFile(caminhoCompletoPasta + nomeArquivoPDF);

        boletoLocal.setPath(pasta + nomeArquivoPDF);

        super.save(boletoLocal);

        return boletoLocal;
    }

    private void configuraBoletoESalva(Contrato contrato) {

        //PEGA CONFIGURAÇÕES DO BOLETO
        ConfiguracoesBoleto config = getConfig();


        /*
		 * I VALIDANDO A DATA PARA GERAR CONFORME CONFIGURADO
         */
        int diaVencimento = contrato.getDiaVencimento();
        Calendar dataHj = Calendar.getInstance();

        //SE O DIA DO VENCIMENTO DO BOLETO FOR MENOR OU IGUAL
        // A DATA DE HJ GERA NORMAL, SE NÃO ELE GERA PRO PROXIMO MES
        if (dataHj.get(Calendar.DAY_OF_MONTH) <= diaVencimento) {

        } else {
            dataHj.add(Calendar.MONTH, 1);
        }

        dataHj.set(Calendar.DAY_OF_MONTH, diaVencimento);
        Calendar dataMaxima = Calendar.getInstance();
        dataMaxima.add(Calendar.DAY_OF_MONTH, config.getDiasAntecipadoParaGeracaoDeBoleto());

        //SE A DATA FOR MAIOS QUE O MÁXIMO CONFIGURADO NÃO GERA NADA.
        if (dataHj.after(dataMaxima)) {
            System.out.println("data depois do configurado");
            return;
        }

        /*
        *   VERIFICA SE O BOLETO JÁ EXISTE NA BASE SE SIM NÃO SALVA NA BASE
         */
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("mes", dataHj.get(Calendar.MONTH)+1);
        filtros.put("ano", dataHj.get(Calendar.YEAR));
        filtros.put("contrato", contrato);

        BoletoLocal boletoBase = super.buscaUnicaUnicaComParametros(filtros, new HashMap<>());

        if (boletoBase != null && boletoBase.getId() != null) {
            System.out.println("Boleto já gerado no sistema");
            return;
        }


        /*
		 * INFORMANDO DADOS SOBRE O CEDENTE.
         */
        Cedente cedente = new Cedente(config.getBanco().getNomeBeneficiado(), config.getDocBeneficiado());

        /*
		 * INFORMANDO DADOS SOBRE O SACADO.
         */
        Sacado sacado = new Sacado(contrato.getLocatario().getNome(), contrato.getLocatario().getDocumento());

        // Informando o endereço do sacado.
        org.jrimum.domkee.comum.pessoa.endereco.Endereco enderecoSac = new org.jrimum.domkee.comum.pessoa.endereco.Endereco();
        enderecoSac.setUF(UnidadeFederativa.valueOfSigla(contrato.getLocatario().getEndereco().getCodUf()));
        enderecoSac.setLocalidade(contrato.getLocatario().getEndereco().getCidade());
        enderecoSac.setCep(new CEP(contrato.getLocatario().getEndereco().getDesCep()));
        enderecoSac.setBairro(contrato.getLocatario().getEndereco().getDesBairro());
        enderecoSac.setLogradouro(contrato.getLocatario().getEndereco().getDesLogradouro());
        enderecoSac.setNumero(contrato.getLocatario().getEndereco().getDesNumero());
        sacado.addEndereco(enderecoSac);

        /*
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
         */
        // Informando dados sobre a conta bancária do título.
        ContaBancaria contaBancaria = null;
        if (config.getBanco().getBanco() == BancosEnum.ITAU) {
            contaBancaria = new ContaBancaria(BancosSuportados.BANCO_ITAU.create());
        }

        if (contaBancaria == null) {
            System.out.println("Conta bancária não cadastrada");
            return;
        }

        contaBancaria.setNumeroDaConta(new NumeroDaConta(Integer.parseInt(config.getBanco().getContaNumero()), config.getBanco().getContaDigito()));
        contaBancaria.setCarteira(new Carteira(Integer.parseInt(config.getCarteira())));
        contaBancaria.setAgencia(new Agencia(Integer.parseInt(config.getBanco().getAgenciaNumero()), config.getBanco().getAgenciaDigito()));

        Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
        titulo.setNumeroDoDocumento(config.getNumeroDocumento());
        titulo.setNossoNumero(config.getNossoNumero());
        titulo.setDigitoDoNossoNumero(config.getNossoNumeroDigito());
        titulo.setValor(contrato.getValor());
        titulo.setDataDoDocumento(new Date());

        titulo.setDataDoVencimento(dataHj.getTime());
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        titulo.setAceite(Aceite.A);
//		titulo.setDesconto(BigDecimal.ZERO);
//		titulo.setDeducao(BigDecimal.ZERO);
//		titulo.setMora(BigDecimal.ZERO);
//		titulo.setAcrecimo(BigDecimal.ZERO);
//		titulo.setValorCobrado(BigDecimal.ZERO);

        /*
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
         */
        Boleto boletoBank = new Boleto(titulo);

        boletoBank.setLocalPagamento(config.getLocalPagamento());
        boletoBank.setInstrucaoAoSacado(config.getInstrucaoSacado());
        boletoBank.setInstrucao1(config.getInstrucao1());
        boletoBank.setInstrucao2(config.getInstrucao2());
        boletoBank.setInstrucao3(config.getInstrucao3());
        boletoBank.setInstrucao4(config.getInstrucao4());
        boletoBank.setInstrucao5(config.getInstrucao5());
        boletoBank.setInstrucao6(config.getInstrucao6());
        boletoBank.setInstrucao7(config.getInstrucao7());
        boletoBank.setInstrucao8(config.getInstrucao8());

        /*
		 * GERANDO O BOLETO BANCÁRIO.
         */
        // Instanciando um objeto "BoletoViewer", classe responsável pela
        // geração do boleto bancário.
        BoletoViewer boletoViewer = new BoletoViewer(boletoBank);

        // Alterado para pegar o path do sistema automático , gerando dentro
        // do projeto.
        // Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
        // pasta do projeto. Outros exemplos:
        // WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
        // LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
        String pasta = "/boletos";

        String caminhoCompletoPasta = FacesUtil.getExternalContext().getRealPath(pasta) + "/";
        BoletoLocal boletoLocal = new BoletoLocal();
        contrato.getBoletos().add(boletoLocal);
        boletoLocal.setContrato(contrato);

        String nomeArquivoPDF = contrato.getLocatario().getNome().replace(" ", "_") + boletoLocal.getMes() + boletoLocal.getAno() + ".pdf";

        System.out.println(caminhoCompletoPasta + nomeArquivoPDF);
        File arquivoPdf = boletoViewer.getPdfAsFile(caminhoCompletoPasta + nomeArquivoPDF);

        boletoLocal.setPath(pasta + nomeArquivoPDF);

        //se chegou até aqui salva na base
        super.save(boletoLocal);

    }

    private ConfiguracoesBoleto getConfig() {
        List<ConfiguracoesBoleto> configs = configBoletoService.findAll();
        if (configs == null || configs.isEmpty()) {
            FacesMensagensUtil.adcionarMensagemErro("Para gerar boletos é necessário que as configurações de banco estejam completas");
            return null;
        }

        return configs.get(0);
    }

    @Override
    public BoletoLocal onSalvar(BoletoLocal boleto) {
        if (boleto.getId() == null) {
            boleto.setCriado(new Date());
            boleto = this.save(boleto);
        } else {
            boleto.setAtualizado(new Date());
            this.update(boleto);
        }

        return boleto;
    }

    @Override
    public void onExcluir(BoletoLocal entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    private Date[] getRangeDatasDoMes(Integer diasAntes) {
//        Calendar data = Calendar.getInstance();
//        data.add(Calendar.DAY_OF_MONTH, diasAntes);
//
//        int diaMaximo = data.get(Calendar.DAY_OF_MONTH);
//
//        Date[] datasFiltro = new Date[2];
//        datasFiltro[0] = data.getTime();
//        
//        datasFiltro[1] = data.getTime();
//
//        return datasFiltro;
//    }
    @Override
    public void onGerarBoletosAutomatico() {
        List<Contrato> contratos = contratoService.findAll();
        for (Contrato contrato : contratos) {
            configuraBoletoESalva(contrato);
        } 
    }

}
