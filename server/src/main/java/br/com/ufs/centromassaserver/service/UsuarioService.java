/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.service;

import br.com.ufs.centromassaserver.controle.UsuarioControle;
import br.com.ufs.centromassaserver.persistencia.GenericDAO;
import br.com.ufs.centromassaserver.persistencia.UsuarioDAO;
import br.com.ufs.centromassaserver.entidade.Usuario;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("usuarios")
public class UsuarioService extends GenericService<Usuario> {

    public UsuarioService() throws SQLException {
        super(new UsuarioControle());
    }

    @GET
    @Path("{id}")
    public Response getId(@PathParam("id") Long id) throws Exception {
        if (isValidSession()) {
            if (id != null) {
                Usuario usuario = ((UsuarioControle) getControle()).getById(id);
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Informe o id").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_USUARIO_N_LOGADO).build();
        }
    }
    
    @PUT
    @Path("login")
    public Response login(Usuario usuario) throws Exception {
        if (isValidSession()) {
            if (usuario != null) {
                usuario = ((UsuarioControle) getControle()).login(usuario.getNome(), usuario.getEmail());
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_USUARIO_N_LOGADO).build();
        }
    }
}
