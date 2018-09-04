/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.filtros;

import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Renan
 *
 * this diverts to home page any attempt to access template folder pages
 */
//@WebFilter(filterName="acessoFilter", urlPatterns= {"/sistema/*"})
public class AcessoFilter implements Filter {

    @Inject
    LogonMB sessaoMB;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {

            if (sessaoMB == null || sessaoMB.getUsuarioLogado() == null) {

                request.getServletContext().getRequestDispatcher("/index.ged").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getServletContext().getRequestDispatcher("/index.ged").forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }

}
