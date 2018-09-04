/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.filtros;

import br.com.galeria.tipos.TipoUsuario;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.IOException;
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
 * @author subversion
 */
//@WebFilter(filterName="acessoMasterFilter", urlPatterns= {"/adm/*"})
public class AcessoMasterFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            LogonMB sessaoMB = (LogonMB) session.getAttribute("logonMB");

            if (sessaoMB == null || sessaoMB.getUsuarioLogado() == null || sessaoMB.getUsuarioLogado().getTipoUsuario() != TipoUsuario.USUARIO_MASTER) {
                request.getServletContext().getRequestDispatcher("/index.ged").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
          
            request.getServletContext().getRequestDispatcher("/index.ged").forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }

}
