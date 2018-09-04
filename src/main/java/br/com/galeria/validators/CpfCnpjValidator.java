package br.com.galeria.validators;

import java.io.Serializable;
import java.util.InputMismatchException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@FacesConverter(value = "br.com.galeria.validators.CpfCnpjValidator")	//in fact, this is as a Converter as a Validator 
public class CpfCnpjValidator implements Converter, Serializable {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
            
                if(arg2 == null)
                    return null;        //quando o campo nao eh requerido, arg2 vem null, e isto nao eh um erro, uma vez que o campo nao eh requerido
            
		String retorno = null;                
		if (arg2 != null && !arg2.isEmpty()) {
                    
                        //if(arg2.trim().equals("888")) return arg2;  //provisorio, para o master, até definir tela restrita   
                            
			retorno = arg2.replaceAll("[\\D]", "");
		}
		 
		//Se o retorno for diferente de nullo verifico o algoritmo                                
                
		if (retorno != null) {
			if (retorno.length()==11 || retorno.length()==14) {
				//se tiver 11 numeros eh cpf, valido cpf, e retorno null para exibir mensagem
				if (retorno.length() == 11 && !isCPF(retorno))
					retorno = null;
				//se tiver 14 numeros eh CNPJ, valido CNPJ, e retorno null para exibir mensagem
				else if (retorno.length() == 14 && !isCNPJ(retorno))
					retorno = null;
			}else{
				//se o algoritmo for vazio passo para null para exibir mensagem
				retorno = null;
			}
		}
		
		if (retorno == null)
			throw new ConverterException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "CPF/CNPJ inválido", ""));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		 
		if (arg2 != null) {
			String cpfCnpj = arg2.toString();
			if (cpfCnpj.length() == 11) {
				return cpfCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
						"$1.$2.$3-$4");
			} else if (cpfCnpj.length() == 14) {
				return cpfCnpj.replaceAll(
						"(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
						"$1.$2.$3/$4-$5");
			}
		}
		return "";
	}

    public boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
           (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
          sm = 0;
          peso = 10;
          for (i=0; i<9; i++) {
            // converte o i-esimo caractere do CPF em um numero:
            // por exemplo, transforma o caractere '0' no inteiro 0
            // (48 eh a posicao de '0' na tabela ASCII)
            num = (int)(CPF.charAt(i) - 48);  
            sm = sm + (num * peso);
            peso = peso - 1;
          }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
            dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

// Calculo do 2o. Digito Verificador
      sm = 0;
      peso = 11;
      for(i=0; i<10; i++) {
        num = (int)(CPF.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11))
         dig11 = '0';
      else dig11 = (char)(r + 48);

// Verifica se os digitos calculados conferem com os digitos informados.
      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
         return(true);
      else return(false);
    } catch (InputMismatchException erro) {
        return(false);
    }
	}// fim do calcular cpf ==================================================

    public boolean isCNPJ(String CNPJ) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
    if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
        CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
        CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
        CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
        CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
       (CNPJ.length() != 14))
       return(false);

    char dig13, dig14;
    int sm, i, r, num, peso;

// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
    try {
// Calculo do 1o. Digito Verificador
      sm = 0;
      peso = 2;
      for (i=11; i>=0; i--) {
// converte o i-�simo caractere do CNPJ em um n�mero:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posi��o de '0' na tabela ASCII)
        num = (int)(CNPJ.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
           peso = 2;
      }

      r = sm % 11;
      if ((r == 0) || (r == 1))
         dig13 = '0';
      else dig13 = (char)((11-r) + 48);

// Calculo do 2o. Digito Verificador
      sm = 0;
      peso = 2;
      for (i=12; i>=0; i--) {
        num = (int)(CNPJ.charAt(i)- 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
           peso = 2;
      }

      r = sm % 11;
      if ((r == 0) || (r == 1))
         dig14 = '0';
      else dig14 = (char)((11-r) + 48);

// Verifica se os d�gitos calculados conferem com os d�gitos informados.
      if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
         return(true);
      else return(false);
    } catch (InputMismatchException erro) {
        return(false);
    }
	}

}
