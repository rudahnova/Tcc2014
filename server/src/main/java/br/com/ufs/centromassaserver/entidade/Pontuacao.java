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
@DatabaseTable(tableName = "common.pontuacao", daoClass = PontuacaoDAO.class)
public class Pontuacao implements Serializable{
    
    public static final String FILD_ID = "id";
    public static final  String FILD_USUARIO_ID = "usuario_id";
    public static final String FILD_PONTOS = "pontos";
    public static final String FILD_DATA = "data";
    
    @DatabaseField(columnName = FILD_ID, generatedIdSequence = "common.pontuacao_id_seq")
    private Long id;
    
    @DatabaseField(columnName = FILD_USUARIO_ID, canBeNull = false)
    private Long usuarioID;
    
    @DatabaseField(columnName = FILD_PONTOS, canBeNull = false)
    private Double pontos;
    
    @DatabaseField(columnName = FILD_DATA, dataType = DataType.DATE, format = "dd/MM/yyyy")
    private Date data;

    public Pontuacao() {
    }

    public Pontuacao(Long id, Long usuarioID, Double pontos, Date data) {
        this.id = id;
        this.usuarioID = usuarioID;
        this.pontos = pontos;
        this.data = data;
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
     * @return the usuarioID
     */
    public Long getUsuarioID() {
        return usuarioID;
    }

    /**
     * @param usuarioID the usuarioID to set
     */
    public void setUsuarioID(Long usuarioID) {
        this.usuarioID = usuarioID;
    }

    /**
     * @return the pontos
     */
    public Double getPontos() {
        return pontos;
    }

    /**
     * @param pontos the pontos to set
     */
    public void setPontos(Double pontos) {
        this.pontos = pontos;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }
    
    
}
