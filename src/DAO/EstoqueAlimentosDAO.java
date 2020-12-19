/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.ProdutosAlimentos;
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
public class EstoqueAlimentosDAO {

    private static Connection c;

    public EstoqueAlimentosDAO() {
        EstoqueAlimentosDAO.c = ConnectionFactory.getConnection();
    }

    public static void insereAlimentos(ProdutosAlimentos al) {
        String sql = "INSERT INTO produtosAlimentos(nomeProduto, quantidade, infoGeral)"
                + "VALUES ( ?, ?, ?)";

        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, al.getNomeProduto());
            stmt.setString(2, al.getQuantidade());
            stmt.setString(3, al.getInfoGeral());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean atualizaAlimentos(ProdutosAlimentos al) {
        String sql = "UPDATE produtosAlimentos SET nomeProduto = ?, quantidade = ?, infoGeral = ?"
                + " WHERE idProdutosAlimentos = ?";

        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, al.getNomeProduto());
            stmt.setString(2, al.getQuantidade());
            stmt.setString(3, al.getInfoGeral());
            stmt.setInt(4, al.getIdProdutosAlimentos());
            stmt.execute();
            stmt.close();
            c.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void deletaProdutosAlimentos(ProdutosAlimentos al) {
        String sql = "DELETE FROM produtosAlimentos WHERE idProdutosAlimentos = ?";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, al.getIdProdutosAlimentos());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<ProdutosAlimentos> getPA() {
        try {
            ObservableList<ProdutosAlimentos> PA = FXCollections.observableArrayList();
            PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM produtosAlimentos");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutosAlimentos produtosA = new ProdutosAlimentos();
                produtosA.setNomeProduto(rs.getString("nomeProduto"));
                produtosA.setQuantidade(rs.getString("quantidade"));
                produtosA.setInfoGeral(rs.getString("infoGeral"));
                produtosA.setIdProdutosAlimentos(rs.getInt("idProdutosAlimentos"));
                
                PA.add(produtosA);
            }
            
            stmt.executeQuery();
            rs.close();
            stmt.close();
            return PA;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
