/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services.impl;

/**
 *
 * @author Renan
 */
import br.com.galeria.dao.AbstractDao;
import br.com.galeria.entidades.Banco;
import br.com.galeria.services.ServiceGenerico;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@EJB(beanName = "BancoServiceImpl", name = "BancoServiceImpl", beanInterface = ServiceGenerico.class)
public class BancoServiceImpl extends AbstractDao<Banco> implements ServiceGenerico<Banco> {

    @PersistenceContext(name = "PU")
    private EntityManager em;

    public BancoServiceImpl() {
        super(Banco.class);
        this.entityManager = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Banco onSalvar(Banco entidade) {
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
    public void onExcluir(Banco entidade) {
        super.remove(entidade.getId());
    }

}
