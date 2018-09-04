/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.conversores;

/**
 *
 * @author Renan
 */
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "conversorNumero")
public class ConversorNumero implements Converter, Serializable {

    @Override           //from view to bean
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if ((value == null) || (value.trim().isEmpty())) {
            return 0.0d;
        } else {
            try {
                if (value.contains(".")) {
                    value.length();
                    char[] valor = value.toCharArray();
                    int indice1 = value.length() - 3;
                    Character x = value.charAt(indice1);
                    if (x.equals('.')) {
                        valor[indice1] = ',';
                    }
                    int indice2 = value.length() - 2;
                    x = value.charAt(indice2);
                    if (x.equals('.')) {
                        valor[indice1] = ',';
                    }
                    value = new String(valor);
                    if (value.contains(",")) {
                        value = value.replaceAll(Pattern.quote("."), "");
                        String novoValor = value.replace(",", ".");
                        return new Double(novoValor);
                    }
                }
            } catch (Exception e) {
                throw new ConverterException("Valor inválido");
            }

            try {
                value = value.replaceAll(Pattern.quote("."), "");
                String novoValor = value.replace(",", ".");
                return new Double(novoValor);

            } catch (Exception e) {
                throw new ConverterException("Valor inválido");
            }
        }
    }

    @Override           //from bean to view
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//            System.out.printf("================ CONVERSOR NUMERO: EXIBE: %s", value);
        try {
            if ((value == null) || (value.toString().trim().isEmpty())) {
                return "0,00";
            } else {
                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
                numberFormat.setMinimumFractionDigits(2);
                numberFormat.setMaximumFractionDigits(2);
                String retorno = numberFormat.format(Double.valueOf(value.toString()));

                return retorno;
            }
        } catch (Exception e) {
            return "";

        }
    }

}
