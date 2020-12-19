/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Usuário
 */
public class LoginDAO {

    private static Connection c;

    public LoginDAO() {
        LoginDAO.c = ConnectionFactory.getConnection();
    }

    public boolean selectLogin(Login log) {
        boolean status = false;
        try {
            PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM funcionarios where email = ? and senha = MD5(?)");

            stmt.setString(1, log.getLogin());
            stmt.setString(2, log.getSenha());

            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                log.setTipoperfil(rs.getString("tipoperfil"));
                status = true;
            }
                  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }   
        return status;
    }
    
    public boolean autenticaAdm(Login log){
        try{
            PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM Funcionarios where email = ? and senha = MD5(?)");
            
            stmt.setString(1, log.getLogin());
            stmt.setString(2, log.getSenha());
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                log.setTipoperfil(rs.getString("tipoPerfil"));
            }
            
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean updateSenha(Login log){
        boolean status = false;
        try{
            PreparedStatement stmt = this.c.prepareStatement("UPDATE funcionarios SET senha = MD5(?) WHERE email = ?");
            
            stmt.setString(1, log.getSenha());
            stmt.setString(2, log.getLogin());
            
            ResultSet rs = stmt.executeQuery();
            
            stmt.close();
            c.close();
            
            status = true;
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return status;
    }
    
    

}
