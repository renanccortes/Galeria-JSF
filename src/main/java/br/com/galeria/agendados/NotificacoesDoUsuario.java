/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.galeria.agendados;

 
import br.com.galeria.entidades.Notificacoes;
import br.com.galeria.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author subversion
 */
public class NotificacoesDoUsuario {
    
    private List<Notificacoes> notificacoes;
    private Usuario usuario;
    private int tipoNotificacao;

    
     
    public List<Notificacoes> getNotificacoes() {
        if(notificacoes == null) notificacoes = new ArrayList<>();
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacoes> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void addNotificacao(Notificacoes notificacao) {
        if(notificacoes == null) notificacoes = new ArrayList<>();
        for(Notificacoes not: notificacoes) {
            if(not.isMesmaNotificacao(notificacao)) return; 
        }
        
        this.getNotificacoes().add(notificacao);
        
    }

    public int getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(int tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.usuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotificacoesDoUsuario other = (NotificacoesDoUsuario) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
