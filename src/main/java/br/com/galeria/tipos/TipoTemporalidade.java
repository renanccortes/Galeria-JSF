/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.tipos;

/**
 *
 * @author Bruna
 */
public enum TipoTemporalidade {
    CORRENTE("Corrente"),
    INTERMEDIARIO("Intermediário"),
    DESTRUIDO("Destruído"),
    ARQUIVO_MORTO("Arquivo morto");
    
    private final String nome;

    private TipoTemporalidade(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
