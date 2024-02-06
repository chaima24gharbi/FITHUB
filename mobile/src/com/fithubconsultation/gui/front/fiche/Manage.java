package com.fithubconsultation.gui.front.fiche;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.fithubconsultation.entities.Consultation;
import com.fithubconsultation.entities.Fiche;
import com.fithubconsultation.services.ConsultationService;
import com.fithubconsultation.services.FicheService;
import com.fithubconsultation.utils.AlertUtils;
import java.util.ArrayList;

public class Manage extends Form {

    Fiche currentFiche;
    Resources theme = UIManager.initFirstTheme("/theme");

    TextField descriptionFicheTF;
    TextField nomTF;
    PickerComponent categoryPC;
    Label descriptionFicheLabel;
    Label nomLabel;
    Label categoryLabel;

    ArrayList<Consultation> listConsultations;
    PickerComponent consultationPC;
    Consultation selectedConsultation = null;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentFiche == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentFiche = ShowAll.currentFiche;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] consultationsStrings;
        int consultationIndex;
        consultationPC = PickerComponent.createStrings("").label("Consultation");
        listConsultations = ConsultationService.getInstance().getAll();
        consultationsStrings = new String[listConsultations.size()];
        consultationIndex = 0;
        for (Consultation consultation : listConsultations) {
            consultationsStrings[consultationIndex] = consultation.getNom();
            consultationIndex++;
        }
        if (listConsultations.size() > 0) {
            consultationPC.getPicker().setStrings(consultationsStrings);
            consultationPC.getPicker().addActionListener(l -> selectedConsultation = listConsultations.get(consultationPC.getPicker().getSelectedStringIndex()));
        } else {
            consultationPC.getPicker().setStrings("");
        }

        descriptionFicheLabel = new Label("DescriptionFiche : ");
        descriptionFicheLabel.setUIID("labelDefault");
        descriptionFicheLabel.getAllStyles().setFgColor(0xFFFFFF);
        descriptionFicheTF = new TextField();
        descriptionFicheTF.setHint("Tapez le descriptionFiche");

        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomLabel.getAllStyles().setFgColor(0xFFFFFF);
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");

        categoryLabel = new Label("Category : ");
        categoryLabel.setUIID("labelDefault");
        categoryLabel.getAllStyles().setFgColor(0xFFFFFF);
        categoryPC = PickerComponent.createStrings("").label("Category");

        String[] strs = new String[2];
        strs[0] = "Follow up";
        strs[1] = "New";

        categoryPC.getPicker().setStrings(strs);

        if (currentFiche == null) {

            manageButton = new Button("Ajouter");
        } else {
            descriptionFicheTF.setText(currentFiche.getDescriptionFiche());
            nomTF.setText(currentFiche.getNom());

            if (currentFiche.getCategory().equals("Follow up")) {
                categoryPC.getPicker().setSelectedString(strs[0]);
            }

            if (currentFiche.getCategory().equals("New")) {
                categoryPC.getPicker().setSelectedString(strs[1]);
            }

            if (currentFiche.getConsultation() != null) {
                consultationPC.getPicker().setSelectedString(currentFiche.getConsultation().getNom());
                selectedConsultation = currentFiche.getConsultation();
            }

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //container.setUIID("containerRounded");
        this.setBgImage(theme.getImage("hero-bg.jpg"));

        container.addAll(
                descriptionFicheLabel, descriptionFicheTF,
                nomLabel, nomTF,
                categoryLabel, categoryPC,
                consultationPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentFiche == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = FicheService.getInstance().add(
                            new Fiche(
                                    descriptionFicheTF.getText(),
                                    nomTF.getText(),
                                    categoryPC.getPicker().getSelectedString(),
                                    selectedConsultation
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Fiche ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de fiche. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = FicheService.getInstance().edit(
                            new Fiche(
                                    currentFiche.getId(),
                                    descriptionFicheTF.getText(),
                                    nomTF.getText(),
                                    categoryPC.getPicker().getSelectedString(),
                                    selectedConsultation
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Fiche modifié avec succes");
                        showBackAndRefresh();
                    } else if (responseCode == 250) {
                        Dialog.show("Erreur", "Fiche déja assigné", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de fiche. Code d'erreur : " + responseCode, new Command("Ok"));
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

        if (descriptionFicheTF.getText().equals("")) {
            Dialog.show("Alert", "DescriptionFiche vide", new Command("Ok"));
            return false;
        }

        if (nomTF.getText().equals("")) {
            Dialog.show("Alert", "Ajouter nom", new Command("Ok"));
            return false;
        }


        if (selectedConsultation == null) {
            Dialog.show("Alert", "Veuillez choisir un fiche", new Command("Ok"));
            return false;
        }

        return true;
    }
}
