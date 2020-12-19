/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.ProdutosFarmacia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Usuário
 */
public class EstoqueFarmaciaDAO {
    
    private static Connection c;
    
    public EstoqueFarmaciaDAO(){
        EstoqueFarmaciaDAO.c = ConnectionFactory.getConnection();
    }
    
    public static void insereFarmacia(ProdutosFarmacia pf) {
        String sql = "INSERT INTO produtosFarmacia(nomeProduto, quantidade, infoGeral)"
                + "VALUES ( ?, ?, ?)";

        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, pf.getNomeProduto());
            stmt.setString(2, pf.getQuantidade());
            stmt.setString(3, pf.getInfoGeral());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean atualizaFarmacia(ProdutosFarmacia pf){
        String sql = "UPDATE produtosFarmacia SET nomeProduto = ?, quantidade = ?, infoGeral = ?"
                + " WHERE idProdutosFarmacia = ?";
        
        try{
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, pf.getNomeProduto());
            stmt.setString(2, pf.getQuantidade());
            stmt.setString(3, pf.getInfoGeral());
            stmt.setInt(4, pf.getIdProdutosFarmacia());
            stmt.execute();
            stmt.close();
            c.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void deletaProdutosFarmacia (ProdutosFarmacia pf){
        String sql = "DELETE FROM produtosFarmacia where idProdutosFarmacia = ?";
        try{
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, pf.getIdProdutosFarmacia());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ObservableList<ProdutosFarmacia> getPF(){
        try{
            ObservableList<ProdutosFarmacia> PF = FXCollections.observableArrayList();
            PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM produtosFarmacia");
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                ProdutosFarmacia produtosF = new ProdutosFarmacia();
                produtosF.setNomeProduto(rs.getString("nomeProduto"));
                produtosF.setQuantidade(rs.getString("quantidade"));
                produtosF.setInfoGeral(rs.getString("infoGeral"));
                produtosF.setIdProdutosFarmacia(rs.getInt("idProdutosFarmacia"));
                
                PF.add(produtosF);
            }
            
            stmt.executeQuery();
            rs.close();
            stmt.close();
            return PF;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
}
