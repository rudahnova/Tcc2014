/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.service;

import br.com.ufs.centromassaserver.controle.UsuarioControle;
import br.com.ufs.centromassaserver.entidade.Usuario;
import static br.com.ufs.centromassaserver.service.GenericService.SESSION_USER;
import java.sql.SQLException;
import javax.ejb.Local;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
public abstract class SecurityService {

    @Context
    private static HttpServletRequest request;
    @Context
    private static HttpHeaders httpHeaders;

    public static String SESSION_USER = "user";
    public static String MSG_USUARIO_N_LOGADO = "Usuario não logado.";

    @POST
    @Path("/login")
    public Response login() throws SQLException, Exception {
        if (isValidSession()) {
            String username = httpHeaders.getRequestHeader("username").get(0);
            String password = httpHeaders.getRequestHeader("password").get(0);
            if (username != null && password != null) {
                UsuarioControle controle = new UsuarioControle();
                Usuario usuario = controle.login(username, password);
                if (usuario == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Falha ao fazer login.").build();
                } else {
                    HttpSession session = request.getSession(true);
                    session.setAttribute(SESSION_USER, usuario);
                    return Response.ok(usuario).header("SessionId", session.getId()).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Dados Invalidos.").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Usuario ja logado.").build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() throws Exception {
        getSession().invalidate();
        return Response.status(Response.Status.OK).entity("Logout efetuado.").build();
    }

    protected boolean isValidSession() {
        /*    HttpSession session = request.getSession(false);
        if (session == null) {
        return false;
        } else {
        return true;
        }*/
        return true;
    }

    protected HttpSession getSession() throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new Exception("Usuario não logado");
        } else {
            return session;
        }
    }

    public void setAttibute(String key, Object object) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new Exception("Usuario não logado.");
        }
        session.setAttribute(key, object);
    }

    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

}
