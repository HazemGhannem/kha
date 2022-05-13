package com.weprovide.gui.back.location;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import entites.Location;
import entites.Maison;
import entites.Voiture;
import services.LocationService;
import services.MaisonService;
import services.VoitureService;

import java.util.ArrayList;

public class Manage extends Form {


    Location currentLocation;

    Label dateDebutLabel, dateFinLabel;

    PickerComponent dateDebutTF;
    PickerComponent dateFinTF;
    PickerComponent maisonPC;
    PickerComponent voiturePC;


    Button manageButton;

    Form previous;
    ArrayList<Maison> listMaisons;
    ArrayList<Voiture> listVoitures;
    Maison selectedMaison = null;
    Voiture selectedVoiture = null;

    public Manage(Form previous) {
        super(ShowAll.currentLocation == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentLocation = ShowAll.currentLocation;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        maisonPC = PickerComponent.createStrings("").label("Maison");
        voiturePC = PickerComponent.createStrings("").label("Voiture");

        listMaisons = MaisonService.getInstance().getAll();
        listVoitures = VoitureService.getInstance().getAll();

        String[] strings = new String[listMaisons.size()];
        int i = 0;

        for (Maison maison : listMaisons) {
            strings[i] = maison.getAdresse();
            i++;
        }
        maisonPC.getPicker().setStrings(strings);

        strings = new String[listVoitures.size()];
        i = 0;

        for (Voiture voiture : listVoitures) {
            strings[i] = voiture.getMarque();
            i++;
        }
        voiturePC.getPicker().setStrings(strings);

        dateDebutTF = PickerComponent.createDate(null).label("DateDebut");
        dateFinTF = PickerComponent.createDate(null).label("DateFin");


        if (currentLocation == null) {


            manageButton = new Button("Ajouter");
        } else {

            dateDebutTF.getPicker().setDate(currentLocation.getDateDebut());
            dateFinTF.getPicker().setDate(currentLocation.getDateFin());

            maisonPC.getPicker().setSelectedString(currentLocation.getMaison().getAdresse());
            voiturePC.getPicker().setSelectedString(currentLocation.getVoiture().getMarque());

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                dateDebutTF,
                dateFinTF,
                maisonPC, voiturePC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentLocation == null) {

            manageButton.addActionListener(action -> {

                for (Maison maison : listMaisons) {
                    if (maisonPC.getPicker().getSelectedString().equals(maison.getAdresse())) {
                        selectedMaison = maison;
                    }
                }

                for (Voiture voiture : listVoitures) {
                    if (voiturePC.getPicker().getSelectedString().equals(voiture.getMarque())) {
                        selectedVoiture = voiture;
                    }
                }

                if (controleDeSaisie()) {
                    int responseCode = LocationService.getInstance().add(
                            new Location(
                                    selectedMaison,
                                    selectedVoiture,
                                    dateDebutTF.getPicker().getDate(),
                                    dateFinTF.getPicker().getDate()
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Location ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de location. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                  for (Maison maison : listMaisons) {
                    if (maisonPC.getPicker().getSelectedString().equals(maison.getAdresse())) {
                        selectedMaison = maison;
                    }
                }

                for (Voiture voiture : listVoitures) {
                    if (voiturePC.getPicker().getSelectedString().equals(voiture.getMarque())) {
                        selectedVoiture = voiture;
                    }
                }

                
                if (controleDeSaisie()) {
                    int responseCode = LocationService.getInstance().edit(
                            new Location(
                                    currentLocation.getId(),
                                    selectedMaison,
                                    selectedVoiture,
                                    dateDebutTF.getPicker().getDate(),
                                    dateFinTF.getPicker().getDate()

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Location modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de location. Code d'erreur : " + responseCode, new Command("Ok"));
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

        if (selectedMaison == null) {
            Dialog.show("Avertissement", "Veuillez choisir une maison", new Command("Ok"));
            return false;
        }

        if (selectedVoiture == null) {
            Dialog.show("Avertissement", "Veuillez choisir une voiture", new Command("Ok"));
            return false;
        }

        if (dateDebutTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateDebut", new Command("Ok"));
            return false;
        }

        if (dateFinTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateFin", new Command("Ok"));
            return false;
        }

        return true;
    }
}