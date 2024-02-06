package com.fithubconsultation.gui.front.fiche;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.fithubconsultation.entities.Fiche;
import com.fithubconsultation.services.FicheService;
import com.fithubconsultation.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ShowAll extends Form {

    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");

    public static Fiche currentFiche = null;
    Button addBtn;

    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public ShowAll(Form previous) {
        super("Fiches", new BoxLayout(BoxLayout.Y_AXIS));
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

        ArrayList<Fiche> listFiches = FicheService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("DescriptionFiche", "Nom", "Category").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listFiches);
            for (Fiche fiche : listFiches) {
                Component model = makeFicheModel(fiche);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listFiches.size() > 0) {
            for (Fiche fiche : listFiches) {
                Component model = makeFicheModel(fiche);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentFiche = null;
            new Manage(this).show();
        });

    }

    Label descriptionFicheLabel, nomLabel, categoryLabel, consultationLabel;

    private Container makeModelWithoutButtons(Fiche fiche) {
        Container ficheModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ficheModel.setUIID("containerRounded");
        this.setBgImage(theme.getImage("hero-bg.jpg"));

        descriptionFicheLabel = new Label("DescriptionFiche : " + fiche.getDescriptionFiche());
        descriptionFicheLabel.setUIID("labelDefault");

        nomLabel = new Label("Nom : " + fiche.getNom());
        nomLabel.setUIID("labelDefault");

        categoryLabel = new Label("Category : " + fiche.getCategory());
        categoryLabel.setUIID("labelDefault");

        if (fiche.getConsultation() != null) {
            consultationLabel = new Label("Consultation : " + fiche.getConsultation().getNom());
            consultationLabel.setUIID("labelDefault");
        }

        ficheModel.addAll(
                descriptionFicheLabel, nomLabel, categoryLabel
        );

        if (fiche.getConsultation() != null) {
            ficheModel.addAll(
                    consultationLabel
            );
        }

        return ficheModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeFicheModel(Fiche fiche) {

        Container ficheModel = makeModelWithoutButtons(fiche);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentFiche = fiche;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce fiche ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = FicheService.getInstance().delete(fiche.getId());

                if (responseCode == 200) {
                    currentFiche = null;
                    dlg.dispose();
                    ficheModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du fiche. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.CENTER, deleteBtn);

        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.setUIID("buttonWhiteCenter");
        btnAfficherScreenshot.addActionListener(listener -> share(fiche));
        btnsContainer.add(BorderLayout.EAST, btnAfficherScreenshot);

        ficheModel.add(btnsContainer);

        return ficheModel;
    }

    private void share(Fiche fiche) {
        Form form = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        form.add(makeModelWithoutButtons(fiche));
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(
                com.codename1.ui.Display.getInstance().getDisplayWidth(),
                com.codename1.ui.Display.getInstance().getDisplayHeight()
        );
        form.revalidate();
        form.setVisible(true);
        form.paintComponent(screenshot.getGraphics(), true);
        form.removeAll();
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        Form screenShotForm = new Form("Partager fiche", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(fiche.toString());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        screenShotForm.show();
    }

}
