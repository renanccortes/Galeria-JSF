/*
 * To change This TemplaTe, choose Tools | TemplaTes
 * and open The TemplaTe in The ediTor.
 */
package br.com.galeria.dao;
 
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Named
public abstract class AbstractDao<Entidade> implements IDao<Entidade> {

    public EntityManager entityManager;

    private Class<Entidade> tipoGenerico;
//

    public AbstractDao(Class<Entidade> tipoGenerico) {
        this.tipoGenerico = tipoGenerico;
    }

    protected Class<Entidade> getTipoGenerico() {
        if (tipoGenerico != null) {
            return tipoGenerico;
        }
//       java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Type genericSuperClass = getClass().getGenericSuperclass();

        ParameterizedType parametrizedType = null;

        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }

        return (Class<Entidade>) parametrizedType.getActualTypeArguments()[0];
//        return ((Class<Entidade>) ((ParameterizedType) getClass().
//                getGenericSuperclass()).getActualTypeArguments()[0]);

    }

    @Override
    public Entidade findOne(final Object id) {

        return getEntityManager().find(getTipoGenerico(), id);
    }

    @Override
    public Object findOne(Class classe, final Object id) {

        return getEntityManager().find(classe, id);
    }

    @Override
    public List< Entidade> findAll() {
        EntityManager em = getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Entidade> criteriaQuery = criteriaBuilder.createQuery(getTipoGenerico());
        criteriaQuery.from(getTipoGenerico());
        Query query = em.createQuery(criteriaQuery);
        List<Entidade> entidades = query.getResultList();
//       em.close();
        return entidades;
    }

    @Override
    public Entidade save(final Entidade entidade) throws PersistenceException, IllegalArgumentException {
        EntityManager em = getEntityManager();
        try {
            em.persist(entidade);
        } catch (Exception ex) {
            //transaction.rollback();
            ex.printStackTrace();
            throw ex;

        }
        return entidade;
    }

    @Override
    public void save(final List<Entidade> entidades) throws PersistenceException, IllegalArgumentException {
        if (entidades == null || entidades.isEmpty()) {
            throw new IllegalArgumentException("Lista de entidades é null");
        }

        EntityManager em = getEntityManager();
        try {

            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            for (Entidade entidade : entidades) {

                em.persist(entidade);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void update(final Entidade entity) throws PersistenceException, IllegalArgumentException {

        try {
            getEntityManager().merge(entity);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(final List<Entidade> entidades) throws PersistenceException, IllegalArgumentException {
        if (entidades == null) {
            throw new IllegalArgumentException("Lista de entidades é null");
        }

        EntityManager em = getEntityManager();

        try {

            for (Entidade ent : entidades) {

                em.merge(ent);
            }

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
//           transaction.rollback();           
//          ex.printStackTrace();
        }
    }

//
    @Override
    public void delete(final Entidade entity) {
        EntityManager em = getEntityManager();
        try {

            em.remove(entity);
        } catch (Exception ex) {
            // transaction.rollback();           

            throw ex;
        }
    }

    public void remove(final Long id) {
        EntityManager em = getEntityManager();
        try {
            Entidade find = em.find(getTipoGenerico(), id);
            em.remove(find);
        } catch (Exception ex) {
            // transaction.rollback();           

            throw ex;
        }
    }

    @Override
    public void delete(final List<Entidade> entities) {
        EntityManager em = getEntityManager();
        try {

            for (Entidade e : entities) {

                em.remove(e);

            }

        } catch (Exception ex) {
            //transaction.rollback();        

            throw ex;
        }
    }

    @Override
    public void deleteById(final Long entityId) {
        EntityManager em = getEntityManager();
        try {
            Entidade find = em.find(getTipoGenerico(), entityId);
            em.remove(find);

        } catch (Exception ex) {
            // transaction.rollback();           

            throw ex;
        }

    }

    @Override
    public void atualizarEntidade(Object entidade) {
        try {

            getEntityManager().refresh(entidade);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entidade atualizarEntidades(Object id) {
        try {
            getEntityManager().flush();
            return findOne(id);
        } catch (Exception e) {
            return null;
        }

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityManager getEm() {
        return getEntityManager();
    }

    @Override
    public Entidade buscaUnicaUnicaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR) {

        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        query.distinct(true);
        Root entidade = query.from(getTipoGenerico());
        query.select(entidade);

        TypedQuery<Entidade> typedQuery = formatarQuery(cb, query, entidade, filtrosOperadorAND, filtrosOperadorOR, null, false);
        try {
            Entidade result = typedQuery.getSingleResult();
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<Entidade> buscaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR
    ) {

        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        query.distinct(true);
        Root entidade = query.from(getTipoGenerico());
        query.select(entidade);

        TypedQuery<Entidade> typedQuery = formatarQuery(cb, query, entidade, filtrosOperadorAND, filtrosOperadorOR, null, false);
        List<Entidade> result = typedQuery.getResultList();
        return result;
    }

    @Override
    public List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR
    ) {

        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        query.distinct(true);
        Root entidade = query.from(getTipoGenerico());
        query.select(entidade);

        TypedQuery<Entidade> typedQuery = formatarQuery(cb, query, entidade, filtrosOperadorAND, filtrosOperadorOR, null, false);

        typedQuery.setFirstResult(inicio);
        typedQuery.setMaxResults(fim);
        List<Entidade> result = typedQuery.getResultList();
        return result;
    }

    @Override
    public List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR,
            String sortField,
            boolean ascendingOrder
    ) {

        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        query.distinct(true);
        Root entidade = query.from(getTipoGenerico());
        query.select(entidade);

        TypedQuery<Entidade> typedQuery = formatarQuery(cb, query, entidade, filtrosOperadorAND, filtrosOperadorOR, sortField, ascendingOrder);
        typedQuery.setFirstResult(inicio);
        typedQuery.setMaxResults(fim);

        List<Entidade> result = typedQuery.getResultList();
        return result;
    }

    @Override
    public int countLinhas(Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR
    ) {

        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        query.distinct(true);
        Root entidade = query.from(getTipoGenerico());
        query.select(cb.count(entidade));

        TypedQuery typedQuery = formatarQuery(cb, query, entidade, filtrosOperadorAND, filtrosOperadorOR, null, false);
        int count = ((Long) typedQuery.getSingleResult()).intValue();

        return count;
    }

    private static Predicate isContemNaLista(List<?> campos, CriteriaBuilder cb, Path caminhoDoCampo) {

        Collection<Predicate> restrictions = new ArrayList<>();
        for (Object campo : campos) {
            restrictions.add(cb.isMember(campo, caminhoDoCampo));
        }

        Predicate[] retorno = new Predicate[restrictions.size()];

        restrictions.toArray(retorno);

        return cb.and(retorno);
    }

    private TypedQuery<Entidade> formatarQuery(CriteriaBuilder cb,
            CriteriaQuery query,
            Root entidade,
            Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR, String sortField, boolean ascendingOrder) {

        List<Predicate> condicoesOR = new ArrayList<>();
        List<Predicate> condicoesAND = new ArrayList<>();

        //primeiro itera sobre os filtro operador AND
        for (Map.Entry<String, Object> entry : filtrosOperadorAND.entrySet()) {

            String nomeDoCampo = entry.getKey();
            Object valorCampo = entry.getValue();
            Path caminhoDoCampo = getCaminhosConsulta(nomeDoCampo, entidade);

            if (valorCampo instanceof String) {

                condicoesAND.add(cb.like(cb.lower(caminhoDoCampo), "%" + ((String) valorCampo).toLowerCase() + "%"));
            } else if (valorCampo instanceof Collection || caminhoDoCampo.getJavaType().getName().contains("java.util.List")) {

                Expression<Collection<Object>> collection = caminhoDoCampo;

                if (valorCampo instanceof Collection) {
                    List<?> castCollection = (List<?>) valorCampo;
                    condicoesAND.add(isContemNaLista(castCollection, cb, caminhoDoCampo));
                } else {
                    condicoesAND.add(cb.isMember(valorCampo, collection));
                }

//                Expression<Collection<Object>> collection = caminhoDoCampo;
//                condicoesAND.add(cb.isMember(valorCampo, collection));
            } else if (valorCampo instanceof Date) {
                Date from = (Date) valorCampo;
                Calendar to = Calendar.getInstance();
                to.setTime(from);
                to.add(Calendar.HOUR, 23);
                to.add(Calendar.MINUTE, 59);
                condicoesAND.add(cb.between(caminhoDoCampo, from, to.getTime()));
            } else if (valorCampo instanceof Date[]) {
                Date[] intervalo = (Date[]) valorCampo;
                condicoesAND.add(cb.between(caminhoDoCampo, intervalo[0], intervalo[1]));
            } else {
                condicoesAND.add(cb.equal(caminhoDoCampo, valorCampo));
            }
        }
        //itera sobre os filtro operador OR        
        for (Map.Entry<String, Object[]> entry : filtrosOperadorOR.entrySet()) {

            String nomeDoCampo = entry.getKey();
            Object[] valoresOR = entry.getValue();
            Path caminhoDoCampo = getCaminhosConsulta(nomeDoCampo, entidade);

            for (Object valorCampo : valoresOR) {

                if (valorCampo instanceof String) {

                    condicoesOR.add(cb.like(cb.lower(caminhoDoCampo), "%" + ((String) valorCampo).toLowerCase() + "%"));

                } else if (valorCampo instanceof Collection || caminhoDoCampo.getJavaType().getName().contains("java.util.List")) {

                    Expression<Collection<Object>> collection = caminhoDoCampo;
                    condicoesOR.add(cb.isMember(valorCampo, collection));

                } else if (valorCampo instanceof Date) {

                    Date from = (Date) valorCampo;
                    Calendar to = Calendar.getInstance();
                    to.setTime(from);
                    to.add(Calendar.HOUR, 23);
                    to.add(Calendar.MINUTE, 59);
                    condicoesOR.add(cb.between(caminhoDoCampo, from, to.getTime()));
                } else {
                    condicoesOR.add(cb.equal(caminhoDoCampo, valorCampo));
                }
            }
        }

        if (!condicoesOR.isEmpty()) {
            query.where(cb.and(condicoesAND.toArray(new Predicate[]{})), cb.or(condicoesOR.toArray(new Predicate[]{})));
        } else {
            query.where(cb.and(condicoesAND.toArray(new Predicate[]{})));
        }

        //ordenação pelo campo
        if (sortField != null) {

            if (ascendingOrder) {
                query.orderBy(cb.asc(entidade.get(sortField)));
            } else {
                query.orderBy(cb.desc(entidade.get(sortField)));
            }
        }

        return getEm().createQuery(query);
    }

    private Path getCaminhosConsulta(String chave, Root entidade) {

        StringTokenizer st = new StringTokenizer(chave, ".");
        Path retorno = null;
        while (st.hasMoreElements()) {

            String elemento = st.nextToken();
            if (retorno == null) {
                retorno = entidade.get(elemento);
            } else {
                retorno = retorno.get(elemento);
            }

            if (retorno.getJavaType().getName().contains("java.util.List")) {
                break;
            }

        }

        return retorno;
    }

    public Collection loadLazyRelationship(Object myEntity, String relacionamento) {
        String ejbql
                = "select o." + relacionamento + " from " + myEntity.getClass().getSimpleName()
                + " o LEFT JOIN FETCH o." + relacionamento + " where o = :myEntity";

        Query query = getEntityManager().createQuery(ejbql);
        query.setParameter("myEntity", myEntity);

        Collection l = query.getResultList();

        return l;
    }

}
