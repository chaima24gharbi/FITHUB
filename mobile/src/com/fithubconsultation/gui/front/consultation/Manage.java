package com.fithubconsultation.gui.front.consultation;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.fithubconsultation.entities.Consultation;
import com.fithubconsultation.entities.Fiche;
import com.fithubconsultation.entities.Utilisateur;
import com.fithubconsultation.services.ConsultationService;
import com.fithubconsultation.services.FicheService;
import com.fithubconsultation.services.UtilisateurService;
import com.fithubconsultation.utils.AlertUtils;

import java.util.ArrayList;

public class Manage extends Form {

    Consultation currentConsultation;

    TextField nomTF;
    Label typeLabel;
    Label nomLabel;
    PickerComponent dateTF;
    PickerComponent heureTF;

    PickerComponent categoryPC;
    Resources theme = UIManager.initFirstTheme("/theme");

    ArrayList<Utilisateur> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateur selectedUtilisateur = null;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentConsultation == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentConsultation = ShowAll.currentConsultation;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] utilisateurStrings;
        int utilisateurIndex;
        utilisateurPC = PickerComponent.createStrings("").label("Utilisateur");
        listUtilisateurs = UtilisateurService.getInstance().getAll();
        utilisateurStrings = new String[listUtilisateurs.size()];
        utilisateurIndex = 0;
        for (Utilisateur utilisateur : listUtilisateurs) {
            utilisateurStrings[utilisateurIndex] = utilisateur.getNom();
            utilisateurIndex++;
        }
        if (listUtilisateurs.size() > 0) {
            utilisateurPC.getPicker().setStrings(utilisateurStrings);
            utilisateurPC.getPicker().addActionListener(l -> selectedUtilisateur = listUtilisateurs.get(utilisateurPC.getPicker().getSelectedStringIndex()));
        } else {
            utilisateurPC.getPicker().setStrings("");
        }

        dateTF = PickerComponent.createDate(null).label("Date");

        heureTF = PickerComponent.createDate(null).label("Heure");

        typeLabel = new Label("Type : ");
        typeLabel.setUIID("labelDefault");
        typeLabel.getAllStyles().setFgColor(0xFFFFFF);
        categoryPC = PickerComponent.createStrings("").label("Type");

        String[] strs = new String[2];
        strs[0] = "A distance";
        strs[1] = "Cabinet";

        categoryPC.getPicker().setStrings(strs);

        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomLabel.getAllStyles().setFgColor(0xFFFFFF);
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");

        if (currentConsultation == null) {

            manageButton = new Button("Ajouter");
        } else {

            if (currentConsultation.getType().equals("A distance")) {
                categoryPC.getPicker().setSelectedString(strs[0]);
            }

            if (currentConsultation.getType().equals("Cabinet")) {
                categoryPC.getPicker().setSelectedString(strs[1]);
            }

            dateTF.getPicker().setDate(currentConsultation.getDate());
            heureTF.getPicker().setDate(currentConsultation.getHeure());
            nomTF.setText(currentConsultation.getNom());

            utilisateurPC.getPicker().setSelectedString(currentConsultation.getUtilisateur().getNom());
            selectedUtilisateur = currentConsultation.getUtilisateur();

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //container.setUIID("containerRounded");
        this.setBgImage(theme.getImage("hero-bg.jpg"));

        container.addAll(
                dateTF,
                heureTF,
                typeLabel, categoryPC,
                nomLabel, nomTF,
                utilisateurPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentConsultation == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ConsultationService.getInstance().add(
                            new Consultation(
                                    selectedUtilisateur,
                                    null,
                                    dateTF.getPicker().getDate(),
                                    heureTF.getPicker().getDate(),
                                    categoryPC.getPicker().getSelectedString(),
                                    nomTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Consultation ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de consultation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ConsultationService.getInstance().edit(
                            new Consultation(
                                    currentConsultation.getId(),
                                    selectedUtilisateur,
                                    null,
                                    dateTF.getPicker().getDate(),
                                    heureTF.getPicker().getDate(),
                                    categoryPC.getPicker().getSelectedString(),
                                    nomTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Consultation modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de consultation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (dateTF.getPicker().getDate() == null) {
            Dialog.show("Alert", "Veuillez saisir la date", new Command("Ok"));
            return false;
        }

        if (heureTF.getPicker().getDate() == null) {
            Dialog.show("Alert", "Veuillez saisir la heure", new Command("Ok"));
            return false;
        }

        if (nomTF.getText().equals("")) {
            Dialog.show("Alert", "Ajouter nom", new Command("Ok"));
            return false;
        }

        if (selectedUtilisateur == null) {
            Dialog.show("Alert", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }

        return true;
    }
}
