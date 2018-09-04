package br.com.galeria.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

public class FacesUtil {

    public static final Integer ERRO = 0;
    public static final Integer AVISO = 1;
    public static final Integer ERRO_FATAL = 2;
    public static final Integer INFORMACAO = 3;

    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static String converterData(Date data) {
        if (data == null) {
            return "";
        }
        String formato = "dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(data);
    }

    public static String converterHora(Date data) {
        String formato = "HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(data);
    }

    public static String converterDataHora(Date data) {
        String formato = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(data);
    }

    public static String converterDataAmericano(Date date) {
        String formato = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(date);

    }

    public static String converterDataHoraPasta(Date date) {
        String formato = "dd-MM-yyyy-HH-mm-ss";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(date);

    }

    public static String converterDataHoraAmericano(Date date) {
        String formato = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(date);

    }

    public static String converterDoubleMoeda(double valor) {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(); //Formato de moeda atual do sistema
        return formatoMoeda.format(valor);
    }

    public static String converteCpfCnpj(String cpfCnpj) {
        if (cpfCnpj.length() < 12) {
            return formatar(cpfCnpj, "###.###.###-##");
        } else {
            return formatar(cpfCnpj, "##.###.###/####-##");
        }

    }

    public static Date converterStringToDateAmericano(String data) {
        if (data == null || data.equals("")) {
            return null;
        }

        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (java.util.Date) formatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static Date converterStringToDate(String data, String pattern) {
        if (data == null || data.equals("")) {
            return null;
        }

        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat(pattern);
            date = (java.util.Date) formatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static String converterStringAmericanoToStringBrasilData(String data) {
        if (data == null) {
            return "";
        }
        if (data.length() != 8) {
            return data;
        }

        StringBuilder retorno = new StringBuilder();
        retorno.append(data.substring(6, 8));
        retorno.append("/");
        retorno.append(data.substring(4, 6));
        retorno.append("/");
        retorno.append(data.substring(0, 4));

        return retorno.toString();
    }

//    public static byte[] uploadedFileToByte(UploadedFile file){
//        
//        try {
//            InputStream is = file.getInputstream();
//            byte[] bytes = IOUtils.toByteArray(is);
//            return bytes;
//        } catch (IOException ex) {
//            return null;
//        }
//           
//        
//    }
    public static String gerarHash(String frase, String algoritmo) {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            bytes = md.digest();
        } catch (NoSuchAlgorithmException e) {

        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }

    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static String getUrlAttribute(String name) {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParameter(name);
    }

    public static void FacesMessage(String msg, Integer tipo) {

        if (tipo == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
        } else if (tipo == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, ""));
        } else if (tipo == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, ""));
        } else if (tipo == 3) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
        }

    }

    public static void cleanSubmittedValues(UIComponent component) {
        if (component instanceof EditableValueHolder) {
            EditableValueHolder evh = (EditableValueHolder) component;
            evh.setSubmittedValue(null);
            evh.setValue(null);
            evh.setLocalValueSet(false);
            evh.setValid(true);
        }
        if (component.getChildCount() > 0) {
            for (UIComponent child : component.getChildren()) {
                cleanSubmittedValues(child);
            }
        }
    }

    public static boolean isValidateEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static void gravarImagemCompactada(byte[] img, String nome) throws IOException {

        String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("imagens/" + nome);

        // Encontra o ImageWriter correto de acordo com o sufixo
//        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("png");
//        ImageWriter writer = (ImageWriter) writers.next();
        // Cria um conjunto de parâmetros para configuração
//        ImageWriteParam param = writer.getDefaultWriteParam();
        // Altera para não usar compressão automática
//        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // Muda a taxa de compressão para 100% (valor entre 0 e 1)
//            float valor = (float) 0.5;
//        param.setCompressionQuality(valor);
        // Salva a imagem
//        FileImageOutputStream output = new FileImageOutputStream(new File(caminho));
//        
//         writer.setOutput(output);
//        writer.write(null, new IIOImage(imagem , null, null), param);
        try {
            FileOutputStream fos = new FileOutputStream(caminho);
            fos.write(img);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static BufferedImage scaleImage(BufferedImage image, int newWidth, int newHeight, String caminho) {

        // Make sure the aspect ratio is maintained, so the image is not distorted
        double thumbRatio = (double) newWidth / (double) newHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;

        if (thumbRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
        }

        // Draw the scaled image
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

        // Encontra o ImageWriter correto de acordo com o sufixo
        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        // Cria um conjunto de parâmetros para configuração
        ImageWriteParam param = writer.getDefaultWriteParam();

        // Altera para não usar compressão automática
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

        // Muda a taxa de compressão para 100% (valor entre 0 e 1)
        float valor = (float) 0.5;
        param.setCompressionQuality(valor);

        FileImageOutputStream output;
        try {
            output = new FileImageOutputStream(new File(caminho));
            writer.setOutput(output);
            writer.write(null, new IIOImage(newImage, null, null), param);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FacesUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newImage;
    }

//metodo que redireciona as paginas
    public static void redirecionar(String url) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext contextEx = facesContext.getExternalContext();

            contextEx.redirect(url);
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao redirecionar para a pagina " + url + ", " + e, ""));
        }

    }

    public static void sendRedirect(String url) {
        try {
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext
                            .getCurrentInstance()
                            .getExternalContext()
                            .getResponse();

            response.sendRedirect(url);
        } catch (IOException ex) {
            Logger.getLogger(FacesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static SelectItem[] createFilterOptions(String[] data) {
        SelectItem[] options;
        if (data != null) {
            options = new SelectItem[data.length + 1];
            options[0] = new SelectItem("", "Selecione");
            for (int i = 0; i < data.length; i++) {
                options[i + 1] = new SelectItem(data[i], data[i]);
            }
        } else {
            options = new SelectItem[1];
            options[0] = new SelectItem("", "Selecione");
        }

        return options;
    }

    public static String getCaminhoAbsoluto(String pasta) {

        return FacesContext.getCurrentInstance().getExternalContext().getRealPath(pasta);
    }

    public static ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

    public static String getContextParam(String contextParam) {
        return getServletContext().getInitParameter(contextParam);
    }

    public static ExternalContext getExternalContext() {
        FacesContext fc = FacesContext.getCurrentInstance();

        return fc.getExternalContext();
    }

    public static HttpSession getHttpSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(create);
    }

    /**
     * Get managed bean based on the bean name.
     *
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
    public static Object getManagedBean(String beanName) {

        return getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
    }

    /**
     * Remove the managed bean based on the bean name.
     *
     * @param beanName the bean name of the managed bean to be removed
     */
    public static void resetManagedBean(String beanName) {
        getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
    }

    /**
     * Store the managed bean inside the session scope.
     *
     * @param beanName the name of the managed bean to be stored
     * @param managedBean the managed bean to be stored
     */
    public static void setManagedBeanInSession(String beanName, Object managedBean) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
    }

    /**
     * Get parameter value from request scope.
     *
     * @param name the name of the parameter
     * @return the parameter value
     */
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    /**
     * Add information message.
     *
     * @param msg the information message
     */
    public static void addInfoMessage(String msg) {
        addInfoMessage(null, msg);
    }

    /**
     * Add information message to a specific client.
     *
     * @param clientId the client id
     * @param msg the information message
     */
    public static void addInfoMessage(String clientId, String msg) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }

    /**
     * Add error message.
     *
     * @param msg the error message
     */
    public static void addErrorMessage(String msg) {
        addErrorMessage(null, msg);
    }

    /**
     * Add error message to a specific client.
     *
     * @param clientId the client id
     * @param msg the error message
     */
    public static void addErrorMessage(String clientId, String msg) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    private static Application getApplication() {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }

    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }

    private static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    private static Object getElValue(String el) {
        return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
    }

    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }

    public static String formatar(String valor, String mascara) {

        String dado = "";
        // remove caracteres nao numericos
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (Character.isDigit(c)) {
                dado += c;
            }
        }

        int indMascara = mascara.length();
        int indCampo = dado.length();

        for (; indCampo > 0 && indMascara > 0;) {
            if (mascara.charAt(--indMascara) == '#') {
                indCampo--;
            }
        }

        String saida = "";
        for (; indMascara < mascara.length(); indMascara++) {
            saida += ((mascara.charAt(indMascara) == '#') ? dado.charAt(indCampo++) : mascara.charAt(indMascara));
        }
        return saida;
    }

    public static void mostraDialog(String dialog, boolean mostrar) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            if (mostrar) {

                context.execute("PF('" + dialog + "').show();");
            } else {
                context.execute("PF('" + dialog + "').hide();");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String tamanhoArquivoString(double tamanho) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        return decimalFormat.format(tamanho).toString().concat(" Megabytes");

    }

    public static String formatFileSize(double size) {
        String hrSize = null;

        size = size * 1024.0;

        double k = size;
        double m = size / 1024.0;
        double g = ((size / 1024.0) / 1024.0);
        double t = (((size / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else {
            hrSize = dec.format(k).concat(" KB");
        }

        return hrSize;
    }

    public static boolean validaCEP(String cep) {

        Pattern pattern
                = Pattern.compile("[0-9]{5}-[0-9]{3}");

        Matcher m = pattern.matcher(cep);

        return m.matches();

    }

    public static byte[] uploadedFileToByte(UploadedFile file) {

        try {
            InputStream is = file.getInputstream();
            byte[] bytes = IOUtils.toByteArray(is);
            return bytes;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static void atualiza(String id) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(id);
    }

    public static String getUrl() {
        HttpServletRequest request = getRequest();
        return request.getRequestURI();
    }

    public static String getUrlCompleta() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        return request.getRequestURL().toString();
    }

    public static String getIpUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        return request.getRemoteAddr();
    }

    public static String getNavegador() {

        HttpServletRequest request = getRequest();

        Enumeration headers = request.getHeaderNames();
        String browser = "";
        while (headers.hasMoreElements()) {
            String header = ((String) headers.nextElement()).toLowerCase();
            if (header.equals("user-agent")) {
                browser = request.getHeader(header);
                return browser;
            }
        }

        return browser;
    }

    private static HttpServletRequest getRequest() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return (HttpServletRequest) fc.getExternalContext().getRequest();
    }

    /* Resolve o problemas das managed bean em viewscoped */
    public static void atualizarPagina() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
    }

    public static void executaJS(String javaScript) {
        RequestContext.getCurrentInstance().execute(javaScript);
    }

    public static void abrirDocumentoNovaAba(String mimeType, byte[] arquivo, String nomeArquivo) throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        final int DEFAULT_BUFFER_SIZE = 10_240;

        try {
            // Open file. 

            input = new BufferedInputStream(new ByteArrayInputStream(arquivo), DEFAULT_BUFFER_SIZE);

            // Init servlet response.
            response.reset();
            response.setContentType(mimeType);

            response.setHeader("Content-Type", mimeType);
            response.setHeader("Content-Length", String.valueOf(arquivo.length));
            response.setHeader("Content-Disposition", "inline; filename=\"" + nomeArquivo + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();

        } catch (Exception e) {
            FacesUtil.addErrorMessage("Falha ao abrir o arquivo, tente novamente mais tarde.");
        } finally {
            close(output);
            close(input);
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it. It may be useful to 
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();
            }
        }
    }

}
