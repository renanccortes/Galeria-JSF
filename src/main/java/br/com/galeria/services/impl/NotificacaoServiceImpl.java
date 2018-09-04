/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services.impl;

import br.com.galeria.dao.AbstractDao;
import br.com.galeria.entidades.Notificacoes;
import br.com.galeria.services.ServiceGenerico;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author miche
 */
@Stateless
@EJB(beanName = "NotificacaoServiceImpl", name = "NotificacaoServiceImpl", beanInterface = ServiceGenerico.class)
public class NotificacaoServiceImpl extends AbstractDao<Notificacoes> implements ServiceGenerico<Notificacoes>, Job {

    @PersistenceContext(name = "PU")
    private EntityManager em;

    public NotificacaoServiceImpl() {
        super(Notificacoes.class);
        this.entityManager = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Notificacoes onSalvar(Notificacoes entidade) {
        if (entidade.getIdNotificacoes() == null) {
            entidade = this.save(entidade);
        } else {
            this.update(entidade);
        }
        return entidade;
    }

    @Override
    public void onExcluir(Notificacoes entidade) {
        entidade = this.findOne(entidade.getIdNotificacoes());
        this.delete(entidade);
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
