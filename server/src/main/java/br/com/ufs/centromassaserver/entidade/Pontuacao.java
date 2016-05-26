/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.entidade;

import br.com.ufs.centromassaserver.persistencia.PontuacaoDAO;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author
 */
@DatabaseTable(tableName = "pontuacao", daoClass = PontuacaoDAO.class)
public class Pontuacao implements Serializable {

    public static final String FILD_ID = "id";
    public static final String FILD_USUARIO_ID = "id_usuario";
    public static final String FILD_PONTOS = "pontos";
    public static final String FILD_DATA = "data_cadastro";
    public static final String FILD_FASE = "fase";

    @DatabaseField(columnName = FILD_ID, generatedId = true)
    private Long id;

    @DatabaseField(columnName = FILD_USUARIO_ID, canBeNull = false)
    private Long idUsuario;

    @DatabaseField(columnName = FILD_PONTOS, canBeNull = false)
    private Integer pontos;

    @DatabaseField(canBeNull = false)
    private Integer fase;

    @DatabaseField(columnName = FILD_DATA, dataType = DataType.DATE, format = "dd/MM/yyyy HH:mm")
    private Date dataCadastro;

    public Pontuacao() {
    }

    public Pontuacao(Long id, Long usuarioID, Integer pontos, Integer fase, Date data) {
        this.id = id;
        this.idUsuario = usuarioID;
        this.pontos = pontos;
        this.dataCadastro = data;
        this.fase = fase;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idUsuario
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the pontos
     */
    public Integer getPontos() {
        return pontos;
    }

    /**
     * @param pontos the pontos to set
     */
    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    /**
     * @return the dataCadastro
     */
    public Date getDataCadastro() {
        return dataCadastro;
    }

    /**
     * @param dataCadastro the dataCadastro to set
     */
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
