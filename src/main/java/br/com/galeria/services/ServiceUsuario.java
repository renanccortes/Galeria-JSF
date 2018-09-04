/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services;

import br.com.galeria.entidades.Usuario; 
import java.io.Serializable;
import javax.ejb.Local;

/**
 *
 * @author Renan
 */
@Local 
public interface ServiceUsuario extends Serializable, ServiceGenerico<Usuario> {
    
    public Usuario onLogar(Usuario usuario);
     
}
