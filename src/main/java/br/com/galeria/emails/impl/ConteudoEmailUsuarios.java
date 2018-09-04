/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.emails.impl;

import br.com.galeria.emails.ConteudoEmail;
import br.com.galeria.entidades.Pessoa;
import br.com.galeria.entidades.Usuario;
import br.com.galeria.util.ConstantesDoSistema;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.mail.BodyPart;

/**
 *
 * @author subversion
 */
public class ConteudoEmailUsuarios implements ConteudoEmail {

    private String conteudo;
    private String emails;
    private String assunto;

    public ConteudoEmailUsuarios() {
        conteudo = conteudoEmailUsuario();
    }

    @Override
    public String getConteudo() {
        return conteudo;
    }

    @Override
    public String emails() {
        return emails;
    }

    @Override
    public String getAssunto() {
        return assunto;
    }

    @Override
    public void configuraDados(Object conteudo, String assunto, String emails) {
        this.emails = emails;
        this.assunto = assunto;

        trocaDados((Usuario) conteudo);
    }

    private void trocaDados(Usuario usuario) {
        conteudo = conteudo.replace("#nome", usuario.getNome());
        conteudo = conteudo.replace("#login", usuario.getLogin().getLogin());

        conteudo = conteudo.replace("#senha", usuario.getLogin().getSenhaDescrip());
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
        conteudo = conteudo.replace("#data", df.format(new Date()));

        conteudo = conteudo.replace("#link", ConstantesDoSistema.URL_SISTEMA);
    }

    private String conteudoEmailUsuario() {
        return "<html>\n"
                + "<body>\n"
                + "<div style='border: 1px solid;width: 550px;'>\n"
                + "<img src=''><br/>\n"
                + "\n"
                + "<div style='margin-left: 22px;text-align: left'>\n"
                + "<br/><br/>\n"
                + "Olá #nome, <br/>\n"
                + "Bem vindo ao GIDE.<br/>\n"
                + "Segue seus dados de acesso.<br/><br/>\n"
                + " \n"
                + "<b>Dados de Acesso</b><br/>\n"
                + "<br/>\n"
                + "<table>\n"
                + "\n"
                + "<tr> \n"
                + "<td>Login:</td> <td>#login</td>\n"
                + "</tr>\n"
                + "<tr> \n"
                + "<td>Senha:</td> <td>#senha</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td>Acesso:</td><td>#link</td>"
                + "</tr>"
                + "</table>\n"
                + "__________________________________________<br/>\n"
                + "\n"
                + "<br/><br/>\n"
                + "Dúvidas? <a href='mailto:suporte@gide.com.br' >suporte@gide.com.br</a><br/><br/>\n"
                + " <b>Equipe GIDE</b><br/>\n"
                + "  <br/><br/>\n"
                + " <font style='font-size: small' >São Jósé do Rio Preto, #data </font>\n"
                + " <br/><br/>\n"
                + "  </div>\n"
                + "\n"
                + "\n"
                + " </div>\n"
                + "</body>\n"
                + "</html>\n"
                + "";
    }

    @Override
    public List<BodyPart> getAnexos() {
        return null;
    }

}
