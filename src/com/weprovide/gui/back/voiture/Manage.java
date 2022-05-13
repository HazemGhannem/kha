package com.weprovide.gui.back.voiture;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import entites.Voiture;
import services.VoitureService;

public class Manage extends Form {


    Voiture currentVoiture;

    Label marqueLabel, couleurLabel, dateAjoutLabel, prixLabel;
    TextField
            marqueTF,
            couleurTF,
            prixTF;
    PickerComponent dateAjoutTF;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentVoiture == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentVoiture = ShowAll.currentVoiture;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        marqueLabel = new Label("Marque : ");
        marqueLabel.setUIID("labelDefault");
        marqueTF = new TextField();
        marqueTF.setHint("Tapez le marque");

        couleurLabel = new Label("Couleur : ");
        couleurLabel.setUIID("labelDefault");
        couleurTF = new TextField();
        couleurTF.setHint("Tapez le couleur");
        dateAjoutTF = PickerComponent.createDate(null).label("DateAjout");

        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");


        if (currentVoiture == null) {


            manageButton = new Button("Ajouter");
        } else {


            marqueTF.setText(currentVoiture.getMarque());
            couleurTF.setText(currentVoiture.getCouleur());
            dateAjoutTF.getPicker().setDate(currentVoiture.getDateAjout());
            prixTF.setText(String.valueOf(currentVoiture.getPrix()));


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                marqueLabel, marqueTF,
                couleurLabel, couleurTF,
                dateAjoutTF,
                prixLabel, prixTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentVoiture == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = VoitureService.getInstance().add(
                            new Voiture(


                                    marqueTF.getText(),
                                    couleurTF.getText(),
                                    dateAjoutTF.getPicker().getDate(),
                                    (int) Float.parseFloat(prixTF.getText())
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Voiture ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de voiture. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = VoitureService.getInstance().edit(
                            new Voiture(
                                    currentVoiture.getId(),


                                    marqueTF.getText(),
                                    couleurTF.getText(),
                                    dateAjoutTF.getPicker().getDate(),
                                    (int) Float.parseFloat(prixTF.getText())

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Voiture modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de voiture. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (marqueTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le marque", new Command("Ok"));
            return false;
        }


        if (couleurTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le couleur", new Command("Ok"));
            return false;
        }


        if (dateAjoutTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateAjout", new Command("Ok"));
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