/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerFuncionarios;

import static Controller.ControllerFuncionarios.GerenciamentoFuncionariosController.selecionado;
import DAO.FuncionariosDAO;
import Model.Funcionarios;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Usuário
 */
public class AtualizaCamposFuncionariosController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtTelefone1;
    @FXML
    private TextField txtTelefone2;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtRua;
    @FXML
    private TextField txtNumero;
    @FXML
    private ComboBox cbTipoPerfil;
    @FXML
    private Button btAtualizar;
    @FXML
    private Button btClearForm;

    private ObservableList<String> tipoPerfilcb = FXCollections.observableArrayList("Funcionário padrão", "Administrador");

    @FXML
    private void alterarDados() {
        if (cbTipoPerfil.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("VERIFICAÇÃO DE CAMPOS");
            a.setHeaderText("Campo vazio");
            a.setContentText("Não deixe o campo 'Tipo Perfil' vazio");
            a.showAndWait();
        } else {
            Funcionarios fun = new Funcionarios();
            fun.setNome(txtNome.getText());
            fun.setEmail(txtEmail.getText());
            fun.setTipoperfil(cbTipoPerfil.getValue().toString());
            fun.setCpf(txtCpf.getText());
            fun.setTelefone1(txtTelefone1.getText());
            fun.setTelefone2(txtTelefone2.getText());
            fun.setCidade(txtCidade.getText());
            fun.setBairro(txtBairro.getText());
            fun.setRua(txtRua.getText());
            fun.setNumero(Integer.parseInt(txtNumero.getText()));
            fun.setIdfuncionarios(selecionado.getIdfuncionarios());
            FuncionariosDAO dao = new FuncionariosDAO();
            if (GerenciamentoFuncionariosController.selecionado != null) {
                if (dao.atualizaFuncionarios(fun)) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("VERIFICAÇÃO DE CADASTRO");
                    a.setHeaderText("Funcionário atualizado com sucesso");
                    a.showAndWait();
                    clearForm();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("VERIFICAÇÃO DE CADASTRO");
                    a.setHeaderText("O funcionário não foi atualizado");
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Por favor, selecione um funcionário.");
            }
        }
    }

    @FXML
    private void clearForm() {
        txtNome.clear();
        txtEmail.clear();
        txtCpf.clear();
        txtTelefone1.clear();
        txtTelefone2.clear();
        txtCidade.clear();
        txtBairro.clear();
        txtRua.clear();
        txtNumero.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbTipoPerfil.setItems(tipoPerfilcb);
    }

}
