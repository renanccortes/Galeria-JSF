/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.PersistenceException;

/**
 *
 * @author Renan
 */
public interface IDao<Entidade> extends Serializable {

    Entidade findOne(final Object id);

    List<Entidade> findAll();

    /**
     * Busca todos filtrando alguns atributos
     *
     * @param fields atributos da entidade, nome do campo/tipo do campo
     * @return
     */
    List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR);

    List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR,
            String sortField,
            boolean ascendingOrder);

    int countLinhas(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

    Entidade buscaUnicaUnicaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

    List<Entidade> buscaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

    Entidade save(final Entidade entity) throws PersistenceException, IllegalArgumentException;

    void save(List<Entidade> entidades) throws PersistenceException, IllegalArgumentException;

    void update(final Entidade entity);

    void delete(final Entidade entity);

    void delete(final List<Entidade> entity);

    void deleteById(final Long entityId);

    public void update(final List<Entidade> entidades);

    public void atualizarEntidade(Object entidade);

    public Object findOne(Class classe, final Object id);

    public Collection loadLazyRelationship(Object myEntity, String relacionamento);

}
