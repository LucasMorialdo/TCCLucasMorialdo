/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LoginDAO;
import Model.Login;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Usuário
 */
public class MudancaSenhaController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtNovaSenha;
    @FXML
    private PasswordField txtConfirmarSenha;
    @FXML
    private Button btConfirmar;
    @FXML
    private Button btLimparCampos;

    @FXML
    public void MudarSenhaPrimeiroLogin() throws Exception {
        Login login = new Login();
        if (txtEmail.getText().equals(LoginController.loginUnv.getLogin())) {
            if (txtNovaSenha.getText().equals(txtConfirmarSenha.getText())) {
                login.setLogin(txtEmail.getText());
                login.setSenha(txtNovaSenha.getText());
                LoginDAO dao = new LoginDAO();
                dao.updateSenha(login);
                clearForm();
                if (true) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Mudança de senha");
                    a.setHeaderText("Senha alterada com sucesso");
                    a.showAndWait();
                }
            } else {
                System.out.println("As senhas não conferem, favor informar corretamente.");
            }
        } else {
            System.out.println("erro");
            System.out.println(login.getLogin());
        }
    }
    
    @FXML
    void clearForm(){
        txtEmail.clear();
        txtNovaSenha.clear();
        txtConfirmarSenha.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
