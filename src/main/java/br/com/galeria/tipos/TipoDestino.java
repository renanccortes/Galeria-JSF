/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.tipos;

/**
 *
 * @author Michel
 */
public enum TipoDestino {
    ARQUIVO_MORTO("Arquivo Morto"),
    DESTRUICAO("Destruição");

    private final String tipoDestino;

    private TipoDestino(String tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

    @Override
    public String toString() {
        return this.tipoDestino;
    }

}
