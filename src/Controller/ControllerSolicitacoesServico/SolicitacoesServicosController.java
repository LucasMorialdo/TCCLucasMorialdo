/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ControllerSolicitacoesServico;

import static Controller.LoginController.loginUnv;
import DAO.SolicitacoesDAO;
import Model.SolicitacoesServicos;
import ViewManage.ControleSolicitacoesServicos.AtualizaSolicitacoesView;
import ViewManage.ControleSolicitacoesServicos.CadastroSolicitacoesView;
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
import java.sql.Date;
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
import javafx.stage.Stage;

/**
 *
 * @author Usuário
 */
public class SolicitacoesServicosController implements Initializable {

    @FXML
    private Button btPesquisar;
    @FXML
    private Button btCadastroSolicitacao;
    @FXML
    private Button btAlterarSolicitacao;
    @FXML
    private Button btExcluirSolicitacao;
    @FXML 
    private Button btAtualizaTabela;
    @FXML
    private ComboBox cbPesquisarPor;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private Button btGerarPdf;
    
    //TABELAS
    @FXML
    TableView<SolicitacoesServicos> tabelaID;
    @FXML
    TableColumn<SolicitacoesServicos, Integer> colunaID;
    @FXML
    TableColumn<SolicitacoesServicos, String> colunaTipoSolicitacao;
    @FXML
    TableColumn<SolicitacoesServicos, Date> colunaDataRegistro;
    @FXML
    TableColumn<SolicitacoesServicos, String> colunaInfoGeral;
    
    private ObservableList<SolicitacoesServicos> solicitacoes;
    static SolicitacoesServicos selecionado;
    
    private ObservableList<String> tipoPesquisa = FXCollections.observableArrayList("ID", "Tipo de Solicitação", "Data do registro", "Informações gerais");
    
    @FXML
    private void preencheTabela(){
        colunaID.setCellValueFactory(new PropertyValueFactory("idsolicitacoes"));
        colunaTipoSolicitacao.setCellValueFactory(new PropertyValueFactory("tipoSolicitacao"));
        colunaDataRegistro.setCellValueFactory(new PropertyValueFactory("dataRegistro"));
        colunaInfoGeral.setCellValueFactory(new PropertyValueFactory("infoGeral"));
        
        SolicitacoesDAO dao = new SolicitacoesDAO();
        solicitacoes = dao.getSolicitacoes();
        tabelaID.setItems(solicitacoes);
    }
    
    @FXML
    private void abreCadastroSolicitacoes() throws Exception {
        CadastroSolicitacoesView cadSol = new CadastroSolicitacoesView();
        cadSol.start(new Stage());
    }
    
    @FXML
    private void abreAtualizaSolicitacoes() throws Exception {
        AtualizaSolicitacoesView attSol = new AtualizaSolicitacoesView();
        attSol.start(new Stage());
    }
    
    @FXML
    private void deletaSolServ() throws Exception {
        if (loginUnv.getTipoperfil().equals("Funcionário padrão")) {
            System.out.println("Você não tem permissão para excluir dados/objetos");
        } else {
            if (SolicitacoesServicosController.selecionado != null) {
                SolicitacoesDAO dao = new SolicitacoesDAO();
                dao.deletaSolicitacao(SolicitacoesServicosController.selecionado);
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencheTabela();
        cbPesquisarPor.setItems(tipoPesquisa);
        
        tabelaID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValues, Object newValue) {
                if(newValue != null) {
                    SolicitacoesServicosController.selecionado = (SolicitacoesServicos) newValue;
                } else {
                    SolicitacoesServicosController.selecionado = null;
                }
            }
        });
    }
    
    private ObservableList<SolicitacoesServicos> pesquisar() {
        if (cbPesquisarPor.getValue().equals("ID")) {
            ObservableList<SolicitacoesServicos> solServPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < solicitacoes.size(); x ++) {
                if (solicitacoes.get(x).getIdsolicitacoes() == parseLong(txtPesquisa.getText())) {
                    solServPesquisa.add(solicitacoes.get(x));
                }
            }
            return solServPesquisa;
        } else if (cbPesquisarPor.getValue().equals("Tipo de Solicitação")) {
            ObservableList<SolicitacoesServicos> solServPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < solicitacoes.size(); x ++) {
                if (solicitacoes.get(x).getTipoSolicitacao().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    solServPesquisa.add(solicitacoes.get(x));
                }
            }
            return solServPesquisa;
        } /*else if (cbPesquisarPor.getValue().equals("Data do registro")) {
            ObservableList<SolicitacoesServicos> solServPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < solicitacoes.size(); x ++) {
                if (solicitacoes.get(x).getDataRegistro().get().contains(txtPesquisa.getText())) {
                    solServPesquisa.add(solicitacoes.get(x));
                }
            }
            return solServPesquisa;
        }*/  else if (cbPesquisarPor.getValue().equals("Informações Gerais")) {
            ObservableList<SolicitacoesServicos> solServPesquisa = FXCollections.observableArrayList();
            for (int x = 0; x < solicitacoes.size(); x ++) {
                if (solicitacoes.get(x).getInfoGeral().toUpperCase().contains(txtPesquisa.getText().toUpperCase())) {
                    solServPesquisa.add(solicitacoes.get(x));
                }
            }
            return solServPesquisa;
        }
        
        return null;
    }
    
    @FXML
    private void gerarPdf() {
        Document doc = new Document();
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fs.showSaveDialog(new Stage());
        SolicitacoesDAO dao = new SolicitacoesDAO();
        ObservableList<SolicitacoesServicos> pdf = dao.getSolicitacoes();

        if (file != null) {
            try {
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SolicitacoesServicosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(SolicitacoesServicosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            doc.open();
            for (int i = 0; i < pdf.size(); i++) {
                try {
                    doc.add(new Paragraph("ID: " + pdf.get(i).getIdsolicitacoes()));
                    doc.add(new Paragraph("Tipo de solicitação: " + pdf.get(i).getTipoSolicitacao()));
                    doc.add(new Paragraph("Data do registro: " + pdf.get(i).getDataRegistro()));
                    doc.add(new Paragraph("Informações Gerais: " + pdf.get(i).getInfoGeral()));
                    doc.add(new Paragraph(" "));
                    
                } catch (DocumentException ex) {
                    Logger.getLogger(SolicitacoesServicosController.class.getName()).log(Level.SEVERE, null, ex);
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
