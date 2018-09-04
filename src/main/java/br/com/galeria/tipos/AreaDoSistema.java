/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.tipos;

/**
 * Representa todos menus/submenus do sistema que abrem alguma tela. Caso mudem
 * de nome, a atualização é feita aqui
 *
 * @author Renan, Mar 20, 2018 1:59:14 PM
 */
public enum AreaDoSistema {

    CADASTROS_GERAIS("Cadastros Gerais"),
    CONTRATOS("CONTRATOS"),
    BOLETOS("BOLETOS"),
    RELATORIOS("RELATORIOS"),
    PISOS("PISO"),
    SALAS("SALA"),
    LOCATARIOS("LOCATARIOS"),
    PERFIS("PERFIS"),
    BANCOS("BANCOS"),
    USUARIOS("USUARIOS"),
    CONFIGURACOES("CONFIGURACOES");

//EXAMES COMPLEMENTARES    
    private String label;

    private AreaDoSistema(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
