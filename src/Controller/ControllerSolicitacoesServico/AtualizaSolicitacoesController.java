/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerSolicitacoesServico;

import static Controller.ControllerSolicitacoesServico.SolicitacoesServicosController.selecionado;
import DAO.SolicitacoesDAO;
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
public class AtualizaSolicitacoesController implements Initializable  {

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
    private void alterarDados(){
        SolicitacoesServicos solServ = new SolicitacoesServicos();
        solServ.setTipoSolicitacao(cbTipoSolicitacao.getValue().toString());
        solServ.setDataRegistro(dpDataRegistro.getValue());
        solServ.setInfoGeral(txtareaInfoGeral.getText());
        solServ.setIdsolicitacoes(selecionado.getIdsolicitacoes());
        SolicitacoesDAO dao = new SolicitacoesDAO();
        if(SolicitacoesServicosController.selecionado != null) {
            if(dao.atualizaSolicitacoes(solServ)) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Solicitação atualizada com sucesso");
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Solicitação não foi atualizado");
                a.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Por favor, selecione uma solicitação");
            al.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbTipoSolicitacao.setItems(tipoSolicitacao);
    }
      
}
