/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerFarmacia;

import static DAO.EstoqueFarmaciaDAO.insereFarmacia;
import Model.ProdutosFarmacia;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Usuário
 */
public class CadastroFarmaciaController implements Initializable {
    
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private TextField txtInfoGeral;
    @FXML
    private Button btCadastrar;
    @FXML
    private Button btSair;

    @FXML
    private void CadastroFarmacia(){
        if(txtNome.getText().equals("") || txtQuantidade.getText().equals("") || txtInfoGeral.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ATENÇÃO");
            a.setHeaderText("Campos vazios");
            a.setContentText("Por favor, não deixe nenhum campo vazio.");
            a.showAndWait();
        } else {
            try {
                ProdutosFarmacia produtosF = new ProdutosFarmacia();
                produtosF.setNomeProduto(txtNome.getText());
                produtosF.setQuantidade(txtQuantidade.getText());
                produtosF.setInfoGeral(txtInfoGeral.getText());
                insereFarmacia(produtosF);
                
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("VERIFICAÇÃO DE CADASTRO");
                a.setHeaderText("Campos digitados corretamente");
                a.setContentText("Ok, cadastro realizado");
                a.showAndWait();
                
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar. " + e.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    
}
