package com.weprovide.gui.back.voiture;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entites.Voiture;
import services.VoitureService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Voiture currentVoiture = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label marqueLabel, couleurLabel, dateAjoutLabel, prixLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Voitures", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Voiture> listVoitures = VoitureService.getInstance().getAll();
        componentModels = new ArrayList<>();

        // recherche
        searchTF = new TextField("", "Chercher voiture par marque");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Voiture voiture : listVoitures) {
                if (voiture.getMarque().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeVoitureModel(voiture);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);

        if (listVoitures.size() > 0) {
            for (Voiture voiture : listVoitures) {
                Component model = makeVoitureModel(voiture);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentVoiture = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Voiture voiture) {
        Container voitureModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        voitureModel.setUIID("containerRounded");


        marqueLabel = new Label("Marque : " + voiture.getMarque());
        marqueLabel.setUIID("labelDefault");

        couleurLabel = new Label("Couleur : " + voiture.getCouleur());
        couleurLabel.setUIID("labelDefault");

        dateAjoutLabel = new Label("DateAjout : " + new SimpleDateFormat("dd-MM-yyyy").format(voiture.getDateAjout()));
        dateAjoutLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + voiture.getPrix());
        prixLabel.setUIID("labelDefault");


        voitureModel.addAll(

                marqueLabel, couleurLabel, dateAjoutLabel, prixLabel
        );

        return voitureModel;
    }

    private Component makeVoitureModel(Voiture voiture) {

        Container voitureModel = makeModelWithoutButtons(voiture);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonMain");
        editBtn.addActionListener(action -> {
            currentVoiture = voiture;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce voiture ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = VoitureService.getInstance().delete(voiture.getId());

                if (responseCode == 200) {
                    currentVoiture = null;
                    dlg.dispose();
                    voitureModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du voiture. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        voitureModel.add(btnsContainer);

        return voitureModel;
    }

}