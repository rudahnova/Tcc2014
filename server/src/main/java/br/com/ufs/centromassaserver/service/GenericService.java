/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.service;

import br.com.ufs.centromassaserver.controle.IControle;
import br.com.ufs.centromassaserver.controle.UsuarioControle;
import br.com.ufs.centromassaserver.entidade.Usuario;
import com.sun.net.httpserver.HttpServer;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public abstract class GenericService<E> extends SecurityService {

    private IControle<E> controle;

    public IControle<E> getControle() {
        return controle;
    }

    public GenericService(IControle<E> controle) {
        this.controle = controle;
    }

    @GET
    public Response getAll() {
        if (isValidSession()) {
            try {
                return Response.ok(controle.getAll()).build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_USUARIO_N_LOGADO).build();
        }
    }

    @PUT
    public Response put(E object) {
        if (isValidSession()) {
            if (object != null) {
                try {
                    return Response.ok(controle.insert(object)).build();
                } catch (Exception e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Informe os dados.").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_USUARIO_N_LOGADO).build();
        }
    }

    @POST
    public Response post(E object) {
        if (isValidSession()) {
            if (object != null) {
                try {
                    controle.update(object);
                    return Response.ok().build();
                } catch (Exception e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Informe os dados.").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_USUARIO_N_LOGADO).build();
        }

    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        if (isValidSession()) {
            if (id != null) {
                try {
                    controle.delete(id);
                    return Response.ok().build();
                } catch (Exception e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Informe o id.").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(MSG_USUARIO_N_LOGADO).build();
        }
    }

}
