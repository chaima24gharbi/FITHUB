package com.fithubconsultation.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.fithubconsultation.entities.Consultation;
import com.fithubconsultation.entities.Fiche;
import com.fithubconsultation.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FicheService {

    public static FicheService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Fiche> listFiches;


    private FicheService() {
        cr = new ConnectionRequest();
    }

    public static FicheService getInstance() {
        if (instance == null) {
            instance = new FicheService();
        }
        return instance;
    }

    public ArrayList<Fiche> getAll() {
        listFiches = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/fiche");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listFiches = getList();
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

        return listFiches;
    }

    private ArrayList<Fiche> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Fiche fiche = new Fiche(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("descriptionFiche"),
                        (String) obj.get("nom"),
                        (String) obj.get("category"),
                        makeConsultation((Map<String, Object>) obj.get("consultation"))
                );

                listFiches.add(fiche);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listFiches;
    }
    
        public Consultation makeConsultation(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Consultation consultation = new Consultation();
        consultation.setId((int) Float.parseFloat(obj.get("id").toString()));
        consultation.setNom((String) obj.get("nom"));
        return consultation;
    }

    public int add(Fiche fiche) {
        return manage(fiche, false);
    }

    public int edit(Fiche fiche) {
        return manage(fiche, true);
    }

    public int manage(Fiche fiche, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/fiche/edit");
            cr.addArgument("id", String.valueOf(fiche.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/fiche/add");
        }

        cr.addArgument("descriptionFiche", fiche.getDescriptionFiche());
        cr.addArgument("nom", fiche.getNom());
        cr.addArgument("category", fiche.getCategory());
        System.out.println(fiche.getConsultation().getId());
        cr.addArgument("consultation", String.valueOf(fiche.getConsultation().getId()));


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int ficheId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/fiche/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(ficheId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
