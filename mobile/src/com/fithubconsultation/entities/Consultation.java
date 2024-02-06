package com.fithubconsultation.entities;

import com.fithubconsultation.utils.DateUtils;
import com.fithubconsultation.utils.Statics;

import java.util.Date;

public class Consultation implements Comparable<Consultation> {

    private int id;
    private Utilisateur utilisateur;
    private Fiche fiche;
    private Date date;
    private Date heure;
    private String type;
    private String nom;

    public Consultation() {
    }

    public Consultation(int id, Utilisateur utilisateur, Fiche fiche, Date date, Date heure, String type, String nom) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.fiche = fiche;
        this.date = date;
        this.heure = heure;
        this.type = type;
        this.nom = nom;
    }

    public Consultation(Utilisateur utilisateur, Fiche fiche, Date date, Date heure, String type, String nom) {
        this.utilisateur = utilisateur;
        this.fiche = fiche;
        this.date = date;
        this.heure = heure;
        this.type = type;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Fiche getFiche() {
        return fiche;
    }

    public void setFiche(Fiche fiche) {
        this.fiche = fiche;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Override
    public int compareTo(Consultation consultation) {
        switch (Statics.compareVar) {
            case "Utilisateur":
                return this.getUtilisateur().getNom().compareTo(consultation.getUtilisateur().getNom());
            case "Fiche":
                return this.getFiche().getNom().compareTo(consultation.getFiche().getNom());
            case "Date":
                DateUtils.compareDates(this.getDate(), consultation.getDate());
            case "Heure":
                DateUtils.compareDates(this.getHeure(), consultation.getHeure());
            case "Type":
                return this.getType().compareTo(consultation.getType());
            case "Nom":
                return this.getNom().compareTo(consultation.getNom());

            default:
                return 0;
        }
    }

}