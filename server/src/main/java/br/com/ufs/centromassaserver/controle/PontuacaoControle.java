/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.controle;

import br.com.ufs.centromassaserver.entidade.Pontuacao;
import br.com.ufs.centromassaserver.persistencia.PontuacaoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 
 */
public class PontuacaoControle implements IControle<Pontuacao> {

    private PontuacaoDAO pontuacaoDAO;

    public PontuacaoDAO getPontuacaoDAO() throws SQLException {
        if (pontuacaoDAO == null) {
            pontuacaoDAO = new PontuacaoDAO(Pontuacao.class);
            return pontuacaoDAO;
        }
        return pontuacaoDAO;
    }

    @Override
    public Pontuacao insert(Pontuacao object) throws Exception {
        synchronized ("insert") {
            getPontuacaoDAO().persist(object);
            return pontuacaoDAO.getLast(Pontuacao.FILD_ID);
        }
    }

    @Override
    public List<Pontuacao> getAll() throws Exception {
        return getPontuacaoDAO().listAll();
    }

    @Override
    public void delete(Number id) throws Exception {
        getPontuacaoDAO().deleteById(id.longValue());
    }

    @Override
    public void update(Pontuacao object) throws Exception {
        getPontuacaoDAO().update(object);
    }

}
