package com.weprovide.gui.back.maison;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entites.Maison;
import services.MaisonService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Maison currentMaison = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label surfaceLabel, nbreChambreLabel, dateAjoutLabel, adresseLabel, prixLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Maisons", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Maison> listMaisons = MaisonService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher maison par adresse");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Maison maison : listMaisons) {
                if (maison.getAdresse().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeMaisonModel(maison);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);

        if (listMaisons.size() > 0) {
            for (Maison maison : listMaisons) {
                Component model = makeMaisonModel(maison);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentMaison = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Maison maison) {
        Container maisonModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        maisonModel.setUIID("containerRounded");


        surfaceLabel = new Label("Surface : " + maison.getSurface());
        surfaceLabel.setUIID("labelDefault");

        nbreChambreLabel = new Label("NbreChambre : " + maison.getNbreChambre());
        nbreChambreLabel.setUIID("labelDefault");

        dateAjoutLabel = new Label("DateAjout : " + new SimpleDateFormat("dd-MM-yyyy").format(maison.getDateAjout()));
        dateAjoutLabel.setUIID("labelDefault");

        adresseLabel = new Label("Adresse : " + maison.getAdresse());
        adresseLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + maison.getPrix());
        prixLabel.setUIID("labelDefault");


        maisonModel.addAll(

                surfaceLabel, nbreChambreLabel, dateAjoutLabel, adresseLabel, prixLabel
        );

        return maisonModel;
    }

    private Component makeMaisonModel(Maison maison) {

        Container maisonModel = makeModelWithoutButtons(maison);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonMain");
        editBtn.addActionListener(action -> {
            currentMaison = maison;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce maison ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = MaisonService.getInstance().delete(maison.getId());

                if (responseCode == 200) {
                    currentMaison = null;
                    dlg.dispose();
                    maisonModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du maison. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        maisonModel.add(btnsContainer);

        return maisonModel;
    }

}