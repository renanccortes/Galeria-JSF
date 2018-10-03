/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.emails.impl;

import br.com.galeria.emails.EmailSender;
import br.com.galeria.emails.ConteudoEmail;
import br.com.galeria.entidades.Login;
import br.com.galeria.entidades.Usuario;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author subversion
 */
public class EmailSenderImpl implements EmailSender {

    private static Session session;
    private static Properties props;

    private static final String ENDERECO_DE = "#";
    private static final String EMAIL_PASSWORD = "#";

    public EmailSenderImpl() {

    }

    public static void main(String[] args) {
        EmailSenderImpl e = new EmailSenderImpl();
        ConteudoEmail conteudo = new ConteudoEmailUsuarios();

        Usuario usuario = new Usuario();
        usuario.setNome("Renan Cortes Carvalho");

        Login login = new Login();
        login.setLogin("renan");
        login.setSenha("1234");
        usuario.setLogin(login);

        conteudo.configuraDados(usuario, "ASSUNTO", "EMAIL DESTINO");
        try {
            e.enviarEmail(conteudo);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean enviarEmail(ConteudoEmail conteudo) throws MessagingException {

        try {
            Multipart conteudoEmail = new MimeMultipart();
            BodyPart texto = new MimeBodyPart();
            texto.setContent(conteudo.getConteudo(), "text/html; charset=Cp1252");
            conteudoEmail.addBodyPart(texto);

            //INCLUINDO ANEXOS
            if (conteudo.getAnexos() != null && !conteudo.getAnexos().isEmpty()) {
                for (BodyPart anexo : conteudo.getAnexos()) {
                    conteudoEmail.addBodyPart(anexo);
                }
            }

            setMail(conteudoEmail, conteudo.getAssunto(), conteudo.emails());
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static boolean setMail(Multipart conteudo, String assunto, String enderecos) throws MessagingException {

        setProperties();
        session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ENDERECO_DE, EMAIL_PASSWORD);
            }
        });
        session.setDebug(false);

        Message mess = new MimeMessage(session);
        mess.setFrom(new InternetAddress(ENDERECO_DE));
        Address[] destinatarios = InternetAddress.parse(enderecos, true);
        mess.setRecipients(Message.RecipientType.BCC, destinatarios);
        mess.setSubject(assunto);
        mess.setContent(conteudo);

        Transport.send(mess);

        return true;
    }

    private static void setProperties() {
        props = new Properties();
        props.put("mail.smtp.host", "SMTP HOST");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
    }

}
