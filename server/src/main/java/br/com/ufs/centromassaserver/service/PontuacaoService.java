/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ufs.centromassaserver.service;

import br.com.ufs.centromassaserver.controle.IControle;
import br.com.ufs.centromassaserver.controle.PontuacaoControle;
import br.com.ufs.centromassaserver.entidade.Pontuacao;
import javax.ws.rs.Path;

/**
 *
 * @author 
 */
@Path("pontuacoes")
public class PontuacaoService extends GenericService<Pontuacao>{

    public PontuacaoService() {
        super(new PontuacaoControle());
    }
    
}
