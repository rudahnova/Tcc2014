package br.com.ufs.centromassa.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.com.ufs.centromassa.entity.Pontuacao;
import br.com.ufs.centromassa.entity.Usuario;

/**
 * Created by jon_j on 28/10/2015.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {


    public static final String DATABASE_NAME = "centroMassa.db";

    private static final int DATABASE_VERSION = 3;

    private Dao<Usuario, Long> usuarioDao;
    private Dao<Pontuacao, Long> pontuacaoDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Pontuacao.class);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Pontuacao.class, true);
            TableUtils.dropTable(connectionSource, Usuario.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    public Dao<Usuario, Long> getUsuarioDAO() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Dao<Pontuacao, Long> getPontuacaoDAO() throws SQLException {
        if (pontuacaoDao == null) {
            pontuacaoDao = getDao(Pontuacao.class);
        }
        return pontuacaoDao;
    }


    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        usuarioDao = null;
        pontuacaoDao = null;
    }
}
