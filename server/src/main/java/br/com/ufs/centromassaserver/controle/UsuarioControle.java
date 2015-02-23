/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ufs.centromassaserver.controle;

import br.com.ufs.centromassaserver.entidade.Usuario;
import br.com.ufs.centromassaserver.persistencia.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jonas
 */
public class UsuarioControle implements IControle<Usuario>{
    
    private UsuarioDAO UsuarioDAO;

    private UsuarioDAO getUsuarioDAO() throws SQLException {
        if(UsuarioDAO == null){
            UsuarioDAO = new UsuarioDAO(Usuario.class);
            return UsuarioDAO;
        }else{
            return UsuarioDAO;
        }
    }
    
    public Usuario getById(Long id) throws Exception{
        Usuario usuario = getUsuarioDAO().findById(id);
        if(usuario == null){
            throw new Exception("Usuario não encontrado.");
        }else{
            return usuario;
        }
    }

    @Override
    public Usuario insert(Usuario object) throws Exception {
         synchronized("insert"){
            getUsuarioDAO().persist(object);
            return UsuarioDAO.getLast(Usuario.FILD_ID);
        }
    }

    @Override
    public List<Usuario> getAll() throws Exception {
        return getUsuarioDAO().listAll();
    }

    @Override
    public void delete(Number id) throws Exception {
        int result = getUsuarioDAO().deleteById(id.longValue());
        if(result <= 0){
            throw new Exception("Não foi possivel excluir id:"+id);
        }
    }

    @Override
    public void update(Usuario object) throws Exception {
        int result = getUsuarioDAO().update(object);
        if(result <= 0){
            throw new Exception("Não foi possivel atualizar.");
        }
    }
  
    public Usuario login(String nome, String email) throws SQLException{
        Usuario queryForFirst = getUsuarioDAO().queryBuilder().where().like("email", email).queryForFirst();
        if(queryForFirst != null){
            return queryForFirst;
        }else{
            Usuario u = new Usuario();
            u.setEmail(email);
            u.setNome(nome);
            getUsuarioDAO().create(u);
            queryForFirst = getUsuarioDAO().queryBuilder().where().like("email", email).queryForFirst();
            return queryForFirst;
        }
        
    }
    
}
