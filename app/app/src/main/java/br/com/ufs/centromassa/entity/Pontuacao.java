package br.com.ufs.centromassa.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by  on 14/01/2015.
 */
@DatabaseTable(tableName = "pontuacao")
public class Pontuacao {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, columnName = "id_usuario", foreignAutoRefresh = true)
    private Usuario usuario;

    @DatabaseField(canBeNull = false)
    private Integer pontos;

    @DatabaseField(columnName = "fase", canBeNull = false)
    private Integer fase;

    @DatabaseField(columnName = "data_cadastro", dataType = DataType.DATE_LONG)
    private Date dataCadastro;

    public Pontuacao() {
        super();
    }

    public Pontuacao(Long id, Usuario usuario, Integer pontos, Integer fase, Date dataCadastro) {
        super();
        this.id = id;
        this.usuario = usuario;
        this.pontos = pontos;
        this.fase = fase;
        this.dataCadastro = dataCadastro;
    }

    public Pontuacao(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getFase() {
        return fase;
    }

    public void setFase(Integer fase) {
        this.fase = fase;
    }
}
