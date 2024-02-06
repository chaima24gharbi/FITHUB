package com.fithubconsultation.gui.front.consultation;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.fithubconsultation.entities.Consultation;
import com.fithubconsultation.services.ConsultationService;
import com.fithubconsultation.utils.Statics;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ShowAll extends Form {

    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");

    public static Consultation currentConsultation = null;
    Button addBtn;


    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public ShowAll(Form previous) {
        super("Consultations", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Consultation> listConsultations = ConsultationService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("Utilisateur", "Date", "Heure", "Type", "Nom").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listConsultations);
            for (Consultation consultation : listConsultations) {
                Component model = makeConsultationModel(consultation);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listConsultations.size() > 0) {
            for (Consultation consultation : listConsultations) {
                Component model = makeConsultationModel(consultation);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentConsultation = null;
            new Manage(this).show();
        });

    }

    Label utilisateurLabel, dateLabel, heureLabel, typeLabel, nomLabel;


    private Container makeModelWithoutButtons(Consultation consultation){
        Container consultationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        consultationModel.setUIID("containerRounded");
        this.setBgImage(theme.getImage("hero-bg.jpg"));
        

        utilisateurLabel = new Label("Utilisateur : " + consultation.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");


        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(consultation.getDate()));
        dateLabel.setUIID("labelDefault");

        heureLabel = new Label("Heure : " + new SimpleDateFormat("dd-MM-yyyy").format(consultation.getHeure()));
        heureLabel.setUIID("labelDefault");

        typeLabel = new Label("Type : " + consultation.getType());
        typeLabel.setUIID("labelDefault");

        nomLabel = new Label("Nom : " + consultation.getNom());
        nomLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + consultation.getUtilisateur().getNom());
        utilisateurLabel.setUIID("labelDefault");


        consultationModel.addAll(

                utilisateurLabel, dateLabel, heureLabel, typeLabel, nomLabel
        );

        return consultationModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeConsultationModel(Consultation consultation) {

        Container consultationModel = makeModelWithoutButtons(consultation);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        this.setBgImage(theme.getImage("hero-bg.jpg"));

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentConsultation = consultation;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce consultation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ConsultationService.getInstance().delete(consultation.getId());

                if (responseCode == 200) {
                    currentConsultation = null;
                    dlg.dispose();
                    consultationModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du consultation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        consultationModel.add(btnsContainer);

        return consultationModel;
    }

}