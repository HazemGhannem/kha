package com.weprovide.gui.front;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AccueilFront extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;

    public AccueilFront() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        ImageViewer userImage = new ImageViewer(theme.getImage("default.jpg").fill(200, 200));
        userImage.setUIID("candidatImage");
        label = new Label("Front");
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setUIID("buttonLogout");
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            //Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.WEST, userImage);
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeLocationsButton(),
                makeVoituresButton(),
                makeMaisonsButton()
        );

        this.add(menuContainer);
    }

    private Button makeLocationsButton() {
        Button button = new Button("Locations");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);

        button.addActionListener(action -> new com.weprovide.gui.front.location.ShowAll(this).show());
        return button;
    }

    private Button makeVoituresButton() {
        Button button = new Button("Voitures");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);

        button.addActionListener(action -> new com.weprovide.gui.front.voiture.ShowAll(this).show());
        return button;
    }

    private Button makeMaisonsButton() {
        Button button = new Button("Maisons");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);

        button.addActionListener(action -> new com.weprovide.gui.front.maison.ShowAll(this).show());
        return button;
    }

}
