/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import com.mysql.jdbc.Constants;
import entities.Consultation;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import services.ConsultationService;
import services.MyListener;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class HomeController implements Initializable {

    @FXML
    private Hyperlink cons;
    @FXML
    private Hyperlink fiche;
    @FXML
    private GridPane grid;
    ConsultationService sc = new ConsultationService();
    List<Consultation> events;
    @FXML
    private Button btnUsers;
     private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#000000");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        liens = new Button[]{
            btnUsers,
        };


        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            //Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnUsers.setTextFill(Color.WHITE);
        refresh(events);
    }    

    @FXML
    private void goCons(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("home.fxml"));
            cons.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goFiche(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("backFiche.fxml"));
            fiche.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    MyListener myListener;
    public void refresh(List<Consultation> events){
        grid.getChildren().clear();
        events = sc.afficherListe();
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
                grid.add(anchorePane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setHgap(320);

                GridPane.setMargin(anchorePane, new Insets(40));
            } catch (IOException ex) {
                Logger.getLogger(ListConsultationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }}

    @FXML
    private void logout(ActionEvent event) {
        //MainApp.getInstance().logout();
    }

   
}
