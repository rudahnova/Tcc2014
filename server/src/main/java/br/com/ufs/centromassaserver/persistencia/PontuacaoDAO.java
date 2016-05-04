/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ufs.centromassaserver.persistencia;

import br.com.ufs.centromassaserver.entidade.Pontuacao;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class PontuacaoDAO extends GenericDAO<Pontuacao, Long>{

    public PontuacaoDAO(Class<Pontuacao> dataClass) throws SQLException {
        super(Persistencia.instance().getConnectionSource(), Pontuacao.class);
    }
    
}
