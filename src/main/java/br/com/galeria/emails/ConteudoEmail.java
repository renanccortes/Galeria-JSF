/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.galeria.emails;

import java.io.Serializable;
import java.util.List;
import javax.mail.BodyPart;

/**
 *
 * @author subversion
 */
public interface ConteudoEmail extends Serializable {
    
    /**
     * Retorna o conteúdo do email
     * @return
     */
    public String getConteudo();
    
    /**
     *
     * @param conteudo
     * @param emails
     * @return
     */
    public String emails();
    
    /**
     * Retorna o assunto do email.
     * @return
     */
    public String getAssunto();
    
    /** Configura dados para o email
     * @param conteudo
     * @param assunto
     * @param emails
     */
    public void configuraDados(Object conteudo,String assunto, String emails);
    
    public List<BodyPart> getAnexos();
    
}
