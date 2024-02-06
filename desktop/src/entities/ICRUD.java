/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hp
 */
public interface ICRUD<T> {
    public void ajouter(T c);
    public void ajouterr(T c);
    public void modifier(int i,T c);
    public void supprimer(int i);
    public List<T> afficherListe();
     public List<T> GetbyId (int id);
    public T GetId (int id);
}
