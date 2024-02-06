/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Consultation;
import entities.ICRUD;
import entities.Utilisateur;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
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
public class UtilisateurService implements ICRUD<Utilisateur> {

    public Connection cnx;
    public Statement stm;

    public UtilisateurService() {
        cnx = MyDB.getInstance().getCnx();
        try {
            stm = cnx.createStatement();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void ajouter(Utilisateur c) {
        String req = "INSERT INTO `consultation`( `nom`) VALUES ('" + c.getNom() + "')";
        try {
            stm.executeUpdate(req);
            System.err.println("Utilisateur ajoutée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void ajouterr(Utilisateur c) {
        String req = "INSERT INTO `consultation`( `nom`) VALUES (?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, c.getNom());
            ps.executeUpdate(req);
            System.err.println("Utilisateur ajoutée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public List<Utilisateur> afficherListe() {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        try {
            stm = cnx.createStatement();
            String req = "SELECT * FROM `utilisateur`";

            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                Utilisateur c = new Utilisateur(rs.getInt("id"),
                        rs.getString("Nom")
                );
                utilisateurs.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utilisateurs;
    }

    public Utilisateur getById(int id) throws SQLException {
        Utilisateur utilisateur = null;
        String query = "SELECT * FROM utilisateur WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    utilisateur = new Utilisateur();
                    utilisateur.setId(resultSet.getInt("id"));
                    utilisateur.setNom(resultSet.getString("nom"));
                    // Set other properties here
                }
            }
        }
        return utilisateur;
    }

    @Override
    public void modifier(int i, Utilisateur c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimer(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Utilisateur> GetbyId(int id) {
        List<Utilisateur> lebyid = new ArrayList<>();
        try {
            String query = "Select * FROM utilisateur WHERE id=" + id;
            Statement st;
            st = cnx.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Utilisateur c = new Utilisateur();
                c.setId(rs.getInt("id"));
                c.setRole(rs.getString("roles"));
                c.setNom(rs.getString("nom"));
                lebyid.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lebyid;
    }

    @Override
    public Utilisateur GetId(int id) {
        Utilisateur c = new Utilisateur();
        try {
            String query = "Select * FROM utilisateur WHERE id=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setRole(rs.getString("roles"));
                c.setNom(rs.getString("nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public Utilisateur GetIdRole() {
        Utilisateur c = new Utilisateur();
        try {
            String query = "Select * FROM utilisateur WHERE roles= '[\"ROLE_NUTRISIONNISTE \"]' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setRole(rs.getString("roles"));
                c.setNom(rs.getString("nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

public List<String> GetIdRoles() {
List<String> utilisateurs = new ArrayList<String>();
try {
stm = cnx.createStatement();
String req = "SELECT nom FROM utilisateur WHERE roles = 'ROLE_NUTRISIONNISTE'";
ResultSet rs = stm.executeQuery(req);
    while (rs.next()) {
        String nom = rs.getString("nom");
        utilisateurs.add(nom);
    }
} catch (SQLException ex) {
    Logger.getLogger(UtilisateurService.class.getName()).log(Level.SEVERE, null, ex);
}
return utilisateurs;
   }

    public List<String> getAllUser() {
        return afficherListe().
                stream().
                map(type -> type.getNom()).collect(Collectors.toList());
    }

    public String getRoleById(int id) {
        return GetId(id).getNom();
    }

    public int getIdConsultationByRole(String nom) {
        Utilisateur t = new Utilisateur();
        t = afficherListe().stream().filter(type -> type.getNom().equals(nom))
                .findFirst().orElse(null);
        System.out.println(t);
        return t.getId();
    }

}
