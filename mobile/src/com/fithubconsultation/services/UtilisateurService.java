package com.fithubconsultation.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.fithubconsultation.entities.Utilisateur;
import com.fithubconsultation.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UtilisateurService {

    public static UtilisateurService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Utilisateur> listUtilisateurs;

    private UtilisateurService() {
        cr = new ConnectionRequest();
    }

    public static UtilisateurService getInstance() {
        if (instance == null) {
            instance = new UtilisateurService();
        }
        return instance;
    }

    public ArrayList<Utilisateur> getAll() {
        listUtilisateurs = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/utilisateur");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listUtilisateurs = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listUtilisateurs;
    }

    private ArrayList<Utilisateur> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Utilisateur utilisateur = new Utilisateur(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("nom")

                );

                listUtilisateurs.add(utilisateur);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listUtilisateurs;
    }
}
