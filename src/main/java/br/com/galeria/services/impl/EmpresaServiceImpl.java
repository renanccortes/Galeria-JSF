/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services.impl;

import br.com.galeria.dao.AbstractDao;
import br.com.galeria.entidades.Empresa;
import br.com.galeria.services.ServiceGenerico;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Renan
 */
@Stateless
@EJB(beanName = "EmpresaServiceImpl", name = "EmpresaServiceImpl", beanInterface = ServiceGenerico.class)
public class EmpresaServiceImpl extends AbstractDao<Empresa> implements ServiceGenerico<Empresa> {
    
    @PersistenceContext(name = "GIDEPU")
    private EntityManager em;
    
    public EmpresaServiceImpl() {
        super(Empresa.class);
        this.entityManager = em;
        
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public Empresa onSalvar(Empresa entidade) {
        if (entidade.getIdEmpresa() == null) {
            entidade = this.save(entidade);
        } else {
            this.update(entidade);
        }
        
        return entidade;
    }
    
    @Override
    public void onExcluir(Empresa entidade) {
        this.delete(entidade);
    }
    
}
