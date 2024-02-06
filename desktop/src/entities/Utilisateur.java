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
public class Utilisateur {

   
    private int id;
    private String nom;
    private String roles;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Utilisateur(String nom) {
        this.nom = nom;
    }

    Utilisateur(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
 /**
     * @return the role
     */
    public String getRole() {
        return roles;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.roles = role;
    }
    
}
