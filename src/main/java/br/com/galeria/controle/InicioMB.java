/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Renan
 */
@Named
@ViewScoped
public class InicioMB implements Serializable {

    @Inject
    private LogonMB sessaoMB;

    public String tamanhoTotalUtilizado() {
        return "";
    }

    public String totalDeArquivos() {
        return "";
    }

}
