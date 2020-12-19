/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerSolicitacoesServico;

import static DAO.SolicitacoesDAO.insereSolicitacoes;
import Model.SolicitacoesServicos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

/**
 *
 * @author Usuário
 */
public class CadastroSolicitacoesController implements Initializable {

    @FXML
    private ComboBox cbTipoSolicitacao;
    @FXML
    private DatePicker dpDataRegistro;
    @FXML
    private TextArea txtareaInfoGeral;
    @FXML
    private Button btCadastrar;
    @FXML
    private Button btSair;
    
    private ObservableList<String> tipoSolicitacao = FXCollections.observableArrayList("Serviços Gerais", "Manutenção", "Compra");

    @FXML
    private void CadastroSolicitacao() {
        if (cbTipoSolicitacao.getValue() == null || dpDataRegistro.getValue() == null || txtareaInfoGeral.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ATENÇÃO");
            a.setHeaderText("Campos vazios");
            a.setContentText("Por favor, não deixe nenhum campo vazio.");
            a.showAndWait();
        } else {
            try {
                
                SolicitacoesServicos solServ = new SolicitacoesServicos();
                solServ.setTipoSolicitacao(cbTipoSolicitacao.getValue().toString());
                solServ.setDataRegistro(dpDataRegistro.getValue());
                solServ.setInfoGeral(txtareaInfoGeral.getText());
                insereSolicitacoes(solServ);
                
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
        cbTipoSolicitacao.setItems(tipoSolicitacao);
    }

}
