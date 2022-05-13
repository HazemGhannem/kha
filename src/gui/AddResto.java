/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import entites.Restaurant;
import services.ServiceResto;

/**
 *
 * @author USER
 */
public class AddResto extends Form {

    public AddResto(Form previous) {
        setTitle("Ajouter Restaurant");
        setLayout(BoxLayout.y());
        TextField tfNomResto = new TextField("", "Nom Restaurant ");
        // CheckBox cbSpecialite = new CheckBox("Specialite");
        TextField tfAdresseResto = new TextField("", "Adresse Restaurant");
        TextField tfSpecialite = new TextField("", "Restaurant Specialite");
        TextField tfTelResto = new TextField("", "Numero Telephone");
        Button btnAdd = new Button("Ajouter");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Restaurant restaurant = new Restaurant(Integer.parseInt(tfTelResto.getText()), tfNomResto.getText(), tfSpecialite.getText(), tfAdresseResto.getText());
                System.out.println(restaurant);
                if (ServiceResto.getInstance().addResto(restaurant)) {
                    Dialog.show("Success", "Restaurant Ajouté", "OK", null);
                } else {
                    Dialog.show("Error", "Request Error", "OK", null);
                }

            }
        });
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            previous.showBack();
        });
        addAll(tfNomResto, tfSpecialite, tfAdresseResto, tfTelResto, btnAdd);
    }

}
