/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.Animais;
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
public class AnimaisDAO {
  
    private static Connection c;
    
    public AnimaisDAO(){
        this.c = ConnectionFactory.getConnection();
    }
    
    public static void insereAnimais (Animais an){
        String sql = "INSERT INTO animais(nome, especie, raca, peso, necessidadesEspeciais, temperamento, sociabilidade, obsGerais)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
            
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, an.getNome());
            stmt.setString(2, an.getEspecie());
            stmt.setString(3, an.getRaca());
            stmt.setString(4, an.getPeso());
            stmt.setString(5, an.getNecessidadesEspeciais());
            stmt.setString(6, an.getTemperamento());
            stmt.setString(7, an.getSociabilidade());
            stmt.setString(8, an.getObsGerais());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean atualizaAnimais(Animais an){
        
        String sql = "UPDATE animais SET nome = ?, especie = ?, raca = ?, peso = ?, necessidadesEspeciais = ?,"
                + " temperamento = ?, sociabilidade = ?, obsGerais = ? WHERE idAnimais = ?";
        
        try{
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, an.getNome());
            stmt.setString(2, an.getEspecie());
            stmt.setString(3, an.getRaca());
            stmt.setString(4, an.getPeso());
            stmt.setString(5, an.getNecessidadesEspeciais());
            stmt.setString(6, an.getTemperamento());
            stmt.setString(7, an.getSociabilidade());
            stmt.setString(8, an.getObsGerais());
            stmt.setInt(9, an.getIdAnimais());
            stmt.execute();
            stmt.close();
            c.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public void deletaAnimais(Animais an){
        
        String sql = "DELETE FROM animais where idAnimais = ?";
        try{
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, an.getIdAnimais());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public ObservableList<Animais> getAnimais(){
        try{
            ObservableList<Animais> ans = FXCollections.observableArrayList();
            PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM animais");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                Animais an = new Animais();
                an.setIdAnimais(rs.getInt("idAnimais"));
                an.setNome(rs.getString("nome"));
                an.setEspecie(rs.getString("especie"));
                an.setRaca(rs.getString("raca"));
                an.setPeso(rs.getString("peso"));
                an.setNecessidadesEspeciais(rs.getString("necessidadesEspeciais"));
                an.setTemperamento(rs.getString("temperamento"));
                an.setSociabilidade(rs.getString("sociabilidade"));
                an.setObsGerais(rs.getString("obsGerais"));
                an.setIdAnimais(rs.getInt("idAnimais"));
                
                ans.add(an);
            }
            
            stmt.executeQuery();
            rs.close();
            stmt.close();
            return ans;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
}
