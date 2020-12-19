/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ViewManage.ControleEstoqueAlimentos.ControleEstoqueAlimentosView;
import ViewManage.ControleAnimais.GerenciamentoAnimaisView;
import ViewManage.ControleEstoqueFarmacia.ControleEstoqueFarmaciaView;
import ViewManage.ControleSolicitacoesServicos.SolicitacoesServicosView;
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
public class TelaFuncionarioController implements Initializable {

    @FXML
    private Button btGerenciamentoDeAnimais;
    @FXML
    private Button btEstoqueAlimento;
    @FXML
    private Button btEstoqueFarmacia;
    @FXML
    private Button btSolicitaServicos;
    @FXML
    private Button btLogout;

    @FXML
    public void abreGerenciamentoAnimais() throws Exception {
        GerenciamentoAnimaisView gerenciamentoAnimais = new GerenciamentoAnimaisView();
        gerenciamentoAnimais.start(new Stage());
    }
    
    @FXML
    public void abreEstoqueAlimentos() throws Exception {
        ControleEstoqueAlimentosView estoqueAlimentos = new ControleEstoqueAlimentosView();
        estoqueAlimentos.start(new Stage());
    }
    
    @FXML
    public void abreEstoqueFarmacia() throws Exception {
        ControleEstoqueFarmaciaView estoqueFarmacia = new ControleEstoqueFarmaciaView();
        estoqueFarmacia.start(new Stage());
    }
    
    @FXML
    public void abreSolicitacoesServicos() throws Exception {
        SolicitacoesServicosView solServ = new SolicitacoesServicosView();
        solServ.start(new Stage());
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
