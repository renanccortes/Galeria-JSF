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
public enum BancosEnum {

    ITAU;

    @Override
    public String toString() {
        if (this == ITAU) {
            return "Itaú";
        }

        return "";
    }

}
