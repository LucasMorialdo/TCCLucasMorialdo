/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.Funcionarios;
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
public class FuncionariosDAO {
    
    private static Connection c;
    
    public FuncionariosDAO(){
        FuncionariosDAO.c = ConnectionFactory.getConnection();
    }
    
    public static void insereFuncionario(Funcionarios fun) {
        String sql = "INSERT INTO Funcionarios(nome, email, senha, tipoPerfil, cpf, telefone1, telefone2, cidade, bairro, rua, numero)"
                + " VALUES(?, ?, MD5(?), ?, ?, ?, ?, ?, ?, ?, ?) ";
        
        try{
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, fun.getNome());
            stmt.setString(2, fun.getEmail());
            stmt.setString(3, fun.getSenha());
            stmt.setString(4, fun.getTipoperfil());
            stmt.setString(5, fun.getCpf());
            stmt.setString(6, fun.getTelefone1());
            stmt.setString(7, fun.getTelefone2());
            stmt.setString(8, fun.getCidade());
            stmt.setString(9, fun.getBairro());
            stmt.setString(10, fun.getRua());
            stmt.setInt(11, fun.getNumero());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean atualizaFuncionarios(Funcionarios fun){
        String sql = "UPDATE funcionarios SET nome = ?, email = ?, senha = ?, tipoPerfil = ?, cpf = ?, telefone1 = ?,"
                + " telefone2 = ?, cidade = ?, bairro = ?, rua = ?, numero = ? WHERE idFuncionarios = ?;";
        
        try{
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, fun.getNome());
            stmt.setString(2, fun.getEmail());
            stmt.setString(3, fun.getSenha());
            stmt.setString(4, fun.getTipoperfil());
            stmt.setString(5, fun.getCpf());
            stmt.setString(6, fun.getTelefone1());
            stmt.setString(7, fun.getTelefone2());
            stmt.setString(8, fun.getCidade());
            stmt.setString(9, fun.getBairro());
            stmt.setString(10, fun.getRua());
            stmt.setInt(11, fun.getNumero());
            stmt.setInt(12, fun.getIdfuncionarios());
            stmt.execute();
            stmt.close();
            c.close();
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void deletaFuncionarios(Funcionarios fun){
        String sql = "DELETE FROM funcionarios WHERE idFuncionarios = ?;";
        try{
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, fun.getIdfuncionarios());
            stmt.execute();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ObservableList<Funcionarios> getList(){
        try{
            ObservableList<Funcionarios> funcionarios = FXCollections.observableArrayList();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM funcionarios");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Funcionarios fun = new Funcionarios();
                fun.setIdfuncionarios(rs.getInt("idfuncionarios"));
                fun.setNome(rs.getString("nome"));
                fun.setEmail(rs.getString("email"));
                fun.setSenha(rs.getString("senha"));
                fun.setTipoperfil(rs.getString("tipoperfil"));
                fun.setCpf(rs.getString("cpf"));
                fun.setTelefone1(rs.getString("telefone1"));
                fun.setTelefone2(rs.getString("telefone2"));
                fun.setCidade(rs.getString("cidade"));
                fun.setBairro(rs.getString("bairro"));
                fun.setRua(rs.getString("rua"));
                fun.setNumero(rs.getInt("numero"));                
                
                funcionarios.add(fun);
            }
            stmt.executeQuery();
            rs.close();
            stmt.close();
            return funcionarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
