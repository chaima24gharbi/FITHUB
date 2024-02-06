/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Fiche;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import services.FicheService;
import services.MyListener;
import services.MyListenerF;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class BackConsultationController implements Initializable {

    @FXML
    private Hyperlink cons;
    @FXML
    private Hyperlink fiche;
    @FXML
    private GridPane grid;
    FicheService sc = new FicheService();
    List<Fiche> events;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    MyListenerF myListener;
    public void refresh(List<Fiche> events){
        grid.getChildren().clear();
        events = sc.afficherListe();
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
