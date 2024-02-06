/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Fiche;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import services.ConsultationService;
import services.FicheService;
import utils.MailAPI;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AjouterFicheController implements Initializable {

    @FXML
    private TextField ficheNonAdd;
    @FXML
    private TextArea fichedescAdd;
    @FXML
    private ComboBox<String> ficheCatAdd;
    @FXML
    private ComboBox<String> ficheConsAdd;
    @FXML
    private Button addFicheBtn;
    ConsultationService ste = new ConsultationService();
    FicheService st = new FicheService();
    @FXML
    private Button back;
    ObservableList<String> cons = FXCollections.observableArrayList();
    ObservableList<String> cat = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cons.addAll(ste.getAllConsultation());
        cat.add("New");
        cat.add("Follow up");
        ficheConsAdd.setItems(cons);
        ficheCatAdd.setItems(cat);
    }

    public String controleDeSaisie() {
        String erreur = "";
        if (ficheNonAdd.getText().trim().isEmpty()) {
            erreur += "-nom vide\n";
        }
        if (fichedescAdd.getText().trim().isEmpty()) {
            erreur += "-description vide\n";
        }

        if (ficheConsAdd.getValue().trim().isEmpty()) {
            erreur += "-consultation vide\n";
        }

        if (ficheCatAdd.getValue().trim().isEmpty()) {
            erreur += "-categorie vide\n";
        }
        return erreur;
    }

    @FXML
    private void add(ActionEvent event) {
        String erreur = controleDeSaisie();
        if (erreur.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING); //alert
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        } else {
            Fiche c = new Fiche();
            c.setConsultation(ste.getIdByConsultation(ficheConsAdd.getValue()));
            c.setNom(ficheNonAdd.getText());
            c.setCategory(ficheCatAdd.getValue());
            c.setDescriptionFiche(fichedescAdd.getText());
            st.ajouter(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("The fiche has been successfully added.");
            alert.showAndWait();
            back(event);
            envoyeremailAdd();
        }
    }

    private void envoyeremailAdd() {
        try {

            MailAPI.sendMail("chaymagharbi10@gmail.com", "Notification par mail", "Une fiche a été ajoutée");
        } catch (MessagingException ex) {
            Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("ListFiche.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
