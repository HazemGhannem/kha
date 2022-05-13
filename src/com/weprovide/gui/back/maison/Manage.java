package com.weprovide.gui.back.maison;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import entites.Maison;
import services.MaisonService;

public class Manage extends Form {


    Maison currentMaison;

    Label surfaceLabel, nbreChambreLabel, dateAjoutLabel, adresseLabel, prixLabel;
    TextField
            surfaceTF,
            nbreChambreTF,
            adresseTF,
            prixTF;
    PickerComponent dateAjoutTF;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentMaison == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentMaison = ShowAll.currentMaison;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        surfaceLabel = new Label("Surface : ");
        surfaceLabel.setUIID("labelDefault");
        surfaceTF = new TextField();
        surfaceTF.setHint("Tapez le surface");

        nbreChambreLabel = new Label("NbreChambre : ");
        nbreChambreLabel.setUIID("labelDefault");
        nbreChambreTF = new TextField();
        nbreChambreTF.setHint("Tapez le nbreChambre");
        dateAjoutTF = PickerComponent.createDate(null).label("DateAjout");

        adresseLabel = new Label("Adresse : ");
        adresseLabel.setUIID("labelDefault");
        adresseTF = new TextField();
        adresseTF.setHint("Tapez le adresse");

        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");


        if (currentMaison == null) {


            manageButton = new Button("Ajouter");
        } else {


            surfaceTF.setText(String.valueOf(currentMaison.getSurface()));
            nbreChambreTF.setText(String.valueOf(currentMaison.getNbreChambre()));
            dateAjoutTF.getPicker().setDate(currentMaison.getDateAjout());
            adresseTF.setText(currentMaison.getAdresse());
            prixTF.setText(String.valueOf(currentMaison.getPrix()));


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                surfaceLabel, surfaceTF,
                nbreChambreLabel, nbreChambreTF,
                dateAjoutTF,
                adresseLabel, adresseTF,
                prixLabel, prixTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentMaison == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = MaisonService.getInstance().add(
                            new Maison(


                                    (int) Float.parseFloat(surfaceTF.getText()),
                                    (int) Float.parseFloat(nbreChambreTF.getText()),
                                    dateAjoutTF.getPicker().getDate(),
                                    adresseTF.getText(),
                                    (int) Float.parseFloat(prixTF.getText())
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Maison ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de maison. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = MaisonService.getInstance().edit(
                            new Maison(
                                    currentMaison.getId(),


                                    (int) Float.parseFloat(surfaceTF.getText()),
                                    (int) Float.parseFloat(nbreChambreTF.getText()),
                                    dateAjoutTF.getPicker().getDate(),
                                    adresseTF.getText(),
                                    (int) Float.parseFloat(prixTF.getText())

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Maison modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de maison. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (surfaceTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le surface", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(surfaceTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", surfaceTF.getText() + " n'est pas un nombre valide (surface)", new Command("Ok"));
            return false;
        }


        if (nbreChambreTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le nbreChambre", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nbreChambreTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nbreChambreTF.getText() + " n'est pas un nombre valide (nbreChambre)", new Command("Ok"));
            return false;
        }


        if (dateAjoutTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateAjout", new Command("Ok"));
            return false;
        }


        if (adresseTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le adresse", new Command("Ok"));
            return false;
        }


        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le prix", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixTF.getText() + " n'est pas un nombre valide (prix)", new Command("Ok"));
            return false;
        }


        return true;
    }
}