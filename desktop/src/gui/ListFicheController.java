/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.WriterException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import entities.Consultation;
import entities.Fiche;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javax.mail.MessagingException;
import services.ConsultationService;
import services.FicheService;
import services.MyListener;
import services.MyListenerF;
import utils.MailAPI;
import utils.SmsAPI;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ListFicheController implements Initializable {

    @FXML
    private Hyperlink ficheBtn;
    @FXML
    private Hyperlink consBtn;
    @FXML
    private Button addBtn;
    @FXML
    private TextField search;
    @FXML
    private Label idgetter;
    @FXML
    private GridPane grid;
    @FXML
    private Button deleteBtn;
    ObservableList<Fiche> data = FXCollections.observableArrayList();
    FicheService sc = new FicheService();
    @FXML
    private TextField nomUp;
    @FXML
    private TextArea descUp;
    @FXML
    private ComboBox<String> catUp;
    @FXML
    private ComboBox<String> consUp;
    ConsultationService ste = new ConsultationService();
    FicheService st = new FicheService();
    ObservableList<String> cons = FXCollections.observableArrayList();
    ObservableList<String> cat = FXCollections.observableArrayList();
    Fiche e;
    private Fiche selectedFiche;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            cons.addAll(ste.getAllConsultation());
            cat.add("New");
            cat.add("Follow up");
            consUp.setItems(cons);
            catUp.setItems(cat);
            recherche_avance();
        } catch (WriterException ex) {
            Logger.getLogger(ListFicheController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToFiche(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("ListFiche.fxml"));
            ficheBtn.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goToConsultation(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("ListConsultation.fxml"));
            consBtn.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    MyListenerF myListener;

    /*public void selectedEvent(Fiche c) {
        idgetter.setText(c.getId() + "");
        fillforum(c);
    }*/
    private void selectedEvent(Fiche c) {
        this.selectedFiche = c;
        idgetter.setText(c.getId() + "");
        System.out.println(c.getId());
        fillforum(c);
    }

    private void fillforum(Fiche e) {
        this.e = e;
        if (e != null) {
            descUp.setText(e.getDescriptionFiche() + "");
            nomUp.setText(e.getNom() + "");
            catUp.setValue(e.getCategory() + "");
            consUp.setValue(ste.getConsultationById(e.getConsultation()) + "");
        }
    }

    public void refresh(List<Fiche> events) throws WriterException {
        grid.getChildren().clear();
        if (events.size() > 0) {
            selectedEvent(events.get(0));
            myListener = new MyListenerF() {
                @Override
                public void onclickListener(Fiche e) {
                    selectedEvent(e);
                }
            };
        }
        int column = 0;
        int row = 1;
        for (int i = 0; i < events.size(); i++) {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("Fiche.fxml"));
            try {
                AnchorPane anchorePane = load.load();
                FicheController itemController = load.getController();
                itemController.remplir(events.get(i), myListener);
                if (column == 2) {
                    column = 0;
                    row++;
                }
                final int finalI = i;
                anchorePane.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (myListener != null) {
                            myListener.onclickListener(events.get(finalI));
                        }
                    }
                });
                grid.add(anchorePane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setHgap(290);

                GridPane.setMargin(anchorePane, new Insets(20));
            } catch (IOException ex) {
                Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /*  public void refresh() {
        grid.getChildren().clear();
        List<Fiche> events = sc.afficherListe();
        if (events.size() > 0) {
            selectedEvent(events.get(0));
            myListener = new MyListenerF() {
                @Override
                public void onclickListener(Fiche e) {
                    selectedEvent(e);
                }
            };
        }
        int column = 0;
        int row = 1;
        for (int i = 0; i < events.size(); i++) {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("Fiche.fxml"));
            try {
                AnchorPane anchorePane = load.load();
                FicheController itemController = load.getController();
                itemController.remplir(events.get(i), myListener);
                if (column == 2) {
                    column = 0;
                    row++;
                }
                final int finalI = i;
                anchorePane.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        myListener.onclickListener(events.get(finalI));
                    }
                });
                grid.add(anchorePane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setHgap(270);

                GridPane.setMargin(anchorePane, new Insets(20));
            } catch (IOException ex) {
                Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }*/
    @FXML
    private void add(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("AjouterFiche.fxml"));
            addBtn.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void delete(ActionEvent event) throws MySQLIntegrityConstraintViolationException, WriterException {
        // code that may throw the MySQLIntegrityConstraintViolationException
        sc.supprimer(Integer.parseInt(idgetter.getText()));
        // show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("The fiche has been successfully deleted.");
        alert.showAndWait();
        recherche_avance();
        envoyeremailSup();
    }

    public String controleDeSaisie() {
        String erreur = "";
        if (nomUp.getText().trim().isEmpty()) {
            erreur += "-nom vide\n";
        }
        if (descUp.getText().trim().isEmpty()) {
            erreur += "-description vide\n";
        }

        if (consUp.getValue().trim().isEmpty()) {
            erreur += "-consultation vide\n";
        }

        if (catUp.getValue().trim().isEmpty()) {
            erreur += "-categorie vide\n";
        }
        return erreur;
    }

    @FXML
    private void edit(ActionEvent event) throws WriterException {
        int id = Integer.parseInt(idgetter.getText());
        String erreur = controleDeSaisie();
        if (erreur.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING); //alert
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        } else {
            Fiche c = new Fiche();
            c.setConsultation(ste.getIdByConsultation(consUp.getValue()));
            c.setNom(nomUp.getText());
            c.setCategory(catUp.getValue());
            c.setDescriptionFiche(descUp.getText());
            sc.modifier(id, c);
            System.out.println(c.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("The fiche has been successfully updated.");
            alert.showAndWait();
            recherche_avance();
            envoyercodesms();
        }
    }

    private void envoyercodesms() {
        SmsAPI.send("+21654878151", "Votre fiche est modifiée!");
    }

    private void envoyeremailSup() {
        try {

            MailAPI.sendMail("chaymagharbi10@gmail.com", "Notification par mail", "Une fiche a été suppimée");
        } catch (MessagingException ex) {
            Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recherche_avance() throws WriterException {
        refresh(st.afficherListe());
        data.clear();
        data.addAll(sc.afficherListe());
        FilteredList<Fiche> filteredList = new FilteredList<>(data, e -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (event.getNom().toLowerCase().indexOf(newValue.toLowerCase()) != -1) {
                    return true;
                }
                if (event.getCategory().toLowerCase().indexOf(newValue.toLowerCase()) != -1) {
                    return true;
                }
                if (event.getDescriptionFiche().toLowerCase().indexOf(newValue.toLowerCase()) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            System.out.println(filteredList);
            try {
                refresh(filteredList);
            } catch (WriterException ex) {
                Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    private void goToStat(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("Stat.fxml"));
            addBtn.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
