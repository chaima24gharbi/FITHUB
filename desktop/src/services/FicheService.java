/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Fiche;
import entities.ICRUD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;

/**
 *
 * @author hp
 */
public class FicheService implements ICRUD<Fiche> {

    public Connection cnx;
    public Statement stm;

    public FicheService() {
        cnx = MyDB.getInstance().getCnx();

    }

    @Override
    public void ajouter(Fiche c) {
        try {
            String req = "INSERT INTO `fiche`( `description_fiche`, "
                    + "`nom_fiche`, `category`,`consultation_id`) "
                    + "VALUES ('" + c.getDescriptionFiche() + "','" + c.getNom() + "','" + c.getCategory() + "','" + c.getConsultation() + "')";
            stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.err.println("Fiche ajoutée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(int id, Fiche c) {
        try {
            String query = "UPDATE fiche SET "
                    + "`description_fiche`='" + c.getDescriptionFiche() + "',"
                    + "`nom_fiche`='" + c.getNom() + "',"
                    + "`category`='" + c.getCategory() + "',"
                    + "`consultation_id`='" + c.getConsultation()
                    + "' WHERE id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(FicheService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Modification avec succés");

    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM fiche WHERE id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(FicheService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Suppression avec succés");

    }

    @Override
    public List<Fiche> afficherListe() {
        List<Fiche> fiches = new ArrayList<>();
        try {
            String req = "SELECT * FROM `fiche`";
            stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
                Fiche c = new Fiche();
                int id = rs.getInt("id");
                System.out.println("Retrieved id from database: " + id);
                c.setId(id);
                c.setDescriptionFiche(rs.getString("description_fiche"));
                c.setNom(rs.getString("nom_fiche"));
                c.setCategory(rs.getString("category"));
                c.setConsultation(rs.getInt("consultation_id"));
                fiches.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FicheService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("erreur ici");
        return fiches;
    }

    @Override
    public void ajouterr(Fiche c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Fiche> GetbyId(int id) {
        List<Fiche> lebyid = new ArrayList<>();
        try {
            String query = "Select * FROM fiche WHERE id=" + id;
            Statement st;
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Fiche c = new Fiche();
                c.setId(rs.getInt("id"));
                c.setDescriptionFiche(rs.getString("description_fiche"));
                c.setNom(rs.getString("nom_fiche"));
                c.setCategory(rs.getString("category"));
                c.setNom(rs.getString("nom"));
                c.setConsultation(rs.getInt("consultation_id"));
                lebyid.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FicheService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lebyid;
    }

    @Override
    public Fiche GetId(int id) {
        Fiche c = new Fiche();
        try {
            String query = "Select * FROM fiche WHERE id=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setDescriptionFiche(rs.getString("description_fiche"));
                c.setNom(rs.getString("nom_fiche"));
                c.setCategory(rs.getString("category"));
                c.setNom(rs.getString("nom"));
                c.setConsultation(rs.getInt("consultation_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FicheService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
