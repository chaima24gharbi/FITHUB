/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Consultation;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import services.ConsultationService;
import services.UtilisateurService;
import utils.MailAPI;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AjouterConsultationController implements Initializable {

    @FXML
    private ImageView cnsAddBcg;
    @FXML
    private Button cnsAddBtn;
    @FXML
    private Label cnsAddNameLabel;
    @FXML
    private TextField cnsAddNameInput;
    @FXML
    private Label cnsAddDateLabel;
    @FXML
    private DatePicker cnsAddDate;
    @FXML
    private ComboBox<String> cnsAddSelect;
    @FXML
    private TextField cnsAddTime;
    ConsultationService ste = new ConsultationService();
    UtilisateurService st = new UtilisateurService();
    ObservableList<String> users = FXCollections.observableArrayList();
    ObservableList<String> type = FXCollections.observableArrayList();
    @FXML
    private Button returnBtn;
    @FXML
    private ComboBox<String> cnsType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        users.addAll(st.GetIdRoles());
        type.add("Cabinet");
        type.add("Adistance");
        cnsAddSelect.setItems(users);
        cnsType.setItems(type);
    }

    public String controleDeSaisie() {
        String erreur = "";//liste des erreurs vide
        /*if (tfdomaine.getText().trim().isEmpty()) {
            erreur += "-d vide\n";//message
        }*/
        if (cnsType.getValue().trim().isEmpty()) {
            erreur += "-type vide\n";//message
        }
        if (cnsAddNameInput.getText().trim().isEmpty()) {
            erreur += "-nom vide\n";//message
        }
        if (cnsAddTime.getText().trim().isEmpty()) {
            erreur += "-heure vide\n";
        }

        if (cnsAddSelect.getValue().trim().isEmpty()) {
            erreur += "-nutritionnist vide\n";
        }

        if (cnsAddDate.getValue() == null) {
            erreur += "-date vide\n";
        }
        //match entier 
        if (!cnsAddTime.getText().matches("\\d+")) {
            erreur += "-heure invalide \n";
        }
        return erreur;
    }

    @FXML
    private void AjoutConsultation(ActionEvent event) {
        String erreur = controleDeSaisie();
        if (erreur.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING); //alert
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        } else {
            Consultation c = new Consultation();
            c.setDate_consultation(Date.valueOf(cnsAddDate.getValue()));
            c.setNom(cnsAddNameInput.getText());
            c.setHeure_consultation(Integer.valueOf(cnsAddTime.getText()));
            c.setType_consultation(cnsType.getValue());
            c.setUtilisateur_id(st.getIdConsultationByRole(cnsAddSelect.getValue()));
            ste.ajouter(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("The Consultation has been successfully added.");
            alert.showAndWait();
            showList(event);
            envoyeremailAdd();
        }
    }

    private void envoyeremailAdd() {
        try {

            MailAPI.sendMail("chaymagharbi10@gmail.com", "Notification par mail", "Une consultation a été ajoutée");
        } catch (MessagingException ex) {
            Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void showList(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("ListConsultation.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
