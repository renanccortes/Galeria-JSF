/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.interceptor;

import br.com.galeria.entidades.AcaoDoUsuario;
import br.com.galeria.entidades.Pessoa;
import br.com.galeria.services.ServiceUsuario;
import br.com.galeria.tipos.Acao;
import br.com.galeria.tipos.AreaDoSistema;
import br.com.galeria.util.FacesUtil;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Renan
 */
@ChecarPermissao(acao = Acao.VISUALIZAR, area = AreaDoSistema.CADASTROS_GERAIS)
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ChecarPermissaoInterceptor implements Serializable {

    @Inject
    private LogonMB sessaoMB;

    @EJB(beanName = "UsuarioServiceImpl")
    ServiceUsuario usuarioService;

    public ChecarPermissaoInterceptor() {

    }

    @AroundInvoke
    public Object verificar(InvocationContext context) throws Exception {

        if (!sessaoMB.getUsuarioLogado().isAdministrador()) {
            ChecarPermissao parametros = context.getMethod().getAnnotation(ChecarPermissao.class);

            AreaDoSistema areaDoSistema = parametros.area();
            Acao acaoRequerida = parametros.acao();

            Pessoa pessoa = sessaoMB.getUsuarioLogado();//usuarioService.findOne(sessaoMB.getUsuarioLogado().getIdPessoa());

            if (pessoa.getPermissaoDeUsuario() == null || pessoa.getPermissaoDeUsuario().getPermissoes() == null) {
                FacesUtil.addInfoMessage("Sem permissão para " + acaoRequerida + " na área " + areaDoSistema + ".");
                return null;
            }

            AcaoDoUsuario acoesLiberadas = pessoa.getPermissaoDeUsuario().getPermissoes().get(areaDoSistema);

            if (acoesLiberadas == null) {
                FacesUtil.addInfoMessage("Sem permissão para " + acaoRequerida + " na área " + areaDoSistema + ".");
                return null;
            } else if (!acoesLiberadas.isPermitido(acaoRequerida)) {
                System.out.println("Sem permissão");
                FacesUtil.addInfoMessage("Sem permissão para " + acaoRequerida + " na área " + areaDoSistema + ".");
                return null;
            }
        }

        Object retorno = context.proceed();
        return retorno;
    }
}
