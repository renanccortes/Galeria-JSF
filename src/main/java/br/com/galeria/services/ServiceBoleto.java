/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services;

import br.com.galeria.entidades.BoletoLocal;
import br.com.galeria.entidades.ConfiguracoesBoleto;
import br.com.galeria.entidades.Contrato;
import br.com.galeria.entidades.Usuario;
import java.io.Serializable;

/**
 *
 * @author Renan
 */
public interface ServiceBoleto extends Serializable, ServiceGenerico<BoletoLocal> {

    public BoletoLocal onSalvar(BoletoLocal boleto, Usuario usuarioLogado);

    public BoletoLocal onGerarBoleto(Contrato contrato, ConfiguracoesBoleto config);

    public void onGerarBoletosAutomatico();

}
