/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Array;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author hp
 */
public class Consultation {

    private int id;
    private Utilisateur utilisateur;
    private int fiche;
    private Date date_consultation;
    private int heure_consultation;
    private String type_consultation;
    private String nom;
    private int utilisateur_id;

    public Consultation() {
    }

    public Consultation(int id, Utilisateur utilisateur, int fiche, Date date, int heure, String type, String nom) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.fiche = fiche;
        this.date_consultation = date;
        this.heure_consultation = heure;
        this.type_consultation = type;
        this.nom = nom;
    }

    public Consultation(int heure, String type, String nom, Date date, int utilisateur_id) {
        this.heure_consultation = heure;
        this.date_consultation = date;
        this.type_consultation = type;
        this.nom = nom;
        this.utilisateur_id = utilisateur_id;
    }
 public Consultation(String nom, String type_consultation, Utilisateur utilisateur, Date date_consultation, int heure_consultation) {
        this.nom = nom;
        this.type_consultation = type_consultation;
        this.utilisateur = utilisateur;
        this.date_consultation = date_consultation;
        this.heure_consultation = heure_consultation;
    }
    public Consultation(int id, Utilisateur utilisateur, Date date, int heure, String type, String nom) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.date_consultation = date;
        this.heure_consultation = heure;
        this.type_consultation = type;
        this.nom = nom;
    }


    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }



    /**
     * @return the fiche
     */
    public int getFiche() {
        return fiche;
    }

    /**
     * @param fiche the fiche to set
     */
    public void setFiche(int fiche) {
        this.fiche = fiche;
    }

    /**
     * @return the nom_utilisateur
     */
    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + getId() + ", utilisateur=" + getUtilisateur() + ", fiche=" + fiche + ", date_consultation=" + getDate_consultation() + ", heure_consultation=" + getHeure_consultation() + ", type_consultation=" + getType_consultation() + ", nom=" + nom + ", utilisateur_id=" + utilisateur_id + '}';
    }

    /**
     * @param nom_utilisateur the nom_utilisateur to set
     */
    public void setUtilisateur_id(int nom_utilisateur) {
        this.utilisateur_id = nom_utilisateur;
    }

    /**
     * @return the date_consultation
     */
    public Date getDate_consultation() {
        return date_consultation;
    }

    /**
     * @param date_consultation the date_consultation to set
     */
    public void setDate_consultation(Date date_consultation) {
        this.date_consultation = date_consultation;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur the utilisateur to set
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * @return the heure_consultation
     */
    public int getHeure_consultation() {
        return heure_consultation;
    }

    /**
     * @param heure_consultation the heure_consultation to set
     */
    public void setHeure_consultation(int heure_consultation) {
        this.heure_consultation = heure_consultation;
    }

    /**
     * @return the type_consultation
     */
    public String getType_consultation() {
        return type_consultation;
    }

    /**
     * @param type_consultation the type_consultation to set
     */
    public void setType_consultation(String type_consultation) {
        this.type_consultation = type_consultation;
    }
}
