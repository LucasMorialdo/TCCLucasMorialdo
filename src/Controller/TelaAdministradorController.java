/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ViewManage.ControleFuncionarios.GerenciamentoFuncionariosView;
import ViewManage.TelaFuncionarioAcessoAdmView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Usuário
 */
public class TelaAdministradorController implements Initializable {

    @FXML
    private Button btGerenciamentoFuncionarios;
    @FXML
    private Button btFuncoesPrimarias; // Tela do funcionário
    @FXML
    private Button btLogout;
    
    @FXML
    public void abreGerenciamentoFuncionarios() throws Exception {
        GerenciamentoFuncionariosView gerenciamentoFuncionarios = new GerenciamentoFuncionariosView();
        gerenciamentoFuncionarios.start(new Stage());
    }
    
    @FXML
    public void abreFuncoesPrimarias() throws Exception {
        Stage stage = (Stage) btLogout.getScene().getWindow();
        stage.close();
        TelaFuncionarioAcessoAdmView telaF = new TelaFuncionarioAcessoAdmView();
        telaF.start(new Stage());
    }
    
    @FXML
    public void logout() throws Exception {
        Stage stage = (Stage) btLogout.getScene().getWindow();
        stage.close();
        TCCLucasMorialdo loginView = new TCCLucasMorialdo();
        loginView.start(new Stage());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
