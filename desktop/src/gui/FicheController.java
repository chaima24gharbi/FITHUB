/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Consultation;
import entities.Fiche;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.ConsultationService;
import services.MyListenerF;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class FicheController implements Initializable {

    
    ConsultationService st = new ConsultationService();
    MyListenerF myListener;
    Fiche e;
    @FXML
    private Label ficheDesc;
    @FXML
    private Label ficheCat;
    @FXML
    private Label ficheCons;
    @FXML
    private Label nomFiche;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
     public void remplir(Fiche e, MyListenerF myListener) {

        this.e = e;
        this.myListener = myListener;
        nomFiche.setText(e.getNom()+ "");
        ficheDesc.setText(e.getDescriptionFiche()+ "");
        ficheCat.setText(e.getCategory()+ "");
        ficheCons.setText(st.getConsultationById(e.getConsultation())+ "");
    }

    private void selectionEvenement(MouseEvent event) {
        myListener.onclickListener(e);
    }
}
