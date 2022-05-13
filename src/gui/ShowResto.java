/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import entites.Restaurant;
import java.util.ArrayList;
import services.ServiceResto;


/**
 *
 * @author USER
 */
public class ShowResto extends Form{

    public ShowResto(Form previous) {
        ArrayList<Restaurant> listResto;
                setTitle("List Restaurants");
        setLayout(BoxLayout.y());
        SpanLabel sp = new SpanLabel();
        getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,(evt) -> { previous.showBack(); });
        listResto = ServiceResto.getInstance().getAllRestaurants();
        Form myRestoForm = new Form("RestoS", new BoxLayout(BoxLayout.Y_AXIS));
        for (int i = 0; i < listResto.size(); i++) {
            int idResto = listResto.get(i).getIdResto();
            String nomResto = listResto.get(i).getNomResto().toString();
            int telResto = listResto.get(i).getTelResto();
            String specialite = listResto.get(i).getSpecialite().toString();
            String adresseResto = listResto.get(i).getAdresseResto().toString();
            addButton(idResto, nomResto, telResto, specialite,adresseResto);

        }
    }
    
        private void addButton(int idResto, String nomResto, int telResto, String specialite , String adresseResto) {
        Container cnt = new Container();
        Label lbNomResto = new Label(" Nom  " + nomResto);
        Label lbSpecialite= new Label(" Specialite " + specialite);
        Label lbAdresseResto= new Label(" Adresse  " + adresseResto);
        Label  lbTelResto= new Label(" Telephone " +telResto);
        Button btnShow = new Button("Menu");
        Button btnMap = new Button("Map");
        cnt.addAll(
                lbNomResto,lbSpecialite, lbAdresseResto,lbTelResto, btnShow, btnMap
        );

        Restaurant p = new Restaurant(idResto,telResto,nomResto,specialite,adresseResto);
        btnShow.addActionListener(e -> {
            new ShowSingleResto(this,p).show();
      //     ToastBar.showMessage("id: " + id + " title: " + titre + " Score: " + Score + " Contenu: " + contenu + " image: " + image, FontImage.MATERIAL_INFO);
        });
                btnMap.addActionListener(e -> {
            new MapForm();
      //     ToastBar.showMessage("id: " + id + " title: " + titre + " Score: " + Score + " Contenu: " + contenu + " image: " + image, FontImage.MATERIAL_INFO);
        });
             
        add(cnt);
    }
    
    
    
}
