/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.agendados;

import br.com.galeria.entidades.Notificacoes;
import br.com.galeria.services.ServiceGenerico; 
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author subversion
 */
public class EnvioDasNotificacoes {

    private List<NotificacoesDoUsuario> notificacoesUsuario;
    private ServiceGenerico<Notificacoes> dao;
    private List<Notificacoes> notificacoesExcluir; 

    public EnvioDasNotificacoes() {
    }

    public void processoDeEnvioDeNotificacoes() {

        notificacoesExcluir = new ArrayList<>();
        notificacoesUsuario = new ArrayList();

        preencheUsuariosNotificacoes(Notificacoes.VENCIMENTO_ANTECEDENCIA);
        enviarEmails();
        // EnvioDasNotificacoes.mostraNaTela();
        notificacoesUsuario.clear();
        preencheUsuariosNotificacoes(Notificacoes.VENCIMENTO_DOCUMENTO);
        enviarEmails();
        // EnvioDasNotificacoes.mostraNaTela();
        notificacoesUsuario.clear();
        preencheUsuariosNotificacoes(Notificacoes.VENCIMENTO_ARMAZENAMENTO);
        enviarEmails();
        //  EnvioDasNotificacoes.mostraNaTela();
        notificacoesUsuario.clear();

//        for (Notificacoes notExcluir : notificacoesExcluir) {
//            Arquivo arquivoUpdate = arquivoService.findOne(notExcluir.getArquivo().getIdArquivo());
//
//            arquivoUpdate.getNotificacoes().remove(notExcluir);
//
//            arquivoService.onSalvar(arquivoUpdate);
//
//        }
    }

    private void preencheUsuariosNotificacoes(int tipoNotificacao) {

//        List<Notificacoes> notificacoesPorDataAntecedencia = dao.getNotificacoesPorData(new Date(), tipoNotificacao);
//
//        for (Notificacoes not : notificacoesPorDataAntecedencia) {
//
//            PessoaAdministrador empresaAdministrador = not.getArquivo().getPessoa().getEmpresaAdministrador();
//            PessoaEmpresa empresaCliente = not.getArquivo().getPessoa();
//
//            boolean avisaAdministradores = not.getArquivo().getDocumento().getConfiguracao().isAvisoAdministradores();
//            boolean avisaClientes = not.getArquivo().getDocumento().getConfiguracao().isAvisoClientesSocio();
//
//            //PERCORRE LISTA DE USUARIOS DA EMPRESA ADMINISTRADORA
//            for (Usuario usuario : empresaAdministrador.getFuncionarios()) {
//
//                if (permissaoColaborador(usuario, tipoNotificacao)) {
//                    NotificacoesDoUsuario buscaUsuarioNoArray = buscaUsuarioNoArray(usuario.getIdUsuario());
//                    buscaUsuarioNoArray.setUsuario(usuario);
//                    buscaUsuarioNoArray.setTipoNotificacao(tipoNotificacao);
//                    buscaUsuarioNoArray.addNotificacao(not);
//
//                    notificacoesUsuario.remove(buscaUsuarioNoArray);
//                    notificacoesUsuario.add(buscaUsuarioNoArray);
//
//                }
//
//                if (permissaoSocio(usuario, tipoNotificacao, avisaAdministradores)) {
//                    NotificacoesDoUsuario buscaUsuarioNoArray = buscaUsuarioNoArray(usuario.getIdUsuario());
//                    buscaUsuarioNoArray.setUsuario(usuario);
//                    buscaUsuarioNoArray.setTipoNotificacao(tipoNotificacao);
//
//                    buscaUsuarioNoArray.addNotificacao(not);
//
//                    notificacoesUsuario.remove(buscaUsuarioNoArray);
//                    notificacoesUsuario.add(buscaUsuarioNoArray);
//
//                }
//
//            }
// 
//
//            notificacoesExcluir.add(not);
//
//        }

    }

//    private static boolean permissaoColaborador(Usuario usuario, int tipoNotificacao) {
//        if (usuario.isSocio()) {
//            return false;
//        }
//
//        if (tipoNotificacao == Notificacoes.VENCIMENTO_ANTECEDENCIA && !usuario.getNotificacao().isRepositorioDocumentosPastasAvisoAntecedenciaEmail()) {
//            return false;
//        }
//
//        if (tipoNotificacao == Notificacoes.VENCIMENTO_DOCUMENTO && !usuario.getNotificacao().isRepositorioDocumentosPastasVencimentosEmail()) {
//            return false;
//        }
//
//        if (tipoNotificacao == Notificacoes.VENCIMENTO_ARMAZENAMENTO && !usuario.getNotificacao().isRepositorioDocumentosPastasVencimentosArmazenamentoEmail()) {
//            return false;
//        }
//
//        return true;
//    }

//    private static boolean permissaoSocio(Usuario usuario, int tipoNotificacao, boolean avisaSocios) {
//
//        if (!avisaSocios || !usuario.isSocio()) {
//            return false;
//        }
//
//        if (tipoNotificacao == Notificacoes.VENCIMENTO_ANTECEDENCIA && !usuario.getNotificacao().isRepositorioDocumentosPastasAvisoAntecedenciaEmail()) {
//            return false;
//        }
//
//        if (tipoNotificacao == Notificacoes.VENCIMENTO_DOCUMENTO && !usuario.getNotificacao().isRepositorioDocumentosPastasVencimentosEmail()) {
//            return false;
//        }
//
//        if (tipoNotificacao == Notificacoes.VENCIMENTO_ARMAZENAMENTO && !usuario.getNotificacao().isRepositorioDocumentosPastasVencimentosArmazenamentoEmail()) {
//            return false;
//        }
//
//        return true;
//    }

    private void mostraNaTela() {
        for (NotificacoesDoUsuario notificacoesDoUsuario : notificacoesUsuario) {

            for (Notificacoes not : notificacoesDoUsuario.getNotificacoes()) {
            }
        }

    }

//    private NotificacoesDoUsuario buscaUsuarioNoArray(Long id) {
//        for (NotificacoesDoUsuario notificacoesDoUsuario : notificacoesUsuario) {
//            if (notificacoesDoUsuario.getUsuario().getIdUsuario().equals(id)) {
//                return notificacoesDoUsuario;
//            }
//        }
//
//        return new NotificacoesDoUsuario();
//    }

    private void enviarEmails() {

//        EmailSender emailSender = new EmailSenderImple();
//        for (NotificacoesDoUsuario notificacaoUsuario : notificacoesUsuario) {
//            ConteudoEmailDocumentosLista conteudo = new ConteudoEmailDocumentosLista();
//            notificacaoUsuario.getUsuario().populateAtributosContato();
//            conteudo.configuraDados(notificacaoUsuario, "Aviso de vencimento de documento(s)", notificacaoUsuario.getUsuario().getEmailCorporativo());
//            try {
//                emailSender.enviarEmail(conteudo);
//            } catch (MessagingException ex) {
//                Logger.getLogger(EnvioDasNotificacoes.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }
}
