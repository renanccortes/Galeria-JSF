/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.galeria.emails;

import br.com.galeria.emails.ConteudoEmail;
import java.io.Serializable;
import java.util.List;
import javax.mail.MessagingException;

/**
 *
 * @author subversion
 */
public interface EmailSender extends Serializable {
    
    /**
     * Envia email 
     * @param conteudo
     * @param assunto
     * @param emails
     * @return
     * @throws javax.mail.MessagingException
     */
    public boolean enviarEmail(ConteudoEmail conteudo) throws MessagingException;
    
}
