/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.WriterException;
import entities.Consultation;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import services.ConsultationService;
import services.MyListener;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javax.mail.MessagingException;
import services.UtilisateurService;
import utils.MailAPI;
import utils.SmsAPI;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ListConsultationController implements Initializable {

    @FXML
    private Hyperlink listeConsultation;
    @FXML
    private Hyperlink listeFiche;
    @FXML
    private GridPane grid;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    @FXML
    private TextField search;
    ObservableList<Consultation> data = FXCollections.observableArrayList();
    ConsultationService sc = new ConsultationService();
    UtilisateurService st = new UtilisateurService();
    ObservableList<String> users = FXCollections.observableArrayList();
    ObservableList<String> type = FXCollections.observableArrayList();
    Consultation e;
    @FXML
    private Label idgetter;
    @FXML
    private TextField nomUp;
    @FXML
    private TextField heureUp;
    @FXML
    private DatePicker dateUp;
    @FXML
    private ComboBox<String> typeUp;
    @FXML
    private ComboBox<String> userUp;
    private Consultation selectedConsultation;
    @FXML
    private Hyperlink stat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            users.addAll(st.GetIdRoles());
            type.add("Cabinet");
            type.add("Adistance");
            userUp.setItems(users);
            typeUp.setItems(type);
            recherche_avance();
        } catch (WriterException ex) {
            Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void redirectConsultList(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("ListConsultation.fxml"));
            listeConsultation.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void reditectFicheList(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("ListFiche.fxml"));
            listeFiche.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    MyListener myListener;

    /*public void selectedEvent(Consultation c) {
        idgetter.setText(c.getId() + "");
        System.out.println(c.getId());
        fillforum(c);
    }*/
    private void selectedEvent(Consultation c) {
        this.selectedConsultation = c;
        idgetter.setText(c.getId() + "");
        System.out.println(c.getId());
        fillforum(c);
    }

    public void refresh(List<Consultation> events) throws WriterException {
        grid.getChildren().clear();
        if (events.size() > 0) {
            selectedEvent(events.get(0));
            myListener = new MyListener() {
                @Override
                public void onclickListener(Consultation e) {
                    selectedEvent(e);
                }
            };
        }
        int column = 0;
        int row = 1;
        for (int i = 0; i < events.size(); i++) {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("Consultation.fxml"));
            try {
                AnchorPane anchorePane = load.load();
                ConsultationController itemController = load.getController();
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

    /*public void refresh(List<Consultation> events) throws WriterException{
        grid.getChildren().clear();
        events = sc.afficherListe();
        final List<Consultation> finalEvents=events;
        final MyListener finalMyListener;
        if (events.size() > 0) {
            selectedEvent(finalEvents.get(0));

            finalMyListener = new MyListener() {
                @Override
                public void onclickListener(Consultation e) {
                    selectedEvent(e);
                }
            };
        }else{ 
            finalMyListener=null;
        }
        int column = 0;
        int row = 1;
        for (int i = 0; i < events.size(); i++) {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("Consultation.fxml"));

            try {
                AnchorPane anchorePane = load.load();
                ConsultationController itemController = load.getController();
                itemController.remplir(events.get(i), myListener);
                if (column == 2) {
                    column = 0;
                    row++;
                }
                final int finalI = i;
                anchorePane.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                       if(finalMyListener !=null){
                        finalMyListener.onclickListener(finalEvents.get(finalI));
                    }}
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
         
    }*/
    @FXML
    private void delete(ActionEvent event) throws WriterException {
        sc.supprimer(Integer.parseInt(idgetter.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("The Consultation has been successfully deleted.");
        alert.showAndWait();
        recherche_avance();
        envoyeremailSup();
    }

    @FXML
    private void add(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("AjouterConsultation.fxml"));
            addBtn.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void fillforum(Consultation e) {
        this.e = e;
        if (e != null) {
            java.util.Date utilDate = new java.util.Date(e.getDate_consultation().getTime());
            Instant instant = utilDate.toInstant();

            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            dateUp.setValue(localDate);

            heureUp.setText(e.getHeure_consultation() + "");
            nomUp.setText(e.getNom() + "");
            typeUp.setValue(e.getType_consultation() + "");
            userUp.setValue(st.getRoleById(e.getUtilisateur_id()) + "");
        }
    }

    public void recherche_avance() throws WriterException {
        refresh(sc.afficherListe());
        data.clear();
        data.addAll(sc.afficherListe());
        FilteredList<Consultation> filteredList = new FilteredList<>(data, e -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (event.getNom().toLowerCase().indexOf(newValue.toLowerCase()) != -1) {
                    return true;
                }
                if (event.getType_consultation().toLowerCase().indexOf(newValue.toLowerCase()) != -1) {
                    return true;
                }
                if (String.valueOf(event.getDate_consultation()).toLowerCase().indexOf(newValue.toLowerCase()) != -1) {
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

    private void envoyercodesms() {
        SmsAPI.send("+21654878151", "Votre consultation est modifiée!");
    }

    private void envoyeremailSup() {
        try {

            MailAPI.sendMail("chaymagharbi10@gmail.com", "Notification par mail", "Une consultation a été suppimée:");
        } catch (MessagingException ex) {
            Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* public void recherche_avance() {
        //remplire lobservablelist
        //data.addAll(sc.afficherListe());
        //liste filtrer
        FilteredList<Consultation> filtreddata = new FilteredList<>(data, u -> true);
        //creation du listenere a partire du textfield
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filtreddata.setPredicate(c -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (c.getNom().indexOf(newValue) != -1) {
                    return true;
                } else if (c.getType_consultation().indexOf(newValue) != -1) {
                    return true;
                } else if (String.valueOf(c.getDate_consultation()).indexOf(newValue) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            events.addAll(filtreddata);
            System.out.println(events);
        });
    }
     */
 /* public void recherche_avance(GridPane gridPane,TextField search) {
    // Create a list of all the nodes in the gridPane
    ObservableList<Node> allNodes = FXCollections.observableArrayList(gridPane.getChildren());

    // Create a filtered list of nodes
    FilteredList<Node> filteredNodes = new FilteredList<>(allNodes, n -> true);

    // Add a listener to the text property of the search TextField
    search.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredNodes.setPredicate(n -> {
    if (newValue == null || newValue.isEmpty()) {
        // If the search field is empty, show all nodes
        return true;
    } else {
        // Otherwise, check if the node contains the search text
        String lowerCaseFilter = newValue.toLowerCase();
        if (n instanceof Label) {
            Label label = (Label) n;
            return label.getText().toLowerCase().contains(lowerCaseFilter);
        } else if (n instanceof Button) {
            Button button = (Button) n;
            return button.getText().toLowerCase().contains(lowerCaseFilter);
        } 
 else {
            return false;
        }
    }
});
        // Clear the gridPane and add the filtered nodes
        gridPane.getChildren().clear();
        gridPane.getChildren().addAll(filteredNodes);
    });
}*/
    public String controleDeSaisie() {
        String erreur = "";
        if (typeUp.getValue().trim().isEmpty()) {
            erreur += "-type vide\n";//message
        }
        if (nomUp.getText().trim().isEmpty()) {
            erreur += "-nom vide\n";//message
        }
        if (heureUp.getText().trim().isEmpty()) {
            erreur += "-heure vide\n";
        }

        if (userUp.getValue().trim().isEmpty()) {
            erreur += "-nutritionnist vide\n";
        }

        if (dateUp.getValue() == null) {
            erreur += "-date vide\n";
        }
        //match entier 
        if (!heureUp.getText().matches("\\d+")) {
            erreur += "-heure invalide \n";
        }
        return erreur;
    }

    @FXML
    private void edit(ActionEvent event) throws WriterException {
        String erreur = controleDeSaisie();
        if (erreur.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING); //alert
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        } else {
            int id = Integer.parseInt(idgetter.getText());
            Consultation c = new Consultation();
            c.setDate_consultation(Date.valueOf(dateUp.getValue()));
            c.setNom(nomUp.getText());
            c.setHeure_consultation(Integer.valueOf(heureUp.getText()));
            c.setType_consultation(typeUp.getValue());
            c.setUtilisateur_id(st.getIdConsultationByRole(userUp.getValue()));
            sc.modifier(id, c);
            System.out.println(c.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("The Consultation has been successfully updated.");
            alert.showAndWait();
            recherche_avance();
            envoyercodesms();
        }
    }

    @FXML
    private void goToStat(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("Stat.fxml"));
            stat.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
