/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Consultation;
import entities.ICRUD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import utils.MyDB;

/**
 *
 * @author hp
 */
public class ConsultationService implements ICRUD<Consultation> {

    public Connection cnx;
    public Statement stm;

    public ConsultationService() {
        cnx = MyDB.getInstance().getCnx();

    }

    @Override
    public void ajouter(Consultation c) {
        try {
            String req = "INSERT INTO `consultation`( `heure_consultation`, "
                    + "`type_consultation`, `nom`, `utilisateur_id`,`date_consultation`) "
                    + "VALUES ('" + c.getHeure_consultation() + "','" + c.getType_consultation() + "','" + c.getNom() + "','" + c.getUtilisateur_id() + "','" + c.getDate_consultation() + "')";
            stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.err.println("Consultation ajoutée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(int id, Consultation c) {
        try {
            String query = "UPDATE consultation SET "
                    + "`heure_consultation`='" + c.getHeure_consultation() + "',"
                    + "`type_consultation`='" + c.getType_consultation() + "',"
                    + "`nom`='" + c.getNom() + "',"
                    + "`utilisateur_id`='" + c.getUtilisateur_id() + "',"
                    + "`date_consultation`='" + c.getDate_consultation()
                    + "' WHERE id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Modification avec succés");

    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM consultation WHERE id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Suppression avec succés");

    }

    @Override
    public List<Consultation> afficherListe() {
        List<Consultation> consultations = new ArrayList<>();
        try (Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM consultation")) {
            while (rs.next()) {
                Consultation c = new Consultation();
                int id = rs.getInt("id");
                System.out.println("Retrieved id from database: " + id);
                c.setId(id);
                c.setHeure_consultation(rs.getInt("heure_consultation"));
                c.setDate_consultation(rs.getDate("date_consultation"));
                c.setUtilisateur_id(rs.getInt("utilisateur_id"));
                c.setType_consultation(rs.getString("type_consultation"));
                c.setNom(rs.getString("nom"));
                consultations.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return consultations;
    }

    @Override
    public void ajouterr(Consultation c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Consultation> GetbyId(int id) {
        List<Consultation> lebyid = new ArrayList<>();
        try {
            String query = "Select * FROM consultation WHERE id=" + id;
            Statement st;
            st = cnx.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Consultation c = new Consultation();
                c.setId(rs.getInt("id"));
                c.setHeure_consultation(rs.getInt("heure_consultation"));
                c.setDate_consultation(rs.getDate("date_consultation"));
                c.setUtilisateur_id(rs.getInt("utilisateur_id"));
                c.setType_consultation(rs.getString("type_consultation"));
                c.setNom(rs.getString("nom"));
                lebyid.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lebyid;
    }

    @Override
    public Consultation GetId(int id) {
        Consultation c = new Consultation();
        try {
            String query = "Select * FROM consultation WHERE id=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setHeure_consultation(rs.getInt("heure_consultation"));
                c.setDate_consultation(rs.getDate("date_consultation"));
                c.setUtilisateur_id(rs.getInt("utilisateur_id"));
                c.setType_consultation(rs.getString("type_consultation"));
                c.setNom(rs.getString("nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public List<String> getAllConsultation() {
        return afficherListe().
                stream().
                map(type -> type.getNom()).collect(Collectors.toList());
    }

    public String getConsultationById(int id) {
        return GetId(id).getNom();
    }

    public int getIdByConsultation(String domain) {
        Consultation t = new Consultation();
        t = afficherListe().stream().filter(type -> type.getNom().equals(domain))
                .findFirst().orElse(null);
        System.out.println(t);
        return t.getId();
    }

}
