/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuário
 */
public class ConnectionFactory {
    public static Connection getConnection(){
        Connection c = null;
        try{
            c = DriverManager.getConnection("jdbc:postgresql://localhost/tcclucasmorialdo", "postgres", "postgres");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}
