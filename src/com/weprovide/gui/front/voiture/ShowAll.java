package com.weprovide.gui.front.voiture;

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
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label marqueLabel, couleurLabel, dateAjoutLabel, prixLabel;
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


        ArrayList<Voiture> listVoitures = VoitureService.getInstance().getAll();
        componentModels = new ArrayList<>();

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


        voitureModel.add(btnsContainer);

        return voitureModel;
    }

}