/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class StatController implements Initializable {

    @FXML
    private PieChart pie;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;
    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> data1 = FXCollections.observableArrayList();
    @FXML
    private PieChart pie2;
    @FXML
    private Hyperlink goToFiche;
    @FXML
    private Hyperlink goToCons;
    @FXML
    private Hyperlink stat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cnx = MyDB.getInstance().getCnx();
        stat();
        statCon();
    }

    private void stat() {
        try {
            String query = "select COUNT(*),`type_consultation` from consultation GROUP BY `type_consultation`;";

            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();
            while (rs.next()) {
                data.add(new PieChart.Data(rs.getString("type_consultation"), rs.getInt("COUNT(*)")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        pie.setLegendSide(Side.LEFT);
        pie.setData(data);
    }

    private void statCon() {
        try {
            String query = "select COUNT(*),`date_consultation` from consultation GROUP BY `date_consultation`;";

            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();
            while (rs.next()) {
                data1.add(new PieChart.Data(rs.getString("date_consultation"), rs.getInt("COUNT(*)")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        pie2.setLegendSide(Side.LEFT);
        pie2.setData(data1);
    }

    @FXML
    private void goToFiche(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("ListFiche.fxml"));
            goToFiche.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void goToCons(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("ListConsultation.fxml"));
            goToCons.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
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
