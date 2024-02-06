package com.fithubconsultation.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.fithubconsultation.entities.Consultation;
import com.fithubconsultation.entities.Fiche;
import com.fithubconsultation.entities.Utilisateur;
import com.fithubconsultation.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsultationService {

    public static ConsultationService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Consultation> listConsultations;


    private ConsultationService() {
        cr = new ConnectionRequest();
    }

    public static ConsultationService getInstance() {
        if (instance == null) {
            instance = new ConsultationService();
        }
        return instance;
    }

    public ArrayList<Consultation> getAll() {
        listConsultations = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/consultation");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listConsultations = getList();
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

        return listConsultations;
    }

    private ArrayList<Consultation> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Consultation consultation = new Consultation(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur")),
                        makeFiche((Map<String, Object>) obj.get("fiche")),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("date")),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("heure")),
                        (String) obj.get("type"),
                        (String) obj.get("nom")

                );

                listConsultations.add(consultation);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listConsultations;
    }

    public Utilisateur makeUtilisateur(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId((int) Float.parseFloat(obj.get("id").toString()));
        utilisateur.setNom((String) obj.get("nom"));
        return utilisateur;
    }

    public Fiche makeFiche(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Fiche fiche = new Fiche();
        fiche.setId((int) Float.parseFloat(obj.get("id").toString()));
        fiche.setNom((String) obj.get("nomFiche"));
        return fiche;
    }

    public int add(Consultation consultation) {
        return manage(consultation, false);
    }

    public int edit(Consultation consultation) {
        return manage(consultation, true);
    }

    public int manage(Consultation consultation, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/consultation/edit");
            cr.addArgument("id", String.valueOf(consultation.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/consultation/add");
        }

        cr.addArgument("utilisateur", String.valueOf(consultation.getUtilisateur().getId()));
        cr.addArgument("date", new SimpleDateFormat("dd-MM-yyyy").format(consultation.getDate()));
        cr.addArgument("heure", new SimpleDateFormat("dd-MM-yyyy").format(consultation.getHeure()));
        cr.addArgument("type", consultation.getType());
        cr.addArgument("nom", consultation.getNom());


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

    public int delete(int consultationId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/consultation/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(consultationId));

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
