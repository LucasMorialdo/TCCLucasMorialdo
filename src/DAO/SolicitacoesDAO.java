/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.SolicitacoesServicos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Usuário
 */
public class SolicitacoesDAO {
    
    private static Connection c;
    
    public SolicitacoesDAO(){
        this.c = ConnectionFactory.getConnection();
    }
    
    public static void insereSolicitacoes(SolicitacoesServicos solicitacoes){
        
        String sql = "INSERT INTO solicitacoesdeservico (tipoSolicitacao, dataRegistro, infoGeral)"
                + " values (?, ?, ?)";
        
        Date data = Date.valueOf(solicitacoes.getDataRegistro());
        
        try{
            
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, solicitacoes.getTipoSolicitacao());
            stmt.setDate(2, data);
            stmt.setString(3, solicitacoes.getInfoGeral());
            stmt.execute();
            stmt.close();
            c.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public boolean atualizaSolicitacoes(SolicitacoesServicos solicitacoes){
        
        String sql = "UPDATE solicitacoesdeservico SET tiposolicitacao = ?, dataRegistro = ?, infoGeral = ?"
                + " WHERE idsolicitacoes = ?";
        
        try {
            
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, solicitacoes.getTipoSolicitacao());
            stmt.setDate(2, Date.valueOf(solicitacoes.getDataRegistro()));
            stmt.setString(3, solicitacoes.getInfoGeral());
            stmt.setInt(4, solicitacoes.getIdsolicitacoes());
            stmt.execute();
            stmt.close();
            c.close();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public void deletaSolicitacao(SolicitacoesServicos solicitacoes){
        String sql = "DELETE FROM solicitacoesdeservico WHERE idsolicitacoes = ?";
        
        try {
            
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, solicitacoes.getIdsolicitacoes());
            stmt.execute();
            stmt.close();
            c.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ObservableList<SolicitacoesServicos> getSolicitacoes() {
        try {
            
            ObservableList<SolicitacoesServicos> solicitacoes = FXCollections.observableArrayList();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM solicitacoesdeservico");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                SolicitacoesServicos solicitacoesServ = new SolicitacoesServicos();
                
                solicitacoesServ.setTipoSolicitacao(rs.getString("tipoSolicitacao"));
                Date date = rs.getDate("dataRegistro");
                solicitacoesServ.setDataRegistro(date.toLocalDate());
                solicitacoesServ.setInfoGeral(rs.getString("infoGeral"));
                solicitacoesServ.setIdsolicitacoes(rs.getInt("idsolicitacoes"));
                solicitacoes.add(solicitacoesServ);
            }
            stmt.executeQuery();
            rs.close();
            return solicitacoes;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
}
