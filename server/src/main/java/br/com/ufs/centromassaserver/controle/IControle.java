/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufs.centromassaserver.controle;

import java.util.List;

/**
 *
 * @author 
 */
public interface IControle<E> {

    public E insert(E object) throws Exception;

    public List<E> getAll() throws Exception;

    public void delete(Number id) throws Exception;

    public void update(E object) throws Exception;
}
