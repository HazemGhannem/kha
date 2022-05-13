/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import entites.Comment;
import entites.Post;
import java.io.IOException;
import java.util.ArrayList;
import services.ServicePost;
import utils.Statics;

/**
 *
 * @author hp
 */
public class ShowSinglePost extends Form {

    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    ArrayList<Comment> commentsList;

    public ShowSinglePost(Form previous, Post p) {
        super("Download Image", BoxLayout.y());
        System.out.println(p.toString());

        setTitle(p.getTitre());
        setLayout(BoxLayout.y());
        SpanLabel sp = new SpanLabel();
        Button btDownload = new Button("Download");
        Label contenuLb = new Label();
        Label scoreLb = new Label();

        contenuLb.setText("Contenu: " + p.getContenu());
        scoreLb.setText("Score: " + p.getNbre_jaime());

        try {
            imgv = new ImageViewer(Image.createImage("/load.png"));
        } catch (IOException ex) {
            Dialog.show("Error", ex.getMessage(), "Ok", null);
        }

        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
            Dialog.show("Error", ex.getMessage(), "Ok", null);
        }
        String url = Statics.URL_REP_IMAGES + p.getImage() + ".jpg";
        imgs = URLImage.createToStorage(enc, p.getImage(), url, URLImage.RESIZE_SCALE);
        imgv.setImage(imgs);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            previous.showBack();
        });

        Label commentLb = new Label("Comments");
        addAll(imgv, contenuLb, scoreLb, commentLb);

        commentsList = ServicePost.getInstance().getComments(p.getId());
        if (commentsList.size() != 0) {
            for (int i = 0; i < commentsList.size(); i++) {
                addAll(new Label("Contenu: " + commentsList.get(i).getContenu()));

            }
        }
        
        
        TextField tfComment = new TextField("", "ajouter un commentaire");
          Button btnAdd = new Button("ajouter");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfComment.getText().equals("") ) {
                    Dialog.show("Error", "inserer un commentaire avant l ajout", "OK", null);
                } else {
                    int nbre_jaime = 0;
                    Comment comment = new Comment(0,p.getId(), tfComment.getText(),2);
                                       

                    if (ServicePost.getInstance().addComment(comment)) {
                        Dialog.show("Success", "succes", "OK", null);
                    } else {
                        Dialog.show("Error", "erreur", "OK", null);

                    }
                }
            }

        });
        addAll(tfComment,btnAdd);
        

    }
}
