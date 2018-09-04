/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.controle;

import br.com.galeria.entidades.Usuario;
import br.com.galeria.services.ServiceUsuario;
import br.com.galeria.tipos.TipoSexo;
import br.com.galeria.tipos.TipoUsuario;
import br.com.galeria.util.FacesMensagensUtil;
import br.com.galeria.util.FacesUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Renan
 */
//@ManagedBean
//@ViewScoped
@Named
@ViewScoped
public class UsuariosMasterMB implements Serializable {

    @EJB(beanName = "UsuarioServiceImpl")
    ServiceUsuario usuarioService;

    private List<Usuario> usuariosCadastrados;
    private Usuario usuario;
    private final String AREA = "Usuario";

    @PostConstruct
    private void init() {
        usuario = new Usuario();
        Map<String, Object> filtro = new HashMap<>();
        filtro.put("tipoUsuario", TipoUsuario.USUARIO_MASTER);
        usuariosCadastrados = usuarioService.buscaComParametros(filtro, new HashMap<>());
    }

    public TipoSexo[] tipoSexo() {
        return TipoSexo.values();
    }

    public TipoUsuario[] tipoUsuario() {
        return TipoUsuario.values();
    }

    public void onNovo() {
        usuario = new Usuario();
    }

    public void onSalvar() {

        boolean editar = usuario.getIdPessoa() != null;

        try {
            usuarioService.onSalvar(usuario);
            usuariosCadastrados = usuarioService.findAll();
            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);
            FacesUtil.mostraDialog("cadastroUsuarios", false);
        } catch (Exception e) {
            e.printStackTrace();
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    public void onExcluir() {
        try {
            usuarioService.onExcluir(usuario);
            usuariosCadastrados = usuarioService.findAll();
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        } catch (Exception e) {
            e.printStackTrace();
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, false);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean ativaBotoes() {
        return usuario != null && usuario.getIdPessoa() != null;
    }

    public List<Usuario> getUsuariosCadastrados() {
        return usuariosCadastrados;
    }

    public void setUsuariosCadastrados(List<Usuario> usuariosCadastrados) {
        this.usuariosCadastrados = usuariosCadastrados;
    }

}
