/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author hp
 */
public class Fiche {
    private int id;
    private String description_fiche;
    private String nom_fiche ;
    private String category;
    private int consultation_id;
    
    public Fiche() {
    }

    public Fiche(String descriptionFiche, String nom, String category,int consultation_id) {
        this.description_fiche = descriptionFiche;
        this.nom_fiche  = nom;
        this.category = category;
        this.consultation_id=consultation_id;
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
     * @return the descriptionFiche
     */
    public String getDescriptionFiche() {
        return description_fiche;
    }

    /**
     * @param descriptionFiche the descriptionFiche to set
     */
    public void setDescriptionFiche(String descriptionFiche) {
        this.description_fiche = descriptionFiche;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom_fiche ;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom_fiche  = nom;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the consultation
     */
    public int getConsultation() {
        return consultation_id;
    }

    @Override
    public String toString() {
        return "Fiche{" + "id=" + id + ", description_fiche=" + description_fiche + ", nom_fiche=" + nom_fiche + ", category=" + category + ", consultation_id=" + consultation_id + '}';
    }

    /**
     * @param consultation the consultation to set
     */
    public void setConsultation(int consultation) {
        this.consultation_id = consultation;
    }
}
