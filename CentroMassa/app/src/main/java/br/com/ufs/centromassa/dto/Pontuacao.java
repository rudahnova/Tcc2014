package br.com.ufs.centromassa.dto;

import java.util.Date;

/**
 * Created by Jonas on 14/01/2015.
 */
public class Pontuacao {


    private Long id;

    private Long usuarioID;

    private Integer pontos;

    private Date data;

    public Pontuacao() {
    }

    public Pontuacao(Long usuarioID, Integer pontos) {
        this.usuarioID = usuarioID;
        this.pontos = pontos;
        data = new Date();
    }

    public Pontuacao(Long id, Long usuarioID, Integer pontos, Date data) {
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
    public Integer getPontos() {
        return pontos;
    }

    /**
     * @param pontos the pontos to set
     */
    public void setPontos(Integer pontos) {
        this.pontos = pontos.intValue();
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
