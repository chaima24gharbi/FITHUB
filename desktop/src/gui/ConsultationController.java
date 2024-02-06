/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Consultation;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.MyListener;
import services.UtilisateurService;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ConsultationController implements Initializable {

    @FXML
    private Label nomCons;
    @FXML
    private Label dateCons;
    @FXML
    private Label heureCons;
    @FXML
    private Label typeCons;
    @FXML
    private Label userCons;
    UtilisateurService st = new UtilisateurService();
    MyListener myListener;
    Consultation e;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void remplir(Consultation e, MyListener myListener) {

        this.e = e;
        this.myListener = myListener;
        dateCons.setText(e.getDate_consultation() + "");
        heureCons.setText(e.getHeure_consultation() + "");
        nomCons.setText(e.getNom() + "");
        typeCons.setText(e.getType_consultation() + "");
        userCons.setText(st.getRoleById(e.getUtilisateur_id()) + "");
    }

    @FXML
    private void selectionEvenement(MouseEvent event) {
        myListener.onclickListener(e);
    }

}
