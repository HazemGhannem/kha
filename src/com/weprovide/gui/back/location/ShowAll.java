package com.weprovide.gui.back.location;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entites.Location;
import services.LocationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Location currentLocation = null;
    Form previous;
    Button addBtn;

    ArrayList<Component> componentModels;
    Label maisonLabel, voitureLabel, dateDebutLabel, dateFinLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Locations", new BoxLayout(BoxLayout.Y_AXIS));
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

        ArrayList<Location> listLocations = LocationService.getInstance().getAll();
        componentModels = new ArrayList<>();

        if (listLocations.size() > 0) {
            for (Location location : listLocations) {
                Component model = makeLocationModel(location);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentLocation = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Location location) {
        Container locationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        locationModel.setUIID("containerRounded");


        maisonLabel = new Label("Maison : " + location.getMaison());
        maisonLabel.setUIID("labelDefault");

        voitureLabel = new Label("Voiture : " + location.getVoiture());
        voitureLabel.setUIID("labelDefault");

        dateDebutLabel = new Label("DateDebut : " + new SimpleDateFormat("dd-MM-yyyy").format(location.getDateDebut()));
        dateDebutLabel.setUIID("labelDefault");

        dateFinLabel = new Label("DateFin : " + new SimpleDateFormat("dd-MM-yyyy").format(location.getDateFin()));
        dateFinLabel.setUIID("labelDefault");

        maisonLabel = new Label("Maison : " + location.getMaison().getAdresse());
        maisonLabel.setUIID("labelDefault");

        voitureLabel = new Label("Voiture : " + location.getVoiture().getMarque());
        voitureLabel.setUIID("labelDefault");


        locationModel.addAll(

                maisonLabel, voitureLabel, dateDebutLabel, dateFinLabel
        );

        return locationModel;
    }

    private Component makeLocationModel(Location location) {

        Container locationModel = makeModelWithoutButtons(location);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonMain");
        editBtn.addActionListener(action -> {
            currentLocation = location;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce location ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = LocationService.getInstance().delete(location.getId());

                if (responseCode == 200) {
                    currentLocation = null;
                    dlg.dispose();
                    locationModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du location. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        locationModel.add(btnsContainer);

        return locationModel;
    }

}