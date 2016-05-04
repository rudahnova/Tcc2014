package br.com.ufs.centromassa.control;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.ufs.centromassa.entity.Pontuacao;
import br.com.ufs.centromassa.persistence.DataBaseHelper;

/**
 * Created by jon_j on 25/01/2016.
 */
public class PontuacaoControl {

    private Dao<Pontuacao, Long> dao;
    private final DataBaseHelper dataBaseHelper;

    public PontuacaoControl(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public Dao<Pontuacao, Long> getDao() throws SQLException {
        if(dao == null){
            dao = dataBaseHelper.getPontuacaoDAO();
        }
        return dao;
    }

    public Pontuacao insert(Pontuacao pontuacao) throws Exception{
        pontuacao.setDataCadastro(new Date());
        getDao().create(pontuacao);
        Pontuacao first = getDao().queryBuilder().orderBy("id", false).queryForFirst();
        return first;
    }

    public List<Pontuacao> getRank() throws Exception{
        QueryBuilder<Pontuacao, Long> builder = getDao().queryBuilder();
        List<Pontuacao> query = builder.orderBy("pontos", false).query();
        return query;
    }

    public List<Pontuacao> get(long idusuario) throws SQLException {
        return getDao().queryBuilder().where().eq("id_usuario", idusuario).query();
    }


}
