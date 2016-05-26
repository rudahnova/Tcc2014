package br.com.ufs.centromassa.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by  on 12/01/2015.
 */
@DatabaseTable(tableName = "usuario")
public class Usuario {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String email;

    @DatabaseField
    private String nome;

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(Long id, String email, String nome) {
        this.id = id;
        this.email = email;
        this.nome = nome;
    }

    public Usuario(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static String ToJSON(Usuario usuario) throws JSONException {
        JSONStringer js = new JSONStringer();

        js.object().key("email").value(usuario.getEmail()).key("nome").value(usuario.getNome());
        if(usuario.getId() != null){
            js.key("id").value(usuario.getId());
        }

        js.endObject();

        return js.toString();
    }

    public static Usuario fromJSON(JSONObject jsonObject) throws JSONException {

        String nome = jsonObject.getString("nome");
        String email = jsonObject.getString("email");
        long id = jsonObject.getLong("id");

        Usuario usuario =new Usuario(id,email,nome);

        return usuario;
    }
}
