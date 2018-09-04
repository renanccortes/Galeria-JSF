/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Renan Cortes
 */
public class AjaxSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {

        try {
// chain...
            HttpServletRequest request = (HttpServletRequest) req;

            if (isAjax(request) && !request.isRequestedSessionIdValid()) {

                HttpServletResponse response = (HttpServletResponse) resp;
                response.getWriter().print(xmlPartialRedirectToPage(request, "?session_expired=1"));
                response.flushBuffer();
                System.out.println("session expired ajax...");
            } else {
                chain.doFilter(req, resp);
            }

        } catch (Exception e) {
// redirect to error page
            HttpServletRequest request = (HttpServletRequest) req;
            request.getSession().setAttribute("lastException", e);
            request.getSession().setAttribute("lastExceptionUniqueId", e.hashCode());

            HttpServletResponse response = (HttpServletResponse) resp;

            if (!isAjax(request)) {
                response.sendRedirect(request.getContextPath() + request.getServletPath() + "/error");
            } else {
// let's leverage jsf2 partial response
                response.getWriter().print(xmlPartialRedirectToPage(request, "/error"));
                response.flushBuffer();
            }
        }
    }

    private String xmlPartialRedirectToPage(HttpServletRequest request, String page) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(request.getServletPath()).append(page).append("\"/></partial-response>");
        return sb.toString();
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
