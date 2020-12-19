/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;


/**
 *
 * @author Usuário
 */
public class SolicitacoesServicos {
    
    private int idsolicitacoes;
    public String tipoSolicitacao;
    public LocalDate dataRegistro;
    public String infoGeral;

    public int getIdsolicitacoes() {
        return idsolicitacoes;
    }

    public void setIdsolicitacoes(int idsolicitacoes) {
        this.idsolicitacoes = idsolicitacoes;
    }

    public String getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(String tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getInfoGeral() {
        return infoGeral;
    }

    public void setInfoGeral(String infoGeral) {
        this.infoGeral = infoGeral;
    }
    
    
    
}
