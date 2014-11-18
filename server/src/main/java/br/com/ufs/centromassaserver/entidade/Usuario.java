/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.entidade;

import br.com.ufs.centromassaserver.persistencia.UsuarioDAO;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

/**
 *
 * @author Jonas
 */
@DatabaseTable(tableName = "common.usuario", daoClass = UsuarioDAO.class)
public class Usuario implements Serializable{

    public static final String FIlD_NOME = "nome";
    public static final String FILD_ID = "id";
    public static final String FILD_CPF = "cpf";

    @DatabaseField(columnName = FILD_ID, generatedIdSequence = "common.usuario_id_seq")
    private Long id;

    @DatabaseField(columnName = FIlD_NOME, canBeNull = false)
    private String nome;

    @DatabaseField(columnName = FILD_CPF, canBeNull = false)
    private String cpf;

    public Usuario() {
        super();
        this.id = 0l;
    }

    public Usuario(Long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    

}
