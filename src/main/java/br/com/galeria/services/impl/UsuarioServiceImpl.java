/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services.impl;


import br.com.galeria.dao.AbstractDao;
import br.com.galeria.entidades.Login;
import br.com.galeria.entidades.Usuario;
import br.com.galeria.services.ServiceUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Renan
 */
@Stateless
@EJB(beanName = "UsuarioServiceImpl", name = "UsuarioServiceImpl", beanInterface = ServiceUsuario.class)
public class UsuarioServiceImpl extends AbstractDao<Usuario> implements ServiceUsuario {

    @PersistenceContext(name = "PU")
    private EntityManager em;

    public UsuarioServiceImpl() {
        super(Usuario.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Usuario onSalvar(Usuario entidade) {

        byte[] foto = entidade.getFoto();
        if (entidade.getIdPessoa() == null) {
            entidade.setCriado(new Date());
            entidade = this.save(entidade);
        } else {
            entidade.setAtualizado(new Date());
            this.update(entidade);
        }

//        if (foto != null) {
//            try {
//                AwsService aws = new AwsAmazon();
//                aws.conectar();
//                aws.enviarArquivo(entidade.getIdPessoa().toString(), AwsAmazon.PASTA_FOTO_USUARIO, AwsAmazon.BUCKET_ARQUIVOS, foto);
//            } catch (AwsExcpetions ex) {
//                ex.printStackTrace();
//                Logger.getLogger(UsuarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return entidade;
    }

    @Override
    public void onExcluir(Usuario entidade) {
        entidade = this.findOne(entidade.getIdPessoa());
        this.delete(entidade);
    }

    private Path getCaminhosConsulta(String chave, Root generico) {
        StringTokenizer st = new StringTokenizer(chave, ".");
        Path retorno = null;
        while (st.hasMoreElements()) {

            String elemento = st.nextToken();
            if (retorno == null) {

                retorno = generico.get(elemento);
            } else {
                retorno = retorno.get(elemento);
            }
        }
        return retorno;
    }

    @Override
    public Usuario onLogar(Usuario usuario) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();

        Root entidade = query.from(Login.class);
        query.select(entidade);
        Path pathLogin = entidade.get("login");
        List<Predicate> queryPredicates = new ArrayList<>();
        queryPredicates.add(cb.equal(pathLogin, usuario.getLogin().getLogin()));
        query.where(queryPredicates.toArray(new Predicate[]{}));

        TypedQuery<Login> typedQuery = em.createQuery(query);
        try {
            Login loginBanco = typedQuery.getSingleResult();
            if (usuario.getLogin().getSenhaDescrip().equals(loginBanco.getSenhaDescrip())) {
                return loginBanco.getUsuario();
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

}
