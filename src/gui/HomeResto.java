/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author USER
 */
public class HomeResto extends Form {

    public HomeResto() {

        setTitle("Page d'accueil");
        setLayout(BoxLayout.y());
        Button btnAdd = new Button("Ajouter Restaurant");
        Button btnShow = new Button("Liste Restaurants");
        Button btnS = new Button("afficher tous les posts");
        btnS.addActionListener((evt) -> {
            new ShowForm(this).show();
        });

        btnAdd.addActionListener((evt) -> {
            new AddResto(this).show();
        });
        btnShow.addActionListener((evt) -> {
            new ShowResto(this).show();
        });
        Button frontendBtn = new Button("Front");
        frontendBtn.addActionListener(l -> new com.weprovide.gui.front.AccueilFront().show());
        this.add(frontendBtn);

        Button backendBtn = new Button("Back");
        backendBtn.addActionListener(l -> new com.weprovide.gui.back.AccueilBack().show());

        this.add(backendBtn);
        addAll(btnAdd, btnShow, btnS);
    }

}
