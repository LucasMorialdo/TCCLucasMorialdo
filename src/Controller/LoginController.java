/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ViewManage.TelaAdministradorView;
import ViewManage.TelaFuncionarioView;
import DAO.LoginDAO;
import Model.Login;
import ViewManage.MudancaSenhaView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Usuário
 */
public class LoginController implements Initializable {

    public static Login loginUnv; 

    public Login getLoginUnv() {
        return loginUnv;
    }

    public void setLoginUnv(Login loginUnv) {
        this.loginUnv = loginUnv;
    }
    
    //Aba login
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;
    @FXML
    private Button btLogar;
    @FXML
    private Button btSair;

    private boolean autenticaLogin(Login log) {
        LoginDAO logDAO = new LoginDAO();
        logDAO.selectLogin(log);
        if (logDAO.selectLogin(log)) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void fechar() {
        Stage stage = (Stage) btSair.getScene().getWindow();
        stage.close();
    }

    public void verificaLogin(Login login) throws Exception {
        if (autenticaLogin(login) == true) {
            if (login.tipoperfil.equals("Funcionário padrão")) {
                TelaFuncionarioView telaFuncionarios = new TelaFuncionarioView();
                telaFuncionarios.start(new Stage());
                fechar();
            } else if (login.tipoperfil.equals("Administrador")) {
                TelaAdministradorView telaAdministrador = new TelaAdministradorView();
                telaAdministrador.start(new Stage());
                fechar();
            }
        } else if (autenticaLogin(login) == false) {
            System.out.println(autenticaLogin(login));
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Erro ao logar");
            a.setHeaderText("Informações não encontradas");
            a.showAndWait();
        }
    }

    @FXML
    public void Logar() throws Exception {
        if (txtLogin.getText().equals("") || txtSenha.getText().equals("")
                || txtLogin.getText() == null || txtSenha.getText() == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ATENÇÃO");
            a.setHeaderText("Campos vazios");
            a.setContentText("Por favor, não deixe nenhum campo vazio.");
            a.showAndWait();
        } else {
            Login login = new Login();
            login.setLogin(txtLogin.getText());
            login.setSenha(txtSenha.getText());
            loginUnv = login;
            if (login.getSenha().equals("123123") && autenticaLogin(login)) {
                verificaLogin(login);
                MudancaSenhaView mudaSenha = new MudancaSenhaView();
                mudaSenha.start(new Stage());
            } else {
                verificaLogin(login);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
