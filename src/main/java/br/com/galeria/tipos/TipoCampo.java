/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.tipos;

/**
 *
 * @author Renan
 */
public enum TipoCampo {

    TEXTO("Texto"), 
    VALOR("Valor"),
    DATA("Data");

    private final String nome;

    private TipoCampo(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {

        return this.nome;
    }

}
