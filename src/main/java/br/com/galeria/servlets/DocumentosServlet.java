///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.galeria.servlets;
// 
//
//import br.com.galeria.services.ServiceGenerico;
//import com.github.adminfaces.starter.infra.security.LogonMB;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.ejb.EJB;
//import javax.inject.Inject;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.io.IOUtils;
//
///**
// *
// * @author Renan
// */
//@WebServlet(name = "DocumentosServlet", urlPatterns = {"/DocumentosServlet"}, asyncSupported = true)
//public class DocumentosServlet extends HttpServlet {
//
//    @EJB(beanName = "ConteudoArquivoServiceImpl")
//    private ServiceGenerico<ConteudoArquivo> conteudoArquivoService;
//
//    @Inject
//    private LogonMB logonMB;
//
//    @Override
//    protected void service(HttpServletRequest request,
//            HttpServletResponse response) throws ServletException, IOException {
//
//        try {
//
//            if ((logonMB.getUsuarioLogado() == null || logonMB.getUsuarioLogado().getIdPessoa() == null)) {
//                escreveMensagemSemPermissao(response);
//                return;
//            }
//
//            String parameter = request.getParameter("file");
//            if (parameter == null || parameter.equals("")) {
//                escreveMensagemArquivoNaoEncontrado(response);
//                return;
//            }
//
//            Long idArquivo = Long.valueOf(parameter);
//
//            ConteudoArquivo arquivo = conteudoArquivoService.findOne(idArquivo);
//
//            if (arquivo == null) {
//                escreveMensagemArquivoNaoEncontrado(response);
//                return;
//            }
//
//            String contextPath = request.getServletContext().getRealPath("/");
//            byte[] dadosDescompactado = arquivo.getConteudo();
//
////            if (arquivo.getTipoArquivo() == 4) {
////                arquivo.getLogs().add(new ArquivoLog(TiopAcaoArquivoLog.BAIXADO, loginMB.getUsuario().getNome(), arquivo));
////                arquivoService.atualizar(arquivo);
////                File file = ArquivosUtil.byteToFile(request.getRealPath("/") + "/" + ConstantesSistema.PASTA_DOWNLOADS, arquivo.getNome(), dadosDescompactado);
////                downloadArquivo(response, file, arquivo.getMimeType());
////                return;
////            } else {
////                arquivo.getLogs().add(new ArquivoLog(TiopAcaoArquivoLog.VISUALIZADO, loginMB.getUsuario().getNome(), arquivo));
////                arquivoService.atualizar(arquivo);
////            }
//            response.setContentType(arquivo.getMimeType());
//            response.setHeader("Content-Type", arquivo.getMimeType());
//            response.setHeader("Content-Length", String.valueOf(dadosDescompactado.length));
//            response.setHeader("Content-Disposition", "inline; filename=\"" + arquivo.getNomeArquivo() + "\"");
////         = request.getServletContext().getContextPath();
//
//            IOUtils.write(dadosDescompactado, response.getOutputStream());
//        } catch (IOException | NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void escreveMensagemSemPermissao(HttpServletResponse response) {
//        PrintWriter out;
//        try {
//            out = response.getWriter();
//            // escreve o texto
//            out.println("<html>");
//            out.println("<body>");
//            out.println("Sem permissões para acessar esse arquivo!");
//            out.println("</body>");
//            out.println("</html>");
//        } catch (IOException ex) {
//            Logger.getLogger(DocumentosServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    private void escreveMensagemArquivoNaoEncontrado(HttpServletResponse response) {
//        PrintWriter out;
//        try {
//            out = response.getWriter();
//            // escreve o texto
//            out.println("<html>");
//            out.println("<body>");
//            out.println("O arquivo não foi encontrado!");
//            out.println("</body>");
//            out.println("</html>");
//        } catch (IOException ex) {
//            Logger.getLogger(DocumentosServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    private void downloadArquivo(HttpServletResponse response, File file, String mimeType) throws FileNotFoundException, IOException {
//
//        response.setContentType(mimeType);
//        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition",
//                String.format("attachment; filename=\"%s\"", file.getName()));
//
//        OutputStream out = response.getOutputStream();
//        try (FileInputStream in = new FileInputStream(file)) {
//            byte[] buffer = new byte[4096];
//            int length;
//            while ((length = in.read(buffer)) > 0) {
//                out.write(buffer, 0, length);
//            }
//        }
//        out.flush();
//
//    }
//
////  
////    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
////    /**
////     * Handles the HTTP <code>GET</code> method.
////     *
////     * @param request servlet request
////     * @param response servlet response
////     * @throws ServletException if a servlet-specific error occurs
////     * @throws IOException if an I/O error occurs
////     */
////    private ArquivoService arquivoService;
////    @Override
////    protected void doGet(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////    try{
////    
////
////        arquivoService = new ArquivoServiceImpl();
////        Long idArquivo = Long.valueOf(request.getParameter("file")); 
////     
////        Arquivo arquivo = arquivoService.findOne(idArquivo);
////        
////        response.setContentType(getContent(arquivo.getNome()));  
//////         = request.getServletContext().getContextPath();
////        String contextPath = request.getServletContext().getRealPath("");
////        IOUtils.write(arquivo.getConteudo().getDadosDescompactado(contextPath), response.getOutputStream());
////     }catch(Exception e){
////         
////     }   
////  }
////    
////    private String getContent(String nome){
////        String[] split = nome.split("\\.");
////        if(split.length <= 0) return "";
////        String ext = split[1];
////        ext);
////        if(ext.equalsIgnoreCase("jpg")) return "image/jpeg";
////        else if( ext.equalsIgnoreCase("pdf")) return "application/pdf";
////        else if(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx") )  return "application/msword";
////
////        //nunca deve ocorrer
////        return "image/jpeg";
////    }
////
////    /**
////     * Handles the HTTP <code>POST</code> method.
////     *
////     * @param request servlet request
////     * @param response servlet response
////     * @throws ServletException if a servlet-specific error occurs
////     * @throws IOException if an I/O error occurs
////     */
////    @Override
////    protected void doPost(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////      
////    }
//}
