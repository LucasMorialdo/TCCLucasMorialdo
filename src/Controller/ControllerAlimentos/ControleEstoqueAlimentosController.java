/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerAlimentos;

import static Controller.LoginController.loginUnv;
import DAO.EstoqueAlimentosDAO;
import Model.ProdutosAlimentos;
import ViewManage.ControleEstoqueAlimentos.AtualizaAlimentosView;
import ViewManage.ControleEstoqueAlimentos.CadastroAlimentosView;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Usuário
 */
public class ControleEstoqueAlimentosController implements Initializable {

    @FXML
    private Button btAdicionarItens;
    @FXML
    private Button btAlterarItens;
    @FXML
    private Button btExcluirItens;
    @FXML
    private Button btAtualizarTabela;
    @FXML
    private Button btPesquisarItens;
    @FXML
    private ComboBox cbPesquisarPor;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private Button btGerarPdf;

    //Tabelas
    @FXML
    TableView<ProdutosAlimentos> tabelaID;
    @FXML
    TableColumn<ProdutosAlimentos, Integer> colunaID;
    @FXML
    TableColumn<ProdutosAlimentos, String> colunaNome;
    @FXML
    TableColumn<ProdutosAlimentos, String> colunaQuantidade;
    @FXML
    TableColumn<ProdutosAlimentos, String> colunaInfoGerais;

    private ObservableList<ProdutosAlimentos> produtosA;
    static ProdutosAlimentos selecionado;

    private ObservableList<String> cbPesquisar = FXCollections.observableArrayList("ID", "Nome", "Quantidade", "Informações Gerais");

    @FXML
    private void preencheTabela() {
        colunaID.setCellValueFactory(new PropertyValueFactory("idProdutosAlimentos"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nomeProduto"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaInfoGerais.setCellValueFactory(new PropertyValueFactory("infoGeral"));

        EstoqueAlimentosDAO dao = new EstoqueAlimentosDAO();
        produtosA = dao.getPA();
        tabelaID.setItems(produtosA);
    }

    @FXML
    private void deletaAlimentos() throws Exception {
        if (loginUnv.getTipoperfil().equals("Funcionário padrão")) {
            System.out.println("Você não tem permissão para excluir dados/objetos");
        } else {
            if (ControleEstoqueAlimentosController.selecionado != null) {
                EstoqueAlimentosDAO dao = new EstoqueAlimentosDAO();
                dao.deletaProdutosAlimentos(ControleEstoqueAlimentosController.selecionado);
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
    private void abreCadastroAlimentos() throws Exception {
        CadastroAlimentosView cadAlimentos = new CadastroAlimentosView();
        cadAlimentos.start(new Stage());
        preencheTabela();
    }

    @FXML
    private void abreAtualizaAlimentos() throws Exception {
        AtualizaAlimentosView attAlimentos = new AtualizaAlimentosView();
        attAlimentos.start(new Stage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbPesquisarPor.setItems(cbPesquisar);
        preencheTabela();

        tabelaID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    ControleEstoqueAlimentosController.selecionado = (ProdutosAlimentos) newValue;
                } else {
                    ControleEstoqueAlimentosController.selecionado = null;
                }
            }
        });

        btPesquisarItens.setOnMouseClicked((MouseEvent e) -> {
            tabelaID.setItems(pesquisar());
        });
    }

    private ObservableList<ProdutosAlimentos> pesquisar() {
        if (cbPesquisarPor.getValue().equals("ID")) {
            ObservableList<ProdutosAlimentos> alimentosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < produtosA.size(); x++) {
                if (produtosA.get(x).getIdProdutosAlimentos() == parseLong(txtPesquisa.getText())) {
                    alimentosPesquisa.add(produtosA.get(x));
                }
            }
            return alimentosPesquisa;
        } else if (cbPesquisarPor.getValue().equals("Nome")) {
            ObservableList<ProdutosAlimentos> alimentosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < produtosA.size(); x++) {
                if (produtosA.get(x).getNomeProduto().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    alimentosPesquisa.add(produtosA.get(x));
                }
            }
            return alimentosPesquisa;
        } else if (cbPesquisarPor.getValue().equals("Quantidade")) {
            ObservableList<ProdutosAlimentos> alimentosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < produtosA.size(); x++) {
                if (produtosA.get(x).getQuantidade().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    alimentosPesquisa.add(produtosA.get(x));
                }
            }
            return alimentosPesquisa;
        } else if (cbPesquisarPor.getValue().equals("Informações Gerais")) {
            ObservableList<ProdutosAlimentos> alimentosPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < produtosA.size(); x++) {
                if (produtosA.get(x).getInfoGeral().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    alimentosPesquisa.add(produtosA.get(x));
                }
            }
            return alimentosPesquisa;
        }
        return null;
    }
    
    @FXML
    private void gerarPdf() {
        Document doc = new Document();
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new ExtensionFilter("PDF", "*.pdf"));
        File file = fs.showSaveDialog(new Stage());
        EstoqueAlimentosDAO dao = new EstoqueAlimentosDAO();
        ObservableList<ProdutosAlimentos> pdf = dao.getPA();

        if (file != null) {
            try {
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControleEstoqueAlimentosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(ControleEstoqueAlimentosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            doc.open();
            for (int i = 0; i < pdf.size(); i++) {
                try {
                    doc.add(new Paragraph("ID: " + pdf.get(i).getIdProdutosAlimentos()));
                    doc.add(new Paragraph("Nome do produto: " + pdf.get(i).getNomeProduto()));
                    doc.add(new Paragraph("Quantidade: " + pdf.get(i).getQuantidade()));
                    doc.add(new Paragraph("Informações Gerais: " + pdf.get(i).getInfoGeral()));
                    doc.add(new Paragraph(" "));
                    
                } catch (DocumentException ex) {
                    Logger.getLogger(ControleEstoqueAlimentosController.class.getName()).log(Level.SEVERE, null, ex);
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
