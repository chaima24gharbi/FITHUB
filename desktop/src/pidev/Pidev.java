/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import entities.Consultation;
import entities.Fiche;
import entities.Utilisateur;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import services.ConsultationService;
import services.FicheService;
import services.UtilisateurService;
import utils.MyDB;

/**
 *
 * @author hp
 */
public class Pidev {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       // MyDB db = new MyDB();
        
        Utilisateur utilisateur = new Utilisateur();
        UtilisateurService utilisateur1 = new UtilisateurService();
        Consultation c1 = new Consultation(15,"cabinet","consultation1",new Date(2023,12,31),2);
        //Fiche f1 = new Fiche("fiche1","f1","New",11);
        ConsultationService ps = new ConsultationService();
        FicheService pf = new FicheService();
        //pf.modifier(3,f1);
        //System.out.println(utilisateur1.GetIdRoles());
        //ps.supprimer(12);
        System.out.println(c1.getNom());
        
    }
    
}
