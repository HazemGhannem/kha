/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import entites.Post;
import java.util.ArrayList;
import services.ServicePost;

/**
 *
 * @author hp
 */
public class ShowForm extends Form {

    public ShowForm(Form previous) {
        ArrayList<Post> listPost;
        setTitle("Posts");
        setLayout(BoxLayout.y());
        /*Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.addSearchCommand((evt) -> {
        });
        SpanLabel sp = new SpanLabel();*/

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            previous.showBack();
        });
                Button btnAdd = new Button ("ajouter un Post");
   btnAdd.addActionListener((evt) -> {
            new AddForm(this).show();
            

        });
        add(btnAdd);
        
        listPost = ServicePost.getInstance().getAllPosts();
        Form myPostForm = new Form("POSTS", new BoxLayout(BoxLayout.Y_AXIS));
        for (int i = 0; i < listPost.size(); i++) {
            int id = listPost.get(i).getId();
            String titre = listPost.get(i).getTitre().toString();
            int Score = listPost.get(i).getNbre_jaime();
            String Contenu = listPost.get(i).getContenu().toString();
            String Image = listPost.get(i).getImage().toString();
            addButton(id, titre, Score, Contenu,Image);

        }
    }

    private void addButton(int id, String titre, int Score, String contenu , String image) {
        Container cnt = new Container();
        Label lbTitre = new Label(" titre  " + titre);
        Label lbScore = new Label(" Score " + Score);
        Label lbContenu = new Label(" contenu  " + contenu);
        Label lbImage = new Label(" image " + image);
        Button btnShow = new Button("afficher");
        cnt.addAll(
                lbTitre,lbScore, lbContenu, btnShow
        );

        Post p = new Post(id,titre, Score, contenu, image);
        btnShow.addActionListener(e -> {
            new ShowSinglePost(this,p).show();
      //     ToastBar.showMessage("id: " + id + " title: " + titre + " Score: " + Score + " Contenu: " + contenu + " image: " + image, FontImage.MATERIAL_INFO);
        });
             
        add(cnt);
    }

}
