/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ufs.centromassaserver.persistencia;

import br.com.ufs.centromassaserver.entidade.Usuario;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;

/**
 *
 * @author Jonas
 */
public class UsuarioDAO extends GenericDAO<Usuario, Long>{

    public UsuarioDAO(Class<Usuario> dataClass) throws SQLException {
        super(Persistencia.instance().getConnectionSource(), Usuario.class);
    }
    
    public Usuario findByNomeCpf(String nome, String cpf) throws SQLException{
        QueryBuilder<Usuario, Long> builder = queryBuilder();
        PreparedQuery<Usuario> preparedQuery = builder.where().like(Usuario.FIlD_NOME, nome).and().like(Usuario.FILD_EMAIL, cpf).prepare();
        return singleResult(preparedQuery);
    }
}
