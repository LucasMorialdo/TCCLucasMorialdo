/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerFarmacia;

import static Controller.LoginController.loginUnv;
import DAO.EstoqueFarmaciaDAO;
import Model.ProdutosFarmacia;
import ViewManage.ControleEstoqueFarmacia.AtualizaFarmaciaView;
import ViewManage.ControleEstoqueFarmacia.CadastroFarmaciaView;
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
public class ControleEstoqueFarmaciaController implements Initializable {

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
    TableView<ProdutosFarmacia> tabelaID;
    @FXML
    TableColumn<ProdutosFarmacia, Integer> colunaID;
    @FXML
    TableColumn<ProdutosFarmacia, String> colunaNome;
    @FXML
    TableColumn<ProdutosFarmacia, String> colunaQuantidade;
    @FXML
    TableColumn<ProdutosFarmacia, String> colunaInfoGerais;

    private ObservableList<ProdutosFarmacia> produtosF;
    static ProdutosFarmacia selecionado;
    
    private ObservableList<String> cbPesquisar = FXCollections.observableArrayList("ID", "Nome", "Quantidade", "Informações Gerais");

    @FXML
    private void preencheTabela() {
        colunaID.setCellValueFactory(new PropertyValueFactory("idProdutosFarmacia"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nomeProduto"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaInfoGerais.setCellValueFactory(new PropertyValueFactory("infoGeral"));

        EstoqueFarmaciaDAO dao = new EstoqueFarmaciaDAO();
        produtosF = dao.getPF();
        tabelaID.setItems(produtosF);
    }

    @FXML
    private void deletaAlimentos() throws Exception {
        if (loginUnv.getTipoperfil().equals("Funcionário padrão")) {
            System.out.println("Você não tem permissão para excluir dados/objetos");
        } else {
            if (ControleEstoqueFarmaciaController.selecionado != null) {
                EstoqueFarmaciaDAO dao = new EstoqueFarmaciaDAO();
                dao.deletaProdutosFarmacia(ControleEstoqueFarmaciaController.selecionado);
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
    private void abreCadastroFarmacia() throws Exception {
        CadastroFarmaciaView cadAlimentos = new CadastroFarmaciaView();
        cadAlimentos.start(new Stage());
        preencheTabela();
    }

    @FXML
    private void abreAtualizaFarmacia() throws Exception {
        AtualizaFarmaciaView attAlimentos = new AtualizaFarmaciaView();
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
                    ControleEstoqueFarmaciaController.selecionado = (ProdutosFarmacia) newValue;
                } else {
                    ControleEstoqueFarmaciaController.selecionado = null;
                }
            }

        });
        
        btPesquisarItens.setOnMouseClicked((MouseEvent e) -> {
            tabelaID.setItems(pesquisar());
        });

    }
    
    private ObservableList<ProdutosFarmacia> pesquisar() {
        if (cbPesquisarPor.getValue().equals("ID")) {
            ObservableList<ProdutosFarmacia> farmaciaPesquisa = FXCollections.observableArrayList();
            for(int x = 0; x < produtosF.size(); x++) {
                if(produtosF.get(x).getIdProdutosFarmacia() == parseLong(txtPesquisa.getText())) {
                    farmaciaPesquisa.add(produtosF.get(x));
                }
            }
            return farmaciaPesquisa;
        } else if (cbPesquisarPor.getValue().equals("Nome")) {
            ObservableList<ProdutosFarmacia> farmaciaPesquisa = FXCollections.observableArrayList();
            for(int x = 0; x < produtosF.size(); x++) {
                if(produtosF.get(x).getNomeProduto().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    farmaciaPesquisa.add(produtosF.get(x));
                }
            }
            return farmaciaPesquisa;
        } else if (cbPesquisarPor.getValue().equals("Quantidade")) {
            ObservableList<ProdutosFarmacia> farmaciaPesquisa = FXCollections.observableArrayList();
            for(int x = 0; x < produtosF.size(); x++) {
                if(produtosF.get(x).getQuantidade().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    farmaciaPesquisa.add(produtosF.get(x));
                }
            }
            return farmaciaPesquisa;
        } else if (cbPesquisarPor.getValue().equals("Informações Gerais")) {
            ObservableList<ProdutosFarmacia> farmaciaPesquisa = FXCollections.observableArrayList();
            for(int x = 0; x < produtosF.size(); x++) {
                if(produtosF.get(x).getInfoGeral().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    farmaciaPesquisa.add(produtosF.get(x));
                }
            }
            return farmaciaPesquisa;
        }
        return null;
    }
    
    @FXML
    private void gerarPdf() {
        Document doc = new Document();
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new ExtensionFilter("PDF", "*.pdf"));
        File file = fs.showSaveDialog(new Stage());
        EstoqueFarmaciaDAO dao = new EstoqueFarmaciaDAO();
        ObservableList<ProdutosFarmacia> pdf = dao.getPF();

        if (file != null) {
            try {
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EstoqueFarmaciaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(EstoqueFarmaciaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            doc.open();
            for (int i = 0; i < pdf.size(); i++) {
                try {
                    doc.add(new Paragraph("ID: " + pdf.get(i).getIdProdutosFarmacia()));
                    doc.add(new Paragraph("Nome do produto: " + pdf.get(i).getNomeProduto()));
                    doc.add(new Paragraph("Quantidade: " + pdf.get(i).getQuantidade()));
                    doc.add(new Paragraph("Informações Gerais: " + pdf.get(i).getInfoGeral()));
                    doc.add(new Paragraph(" "));
                    
                } catch (DocumentException ex) {
                    Logger.getLogger(EstoqueFarmaciaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
