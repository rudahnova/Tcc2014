package br.com.ufs.centromassa.control;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import br.com.ufs.centromassa.entity.Usuario;
import br.com.ufs.centromassa.persistence.DataBaseHelper;

/**
 * Created by jon_j on 25/01/2016.
 */
public class UsuarioControl {

    private final DataBaseHelper dataBaseHelper;
    private Dao<Usuario, Long> dao;

    public UsuarioControl(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public Dao<Usuario, Long> getDao() throws SQLException {
        if(dao == null){
            dao = dataBaseHelper.getUsuarioDAO();
        }
        return dao;
    }

    public Usuario insert(Usuario usuario) throws Exception{
        Usuario ifNotExists = getDao().createIfNotExists(usuario);
        //Usuario queryForFirst = getDao().queryBuilder().orderBy("id", false).queryForFirst();
        return ifNotExists;
    }

    public Usuario get(Long id) throws Exception{
        Usuario usuario = getDao().queryBuilder().where().eq("id", id).queryForFirst();
        return usuario;
    }
}
