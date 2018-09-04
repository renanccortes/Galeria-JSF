/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services.impl;

import br.com.galeria.dao.AbstractDao;
import br.com.galeria.entidades.Locatario;
import br.com.galeria.entidades.Sala;
import br.com.galeria.services.ServiceGenerico;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Renan
 */
@Stateless
@EJB(beanName = "LocatarioServiceImpl", name = "LocatarioServiceImpl", beanInterface = ServiceGenerico.class)
public class LocatarioServiceImpl extends AbstractDao<Locatario> implements ServiceGenerico<Locatario> {

    @PersistenceContext(name = "PU")
    private EntityManager em;

    public LocatarioServiceImpl() {
        super(Locatario.class);
        this.entityManager = em;

    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Locatario onSalvar(Locatario entidade) {
        if (entidade.getId() == null) {
            entidade.setCreated(new Date());
            entidade = this.save(entidade);
        } else {
            entidade.setUpdated(new Date());
            this.update(entidade);
        }

        return entidade;
    }

    @Override
    public void onExcluir(Locatario entidade) {
        this.delete(entidade);
    }

}
