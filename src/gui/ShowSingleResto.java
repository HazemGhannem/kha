/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import entites.Plat;
import entites.Restaurant;
import java.io.IOException;
import java.util.ArrayList;
import utils.Statics;
import services.ServiceResto;

/**
 *
 * @author USER
 */
public class ShowSingleResto extends Form {
      EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
        ArrayList<Plat> platsList ;


    public ShowSingleResto(Form previous, Restaurant p) {
        Container cnt = new Container();
        System.out.println(p.toString());
        Button btnAddPlat = new Button("Ajouter Plat");
        //setNomResto(p.getNomResto());
        SpanLabel sp = new SpanLabel();
        //Button btDownload = new Button("Download");
        Label telRestoLb = new Label();
        Label specialiteLb = new Label();
        Label adresseRestoLb = new Label();
        
        specialiteLb.setText("Specialite: " + p.getSpecialite());
        adresseRestoLb.setText("Adresse: " + p.getAdresseResto());
        telRestoLb.setText("Telephone: " + p.getTelResto());

     
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            previous.showBack();
        });
        
        Label platLb = new Label("Plats");
                cnt.addAll(telRestoLb,specialiteLb,adresseRestoLb,platLb,btnAddPlat);
                  

                  platsList = ServiceResto.getInstance().getAllPlats();
                  if(!platsList.isEmpty()){
                      System.out.println("o2k");
                      for(int i=0;i<platsList.size();i++){
                       add(new Label("Nom du plat: "+platsList.get(i).getNomPlat()));
                       add(new Label("Ingredients: "+platsList.get(i).getIngredients()));
                          add(new Label("Prix: "+platsList.get(i).getPrixPlat()));
                          
                          
                      }
                  }
                  add(cnt);
     btnAddPlat.addActionListener((evt) -> {new AddPlat(this).show();
        });
     
    }
}
