/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.Notificacoes;
import br.com.galeria.services.ServiceGenerico;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import javax.inject.Named;

/**
 *
 * @author miche
 */
//@ManagedBean
//@ViewScoped //verificar com Renan sobre a anotação ser sessionscoped 
@Named
@ViewScoped
public class NotificacaoMB implements Serializable {

    @EJB(beanName = "NotificacaoServiceImpl")
    private ServiceGenerico<Notificacoes> notificacaoService;
    private Notificacoes notificacao;
    private List<Notificacoes> listaNotificacao;

    @PostConstruct
    private void init() {
        onCarregarNotificacoes();
    }

    public void onCarregarNotificacoes() {
        Map<String, Object[]> filtros = new HashMap<>();
        Calendar dataFiltros = Calendar.getInstance();
        dataFiltros.setTime(new Date());

        Object[] data = new Object[2];
        data[0] = dataFiltros.getTime();
        dataFiltros.add(Calendar.DAY_OF_MONTH, -1);
        data[1] = dataFiltros.getTime();
        filtros.put("dataMudancaDestino", data);
        listaNotificacao = notificacaoService.buscaPaginada(0, 100, new HashMap<>(), filtros);
    }

    public void onVisualizar() {
        onCarregarNotificacoes();
    }

    public void onExcluir() {

    }

    public ServiceGenerico<Notificacoes> getNotificacaoService() {
        return notificacaoService;
    }

    public void setNotificacaoService(ServiceGenerico<Notificacoes> notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    public Notificacoes getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacoes notificacao) {
        this.notificacao = notificacao;
    }

    public List<Notificacoes> getListaNotificacao() {
        return listaNotificacao;
    }

    public void setListaNotificacao(List<Notificacoes> listaNotificacao) {
        this.listaNotificacao = listaNotificacao;
    }

}
