/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services.impl;

import br.com.galeria.dao.AbstractDao;
import br.com.galeria.entidades.Piso;
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
@EJB(beanName = "PisoServiceImpl", name = "PisoServiceImpl", beanInterface = ServiceGenerico.class)
public class PisoServiceImpl extends AbstractDao<Piso> implements ServiceGenerico<Piso> {

    @PersistenceContext(name = "PU")
    private EntityManager em;

    public PisoServiceImpl() {
        super(Piso.class);
        this.entityManager = em;

    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Piso onSalvar(Piso entidade) {
        if (entidade.getId() == null) {
            entidade.setCriado(new Date());
            entidade = this.save(entidade);
        } else {
            entidade.setAtualizado(new Date());
            this.update(entidade);
        }

        return entidade;
    }

    @Override
    public void onExcluir(Piso entidade) {
        this.delete(entidade);
    }

}
