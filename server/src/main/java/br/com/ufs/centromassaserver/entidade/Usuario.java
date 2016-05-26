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
 * @author 
 */
@DatabaseTable(tableName = "usuario", daoClass = UsuarioDAO.class)
public class Usuario implements Serializable {

    public static final String FIlD_NOME = "nome";
    public static final String FILD_ID = "id";
    public static final String FILD_EMAIL = "email";

    @DatabaseField(columnName = FILD_ID, generatedId = true)
    private Long id;

    @DatabaseField(columnName = FIlD_NOME, canBeNull = false)
    private String nome;

    @DatabaseField(columnName = FILD_EMAIL, canBeNull = false)
    private String email;

    public Usuario() {
        super();
        this.id = 0l;
    }

    public Usuario(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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
    public String getEmail() {
        return email;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setEmail(String cpf) {
        this.email = cpf;
    }

}
