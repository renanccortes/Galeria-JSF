///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.galeria.util;
//
//import br.com.galeria.entidades.Arquivo;
//import br.com.galeria.table.LazyTableArquivo;
//import br.com.galeria.table.LazyTableGenerico;
//import java.io.Serializable;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// *
// * @author Renan
// */
//public class DateRange implements Serializable {
//
//    private Date fromValido;
//    private Date toValido;
//
//    private Date fromEnviado;
//    private Date toEnviado;
//
//    private Date fromData;
//    private Date toData;
//
//    private LazyTableArquivo<Arquivo> tabelaArquivos;
//
//    public DateRange(LazyTableArquivo<Arquivo> tabelaArquivos) {
//        this.tabelaArquivos = tabelaArquivos;
//        toData = new Date();
//        toEnviado = new Date();
//    }
//
//    public void filterByDate() {
//        onFilrarDataInsersao();
//        onFilrarDataValido();
//        onFilrarDataCriacao();
//    }
//
//    private void onFilrarDataInsersao() {
//        Date[] datas = new Date[2];
//
//        if (fromEnviado == null) { //SE AINDA FOR NULL SETA 50 ANOS ATRÁS
//            Calendar calendario = Calendar.getInstance();
//            calendario.add(Calendar.YEAR, -50);
//            datas[0] = calendario.getTime();
//        } else {
//            datas[0] = fromEnviado;
//        }
//
//        datas[1] = toEnviado;
//
//        tabelaArquivos.adicionarFiltro("dataInsercao", datas);
//    }
//
//    private void onFilrarDataValido() {
//        Date[] datas = new Date[2];
//
//        if (fromValido == null) { //SE AINDA FOR NULL SETA 50 ANOS ATRÁS
//            Calendar calendario = Calendar.getInstance();
//            calendario.add(Calendar.YEAR, -50);
//            datas[0] = calendario.getTime();
//        } else {
//            datas[0] = fromValido;
//        }
//
//        if (toValido == null) { //SE AINDA FOR NULL SETA 100 anos na frente
//            Calendar calendario = Calendar.getInstance();
//            calendario.add(Calendar.YEAR, 100);
//            datas[1] = calendario.getTime();
//        } else {
//            datas[1] = toValido;
//        }
//
//        tabelaArquivos.adicionarFiltro("tempoValidade", datas);
//    }
//
//    private void onFilrarDataCriacao() {
//        Date[] datas = new Date[2];
//
//        if (fromData == null) { //SE AINDA FOR NULL SETA 50 ANOS ATRÁS
//            Calendar calendario = Calendar.getInstance();
//            calendario.add(Calendar.YEAR, -50);
//            datas[0] = calendario.getTime();
//        } else {
//            datas[0] = fromData;
//        }
//
//        datas[1] = toData;
//
//        tabelaArquivos.adicionarFiltro("dataCriacao", datas);
//    }
//
//    public Date getFromData() {
//        return fromData;
//    }
//
//    public void setFromData(Date fromData) {
//        this.fromData = getDataInicioDoDia(fromData);
//    }
//
//    public Date getToData() {
//
//        return toData;
//    }
//
//    public void setToData(Date toData) {
//        this.toData = getDataFinalDoDia(toData);
//    }
//
//    public Date getFromValido() {
//        return fromValido;
//    }
//
//    public void setFromValido(Date fromValido) {
//        this.fromValido = getDataInicioDoDia(fromValido);
//    }
//
//    public Date getToValido() {
//        return toValido;
//    }
//
//    public void setToValido(Date toValido) {
//        this.toValido = getDataFinalDoDia(toValido);
//    }
//
//    public Date getFromEnviado() {
//        return fromEnviado;
//    }
//
//    public void setFromEnviado(Date fromEnviado) {
//        this.fromEnviado = getDataInicioDoDia(fromEnviado);
//    }
//
//    public Date getToEnviado() {
//        return toEnviado;
//    }
//
//    public void setToEnviado(Date toEnviado) {
//        this.toEnviado = getDataFinalDoDia(toEnviado);
//    } 
//
//    private Date getDataFinalDoDia(Date data) {
//        if (data == null) {
//            return null;
//        }
//        Calendar calendario = Calendar.getInstance();
//        calendario.setTime(data);
//        calendario.set(Calendar.HOUR, 23);
//        calendario.set(Calendar.MINUTE, calendario.getMaximum(Calendar.MINUTE));
//        calendario.set(Calendar.SECOND, calendario.getMaximum(Calendar.SECOND));
//        calendario.set(Calendar.MILLISECOND, calendario.getMaximum(Calendar.MILLISECOND));
//
//        return calendario.getTime();
//    }
//
//    private Date getDataInicioDoDia(Date data) {
//        if (data == null) {
//            return null;
//        }
//        Calendar calendario = Calendar.getInstance();
//        calendario.setTime(data);
//        calendario.set(Calendar.HOUR, calendario.getMinimum(Calendar.HOUR));
//        calendario.set(Calendar.MINUTE, calendario.getMinimum(Calendar.MINUTE));
//        calendario.set(Calendar.SECOND, calendario.getMinimum(Calendar.SECOND));
//        calendario.set(Calendar.MILLISECOND, calendario.getMinimum(Calendar.MILLISECOND));
//
//        return calendario.getTime();
//    }
//
//}
