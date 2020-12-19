/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerAnimais;

import static Controller.LoginController.loginUnv;
import DAO.AnimaisDAO;
import static DAO.AnimaisDAO.insereAnimais;
import Model.Animais;
import ViewManage.ControleAnimais.AtualizarCamposAnimaisView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static java.lang.Long.parseLong;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Usuário
 */
public class GerenciamentoAnimaisController implements Initializable {

    // Aba Cadastro
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
    private Button btCadastrar;
    @FXML
    private Button btLimpar;

    private ObservableList<String> necessidades = FXCollections.observableArrayList("Sim", "Não");
    private ObservableList<String> temperamento = FXCollections.observableArrayList("Agressivo", "Dócil", "Companheiro", "Outro");
    private ObservableList<String> sociabilidade = FXCollections.observableArrayList("Alta", "Média", "Baixa");
    private ObservableList<String> tipoPesquisa = FXCollections.observableArrayList("ID", "Nome", "Espécie", "Raça", "Peso", "Necessidades Especiais", "Temperamento", "Sociabilidade", "Observações Gerais", "Cadastrado por");

    // Aba Listagem
    @FXML
    private ComboBox cbPesquisa;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private Button btPesquisa;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btAlterar;
    @FXML
    private Button btAtualizar;
    @FXML
    private Button btGerarPdf;
    // Tabelas
    @FXML
    TableView<Animais> tabelaID;
    @FXML
    TableColumn<Animais, Integer> colunaID;
    @FXML
    TableColumn<Animais, String> colunaNome;
    @FXML
    TableColumn<Animais, String> colunaEspecie;
    @FXML
    TableColumn<Animais, String> colunaRaca;
    @FXML
    TableColumn<Animais, String> colunaPeso;
    @FXML
    TableColumn<Animais, String> colunaNecessidades;
    @FXML
    TableColumn<Animais, String> colunaTemperamento;
    @FXML
    TableColumn<Animais, String> colunaSociabilidade;
    @FXML
    TableColumn<Animais, String> colunaObsGerais;

    private ObservableList<Animais> animais = FXCollections.observableArrayList();
    static Animais selecionado;

    @FXML
    void preencheTabela() {
        colunaID.setCellValueFactory(new PropertyValueFactory("idAnimais"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaEspecie.setCellValueFactory(new PropertyValueFactory("especie"));
        colunaRaca.setCellValueFactory(new PropertyValueFactory("raca"));
        colunaPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        colunaNecessidades.setCellValueFactory(new PropertyValueFactory("necessidadesEspeciais"));
        colunaTemperamento.setCellValueFactory(new PropertyValueFactory("temperamento"));
        colunaSociabilidade.setCellValueFactory(new PropertyValueFactory("sociabilidade"));
        colunaObsGerais.setCellValueFactory(new PropertyValueFactory("obsGerais"));

        AnimaisDAO dao = new AnimaisDAO();
        animais = dao.getAnimais();
        tabelaID.setItems(animais);
    }

    @FXML
    private void abreAtualizacaoCampos() throws Exception {
        AtualizarCamposAnimaisView atualizaCampos = new AtualizarCamposAnimaisView();
        atualizaCampos.start(new Stage());
    }

    @FXML
    private void CadastroAnimais() {
        if (txtNome.getText().equals("") || txtEspecie.getText().equals("")
                || txtRaca.getText().equals("") || txtPeso.getText().equals("")
                || cbNecessidades.getValue() == null || cbTemperamento.getValue() == null
                || cbSociabilidade.getValue() == null || txtareaObsGerais.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ATENÇÃO");
            a.setHeaderText("Campos vazios");
            a.setContentText("Por favor, não deixe nenhum campo vazio.");
            a.showAndWait();
        } else {
            try {
                Animais an = new Animais();
                an.setNome(txtNome.getText());
                an.setEspecie(txtEspecie.getText());
                an.setRaca(txtRaca.getText());
                an.setPeso(txtPeso.getText());
                an.setNecessidadesEspeciais(cbNecessidades.getValue().toString());
                an.setTemperamento(cbTemperamento.getValue().toString());
                an.setSociabilidade(cbSociabilidade.getValue().toString());
                an.setObsGerais(txtareaObsGerais.getText());
                insereAnimais(an);

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("VERIFICAÇÃO DE CADASTRO");
                a.setHeaderText("Campos digitados corretamente");
                a.setContentText("Ok, cadastro realizado");
                a.showAndWait();

                preencheTabela();
                clearForm();
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar. " + e.getMessage());
            }
        }
    }

    @FXML
    public void deletaAnimais() throws Exception {
        if (loginUnv.getTipoperfil().equals("Funcionário padrão")) {
            System.out.println("Você não tem permissão para excluir dados/objetos");
        } else {
            if (GerenciamentoAnimaisController.selecionado != null) {
                AnimaisDAO dao = new AnimaisDAO();
                dao.deletaAnimais(GerenciamentoAnimaisController.selecionado);
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Produto deletado com sucesso.");
                a.showAndWait();
                preencheTabela();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Por favor, selecione um produto.");
                a.showAndWait();
            }
        }
    }

    @FXML
    void clearForm() {
        txtNome.clear();
        txtEspecie.clear();
        txtPeso.clear();
        txtRaca.clear();
        txtareaObsGerais.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbNecessidades.setItems(necessidades);
        cbTemperamento.setItems(temperamento);
        cbSociabilidade.setItems(sociabilidade);
        cbPesquisa.setItems(tipoPesquisa);
        preencheTabela();

        btPesquisa.setOnMouseClicked((MouseEvent e) -> {
            tabelaID.setItems(pesquisar());
        });
        

        tabelaID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    GerenciamentoAnimaisController.selecionado = (Animais) newValue;
                } else {
                    GerenciamentoAnimaisController.selecionado = null;
                }
            }
        });
    }

    private ObservableList<Animais> pesquisar() {
        if (cbPesquisa.getValue().equals("Nome")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getNome().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;
        } else if (cbPesquisa.getValue().equals("ID")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();

            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getIdAnimais() == parseLong(txtPesquisa.getText())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }

            return animaisPesquisa;
        } else if (cbPesquisa.getValue().equals("Espécie")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getEspecie().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;
        } else if (cbPesquisa.getValue().equals("Raça")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getRaca().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;
        } else if (cbPesquisa.getValue().equals("Peso")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getPeso().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;

        } else if (cbPesquisa.getValue().equals("Necessidades Especiais")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getNecessidadesEspeciais().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;

        } else if (cbPesquisa.getValue().equals("Temperamento")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getTemperamento().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;

        } else if (cbPesquisa.getValue().equals("Sociabilidade")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getSociabilidade().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;

        } else if (cbPesquisa.getValue().equals("Observações Gerais")) {
            ObservableList<Animais> animaisPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < animais.size(); x++) {
                if (animais.get(x).getObsGerais().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    animaisPesquisa.add(animais.get(x));
                }
            }
            return animaisPesquisa;

        }
        return null;

    }
    
    @FXML
    private void gerarPdf() {
        Document doc = new Document();
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fs.showSaveDialog(new Stage());
        AnimaisDAO dao = new AnimaisDAO();
        ObservableList<Animais> pdf = dao.getAnimais();

        if (file != null) {
            try {
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GerenciamentoAnimaisController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(GerenciamentoAnimaisController.class.getName()).log(Level.SEVERE, null, ex);
            }
            doc.open();
            for (int i = 0; i < pdf.size(); i++) {
                try {
                    doc.add(new Paragraph("ID: " + pdf.get(i).getIdAnimais()));
                    doc.add(new Paragraph("Nome do animal: " + pdf.get(i).getNome()));
                    doc.add(new Paragraph("Espécie: " + pdf.get(i).getEspecie()));
                    doc.add(new Paragraph("Raça: " + pdf.get(i).getRaca()));
                    doc.add(new Paragraph("Peso: " + pdf.get(i).getPeso()));
                    doc.add(new Paragraph("Necessidades especiais?: " + pdf.get(i).getNecessidadesEspeciais()));
                    doc.add(new Paragraph("Temperamento: " + pdf.get(i).getTemperamento()));
                    doc.add(new Paragraph("Grau de Sociabilidade: " + pdf.get(i).getSociabilidade()));
                    doc.add(new Paragraph("Observações Gerais: " + pdf.get(i).getObsGerais()));
                    doc.add(new Paragraph(" "));
                    
                } catch (DocumentException ex) {
                    Logger.getLogger(GerenciamentoAnimaisController.class.getName()).log(Level.SEVERE, null, ex);
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
