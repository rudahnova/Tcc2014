/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.persistencia;

import com.j256.ormlite.db.PostgresDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

/**
 *
 * @author Jonas
 */
public class Persistencia {

    private ConnectionSource connectionSource;
    private static Persistencia instance;
    private static final String databaseUrl = "jdbc:postgresql://localhost:5432/CentroMassa";

    private Persistencia() {
        try {
            connectionSource = createConnectionSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ConnectionSource createConnectionSource() throws SQLException {
        return new JdbcConnectionSource(databaseUrl, "postgres", "jonas");
    }

    public static Persistencia instance() {
        if (instance == null) {
            instance = new Persistencia();
        }
        return instance;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}
