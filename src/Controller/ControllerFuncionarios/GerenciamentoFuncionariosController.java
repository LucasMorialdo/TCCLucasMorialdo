/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerFuncionarios;

import DAO.FuncionariosDAO;
import static DAO.FuncionariosDAO.insereFuncionario;
import Model.Funcionarios;
import ViewManage.ControleFuncionarios.AtualizarCamposFuncionariosView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import static java.lang.Long.parseLong;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Usuário
 */
public class GerenciamentoFuncionariosController implements Initializable {

    // Aba Cadastro
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
    private Button btCadastrar;
    @FXML
    private Button btLimpar;

    // Aba Listagem
    @FXML
    private TextField txtPesquisa;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btAlterarDados;
    @FXML
    private Button btAtualizaTabela;
    @FXML
    private Button btPesquisar;
    @FXML
    private ComboBox cbPesquisar;
    @FXML
    private Button btGerarPdf;

    //TABELAS
    @FXML
    TableView<Funcionarios> tabelaID;
    @FXML
    TableColumn<Funcionarios, Integer> colunaID;
    @FXML
    TableColumn<Funcionarios, String> colunaNome;
    @FXML
    TableColumn<Funcionarios, String> colunaEmail;
    @FXML
    TableColumn<Funcionarios, String> colunaTipoPerfil;
    @FXML
    TableColumn<Funcionarios, String> colunaCpf;
    @FXML
    TableColumn<Funcionarios, String> colunaTelefone1;
    @FXML
    TableColumn<Funcionarios, String> colunaTelefone2;
    @FXML
    TableColumn<Funcionarios, String> colunaCidade;
    @FXML
    TableColumn<Funcionarios, String> colunaBairro;
    @FXML
    TableColumn<Funcionarios, String> colunaRua;
    @FXML
    TableColumn<Funcionarios, Integer> colunaNumero;

    private ObservableList<Funcionarios> funcionarios = FXCollections.observableArrayList();
    static Funcionarios selecionado;
    private ObservableList<String> tipoPerfil = FXCollections.observableArrayList("Funcionário padrão", "Administrador");
    private ObservableList<String> definirComboBox = FXCollections.observableArrayList("ID", "Nome", "Email", "Tipo de perfil", "CPF", "Telefone 1",
            "Telefone 2", "Cidade", "Bairro", "Rua", "Número");

    @FXML
    public void preencheTabela() {
        colunaID.setCellValueFactory(new PropertyValueFactory("idfuncionarios"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colunaTipoPerfil.setCellValueFactory(new PropertyValueFactory("tipoperfil"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colunaTelefone1.setCellValueFactory(new PropertyValueFactory("telefone1"));
        colunaTelefone2.setCellValueFactory(new PropertyValueFactory("telefone2"));
        colunaCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
        colunaBairro.setCellValueFactory(new PropertyValueFactory("bairro"));
        colunaRua.setCellValueFactory(new PropertyValueFactory("rua"));
        colunaNumero.setCellValueFactory(new PropertyValueFactory("numero"));

        FuncionariosDAO dao = new FuncionariosDAO();
        funcionarios = dao.getList();
        tabelaID.setItems(funcionarios);
    }

    @FXML
    private void CadastroFuncionarios() {
        if (txtNome.getText().equals("") || txtEmail.getText().equals("")
                || cbTipoPerfil.getValue() == null || txtCpf.getText().equals("")
                || txtTelefone1.getText().equals("") || txtCidade.getText().equals("")
                || txtBairro.getText().equals("") || txtRua.getText().equals("")
                || txtNumero.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ATENÇÃO");
            a.setHeaderText("Campos vazios");
            a.setContentText("Por favor, não deixe nenhum campo vazio.");
            a.showAndWait();
        } else {
            try {
                Funcionarios funcs = new Funcionarios();
                funcs.setNome(txtNome.getText());
                funcs.setEmail(txtEmail.getText());
                funcs.setTipoperfil(cbTipoPerfil.getValue().toString());
                funcs.setCpf(txtCpf.getText());
                funcs.setTelefone1(txtTelefone1.getText());
                funcs.setTelefone2(txtTelefone2.getText());
                funcs.setCidade(txtCidade.getText());
                funcs.setBairro(txtBairro.getText());
                funcs.setRua(txtRua.getText());
                funcs.setNumero(Integer.parseInt(txtNumero.getText()));
                funcs.setSenha("123123");
                insereFuncionario(funcs);

                preencheTabela();
                clearForm();

                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("VERIFICAÇÃO DE CADASTRO");
                a.setHeaderText("Cadastro Realizado");
                a.setContentText("Usuário cadastrado com sucesso");
                a.showAndWait();
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar. " + e.getMessage());
            }
        }
    }

    @FXML
    public void deletaFuncionarios() throws Exception {
        if (GerenciamentoFuncionariosController.selecionado != null) {
            FuncionariosDAO dao = new FuncionariosDAO();
            dao.deletaFuncionarios(selecionado);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Funcionário deletado com sucesso");
            a.showAndWait();
            preencheTabela();
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Por favor, selecione um funcionário");
            al.showAndWait();
        }
    }

    @FXML
    public void abreAtualizaDados() throws Exception {
        AtualizarCamposFuncionariosView attCampos = new AtualizarCamposFuncionariosView();
        attCampos.start(new Stage());
    }

    @FXML
    void clearForm() {
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
        cbTipoPerfil.setItems(tipoPerfil);
        cbPesquisar.setItems(definirComboBox);
        preencheTabela();

        tabelaID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    GerenciamentoFuncionariosController.selecionado = (Funcionarios) newValue;
                } else {
                    GerenciamentoFuncionariosController.selecionado = null;
                }
            }
        });
        
        btPesquisar.setOnMouseClicked((MouseEvent e) -> {
            tabelaID.setItems(pesquisar());
        });
        
    }

    private ObservableList<Funcionarios> pesquisar() {
        if (cbPesquisar.getValue().equals("ID")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getIdfuncionarios() == parseLong(txtPesquisa.getText())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Nome")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getNome().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Email")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getEmail().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Tipo de perfil")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getTipoperfil().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("CPF")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getCpf().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Telefone 1")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getTelefone1().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Telefone 2")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getTelefone2().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Cidade")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getCidade().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Bairro")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getBairro().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Rua")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getRua().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        } else if (cbPesquisar.getValue().equals("Número")) {
            ObservableList<Funcionarios> funcionariosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < funcionarios.size(); x++) {
                if (funcionarios.get(x).getNumero() == parseLong(txtPesquisa.getText())) {
                    funcionariosPesquisa.add(funcionarios.get(x));
                }
            }
            return funcionariosPesquisa;
        }
        return null;
    }
    
    @FXML
    private void gerarPdf() {
        Document doc = new Document();
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fs.showSaveDialog(new Stage());
        FuncionariosDAO dao = new FuncionariosDAO();
        ObservableList<Funcionarios> pdf = dao.getList();

        if (file != null) {
            try {
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GerenciamentoFuncionariosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(GerenciamentoFuncionariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            doc.open();
            for (int i = 0; i < pdf.size(); i++) {
                try {
                    doc.add(new Paragraph("ID: " + pdf.get(i).getIdfuncionarios()));
                    doc.add(new Paragraph("Nome do funcionário: " + pdf.get(i).getNome()));
                    doc.add(new Paragraph("Email: " + pdf.get(i).getEmail()));
                    doc.add(new Paragraph("Tipo do perfil: " + pdf.get(i).getTipoperfil()));
                    doc.add(new Paragraph("CPF: " + pdf.get(i).getCpf()));
                    doc.add(new Paragraph("Telefone 1: " + pdf.get(i).getTelefone1()));
                    doc.add(new Paragraph("Telefone 2: " + pdf.get(i).getTelefone2()));
                    doc.add(new Paragraph("Cidade: " + pdf.get(i).getCidade()));
                    doc.add(new Paragraph("Bairro: " + pdf.get(i).getBairro()));
                    doc.add(new Paragraph("Rua: " + pdf.get(i).getRua()));
                    doc.add(new Paragraph("Número: " + pdf.get(i).getNumero()));
                    doc.add(new Paragraph(" "));
                    
                } catch (DocumentException ex) {
                    Logger.getLogger(GerenciamentoFuncionariosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            doc.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("PDF gerado com sucesso");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Defina um lugar para salvar o arquivo");
            alert.show();
        }
    }

}
