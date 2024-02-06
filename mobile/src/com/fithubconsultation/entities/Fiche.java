package com.fithubconsultation.entities;

import com.fithubconsultation.utils.Statics;

public class Fiche implements Comparable<Fiche> {

    private int id;
    private String descriptionFiche;
    private String nom;
    private String category;
    private Consultation consultation;
    
    public Fiche() {
    }

    public Fiche(int id, String descriptionFiche, String nom, String category, Consultation consultation) {
        this.id = id;
        this.descriptionFiche = descriptionFiche;
        this.nom = nom;
        this.category = category;
        this.consultation = consultation;
    }

    public Fiche(String descriptionFiche, String nom, String category, Consultation consultation) {
        this.descriptionFiche = descriptionFiche;
        this.nom = nom;
        this.category = category;
        this.consultation = consultation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriptionFiche() {
        return descriptionFiche;
    }

    public void setDescriptionFiche(String descriptionFiche) {
        this.descriptionFiche = descriptionFiche;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    

    @Override
    public String toString() {
        return "Fiche{" +
                "id=" + id +
                ", descriptionFiche='" + descriptionFiche + '\'' +
                ", nom='" + nom + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public int compareTo(Fiche fiche) {
        switch (Statics.compareVar) {
            case "DescriptionFiche":
                return this.getDescriptionFiche().compareTo(fiche.getDescriptionFiche());
            case "Nom":
                return this.getNom().compareTo(fiche.getNom());
            case "Category":
                return this.getCategory().compareTo(fiche.getCategory());

            default:
                return 0;
        }
    }

}