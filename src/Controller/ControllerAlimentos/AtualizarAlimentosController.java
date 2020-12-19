/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerAlimentos;

import static Controller.ControllerAlimentos.ControleEstoqueAlimentosController.selecionado;
import DAO.EstoqueAlimentosDAO;
import Model.ProdutosAlimentos;
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
public class AtualizarAlimentosController implements Initializable {

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
        ProdutosAlimentos pa = new ProdutosAlimentos();
        pa.setNomeProduto(txtNomeProduto.getText());
        pa.setQuantidade(txtQuantidade.getText());
        pa.setInfoGeral(txtInfoGeral.getText());
        pa.setIdProdutosAlimentos(selecionado.getIdProdutosAlimentos());
        EstoqueAlimentosDAO dao = new EstoqueAlimentosDAO();
        if (ControleEstoqueAlimentosController.selecionado != null) {
            if (dao.atualizaAlimentos(pa)) {
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
