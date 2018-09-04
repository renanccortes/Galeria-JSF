/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 * TODAS CLASSES QUE IMPLEMENTAR ESSA INTERFACE DEVERÁ TER ESTAS FUNÇÕES
 * PADRÕES.
 *
 * @author Renan
 * @param <Entidade>
 */
@Local
public interface ServiceGenerico<Entidade> {

    /**
     * SALVAR GENERICO, SERVE TANTO PARA SALVAR E ATUALIZAR.
     *
     * @param entidade
     * @return
     */
    public Entidade onSalvar(Entidade entidade);

    /**
     * EXCLUIR GENÉRICO, RECEBE A ENTIDADE, É NECESSÁRIO TER O ID DA ENTIDADE
     *
     * @param entidade
     */
    public void onExcluir(Entidade entidade);

    /**
     * RETORNA AS ENTIDADES CONFORME A PÁGINA E OS FILTROS PASSADOS PELA
     * DATATABLE DO PRIMEFACES É USADO NA LazyTableGenerico
     *
     * @param inicio
     * @param fim
     * @param filters
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

    public List<Entidade> findAll();

    public Entidade findOne(Object id);

    public Collection loadLazyRelationship(Object myEntity, String relacionamento);
    
//    public Object getMetodoGenerico();
//    
//    public List<?> getListMetodoGenerico();

}
