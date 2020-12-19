/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerFarmacia;

import static Controller.ControllerFarmacia.ControleEstoqueFarmaciaController.selecionado;
import DAO.EstoqueFarmaciaDAO;
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
public class AtualizarFarmaciaController implements Initializable {

    @FXML
    private TextField txtNomeProduto;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private TextField txtInfoGeral;
    @FXML
    private Button btAtualizar;
    @FXML
    private Button btSair;

    @FXML
    private void alterarDados() {
        ProdutosFarmacia pf = new ProdutosFarmacia();
        pf.setNomeProduto(txtNomeProduto.getText());
        pf.setQuantidade(txtQuantidade.getText());
        pf.setInfoGeral(txtInfoGeral.getText());
        pf.setIdProdutosFarmacia(selecionado.getIdProdutosFarmacia());
        EstoqueFarmaciaDAO dao = new EstoqueFarmaciaDAO();
        if (ControleEstoqueFarmaciaController.selecionado != null) {
            if (dao.atualizaFarmacia(pf)) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Produto atualizado com sucesso");
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Produto não foi atualizado");
                a.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Por favor, selecione um produto");
            al.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
