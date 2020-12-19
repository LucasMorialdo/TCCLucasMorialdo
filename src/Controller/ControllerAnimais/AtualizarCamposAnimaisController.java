/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerAnimais;

import static Controller.ControllerAnimais.GerenciamentoAnimaisController.selecionado;
import DAO.AnimaisDAO;
import Model.Animais;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Usuário
 */
public class AtualizarCamposAnimaisController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEspecie;
    @FXML
    private TextField txtRaca;
    @FXML
    private TextField txtPeso;
    @FXML
    private ComboBox cbNecessidades;
    @FXML
    private ComboBox cbTemperamento;
    @FXML
    private ComboBox cbSociabilidade;
    @FXML
    private TextArea txtareaObsGerais;
    @FXML
    private Button btAtualizarDados;
    @FXML
    private Button btLimparCampos;

    private ObservableList<String> necessidades = FXCollections.observableArrayList("Sim", "Não");
    private ObservableList<String> temperamento = FXCollections.observableArrayList("Agressivo", "Dócil", "Companheiro", "Outro");
    private ObservableList<String> sociabilidade = FXCollections.observableArrayList("Alta", "Média", "Baixa");

    @FXML
    private void alterarDados() {
        Animais an = new Animais();
        an.setNome(txtNome.getText());
        an.setEspecie(txtEspecie.getText());
        an.setRaca(txtRaca.getText());
        an.setPeso(txtPeso.getText());
        an.setNecessidadesEspeciais(cbNecessidades.getValue().toString());
        an.setTemperamento(cbTemperamento.getValue().toString());
        an.setSociabilidade(cbSociabilidade.getValue().toString());
        an.setObsGerais(txtareaObsGerais.getText());
        an.setIdAnimais(selecionado.getIdAnimais());
        AnimaisDAO dao = new AnimaisDAO();
        if (GerenciamentoAnimaisController.selecionado != null) {
            if (dao.atualizaAnimais(an)) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Animal atualizado");
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Animal não foi atualizado");
                a.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Por favor, selecione um animal");
            al.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbNecessidades.setItems(necessidades);
        cbTemperamento.setItems(temperamento);
        cbSociabilidade.setItems(sociabilidade);
    }

}
