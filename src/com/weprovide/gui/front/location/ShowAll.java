package com.weprovide.gui.front.location;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import entites.Location;
import static com.weprovide.gui.back.location.ShowAll.currentLocation;
import services.LocationService;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Location currentLocation = null;
    Form previous;
    ArrayList<Component> componentModels;
    Label maisonLabel, voitureLabel, dateDebutLabel, dateFinLabel;
    Container btnsContainer;
    Button addBtn;

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
        addBtn = new Button("Louer");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);

        ArrayList<Location> listLocations = LocationService.getInstance().getAll();

        if (listLocations.size() > 0) {
            for (Location location : listLocations) {
                Component model = makeLocationModel(location);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
  addBtn.addActionListener(action -> {
            currentLocation = null;
            new com.weprovide.gui.front.location.Manage(this).show();
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


        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> share(location));

        btnsContainer.add(BorderLayout.CENTER, btnAfficherScreenshot);

        locationModel.add(btnsContainer);

        return locationModel;
    }

    // share api
    private void share(Location location) {
        Form form = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        form.add(makeModelWithoutButtons(location));
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
        Form screenShotForm = new Form("Partager location", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(location.toString());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        screenShotForm.show();
    }

}