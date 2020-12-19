/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Usuário
 */
public class ProdutosAlimentos {
    
    private int idProdutosAlimentos;
    public String nomeProduto;
    public String quantidade;
    public String infoGeral;

    public int getIdProdutosAlimentos() {
        return idProdutosAlimentos;
    }

    public void setIdProdutosAlimentos(int idProdutosAlimentos) {
        this.idProdutosAlimentos = idProdutosAlimentos;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getInfoGeral() {
        return infoGeral;
    }

    public void setInfoGeral(String infoGeral) {
        this.infoGeral = infoGeral;
    }
    
}
