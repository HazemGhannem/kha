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
import entites.Plat;
import entites.Restaurant;
import services.ServiceResto;



/**
 *
 * @author USER
 */
public class AddPlat extends Form{

    public AddPlat(Form previous) {
        setTitle("Add Plat");
        setLayout(BoxLayout.y());
        TextField tfNomPlat = new TextField("","Nom Plat");
       // CheckBox cbSpecialite = new CheckBox("Specialite");
        TextField tfPrixPlat = new TextField("","Prix Plat");
        TextField tfIngredients = new TextField("","Ingredients");
       // TextField tfTelResto = new TextField("","Restaurant Number");
        Button btnAdd = new Button("Ajouter Plat");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {  
                
            Plat plat = new Plat( tfNomPlat.getText(),0,Integer.parseInt(tfPrixPlat.getText()),tfIngredients.getText());
            if(ServiceResto.getInstance().addPlat(plat)){
                Dialog.show("Success","Plat Ajouté", "OK", null);
            }else{
                Dialog.show("Error","Request Error","OK",null);
            }
            
            }
        });
        getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,(evt) -> { previous.showBack(); });
        addAll(tfNomPlat,tfPrixPlat,tfIngredients,btnAdd);
    }
    
    
}
