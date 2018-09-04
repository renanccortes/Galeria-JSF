/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.galeria.filtros;
 

import br.com.galeria.dao.AbstractDao;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Renan Cortes
 */
public class HibernateSessionFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
   
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
   
        try{
            chain.doFilter(request, response);
        }finally{
//             EntityManager em = AbstractDao.getEntityManager(false);
//             if(em!=null && em.isOpen()){
//                 em.clear();
//                 em.close();
//             }
        }
        
    }

    @Override
    public void destroy() {
   
    }
    
}
