/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import entites.Post;
import java.io.IOException;
import services.ServicePost;
import utils.Statics;

/**
 *
 * @author hp
 */
public class AddForm extends Form {

    public AddForm(Form previous) {
        super("Upload Image", BoxLayout.y());
        setTitle("Add New Post");
        setLayout(BoxLayout.y());
        TextField tfTitre = new TextField("", "titre");
        TextField tfContenu = new TextField("", "contenu");
        TextField tfImage = new TextField("", "nom d image");

        Button btnUpload = new Button("Upload");
        btnUpload.addActionListener((evt) -> {
            if (!"".equals(tfImage.getText())) {
                MultipartRequest cr = new MultipartRequest();
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);

                cr.setUrl(Statics.URL_UPLOAD);
                cr.setPost(true);
                String mime = "image/jpeg";
                try {
                    cr.addData("file", filePath, mime);
                } catch (IOException ex) {
                    Dialog.show("Error", ex.getMessage(), "OK", null);
                }
                cr.setFilename("file", tfImage.getText() + ".jpg");//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                Dialog.show("Success", "succes", "OK", null);
            } else {
                Dialog.show("Error", "nom d image n est pas valide ", "Ok", null);
            }
        });
        Button btnAdd = new Button("ajouter");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfContenu.getText().equals("") || tfTitre.getText().equals("")) {
                    Dialog.show("Error", "ajouter tous les donnees", "OK", null);
                } else {
                    int nbre_jaime = 0;
                    Post post = new Post(tfTitre.getText(), nbre_jaime, tfContenu.getText(), tfImage.getText());
                    System.out.println(post.toString());

                    if (ServicePost.getInstance().addPost(post)) {
                        Dialog.show("Success", "succes", "OK", null);
                        LocalNotification n = new LocalNotification();
                        n.setId("demo-notification");
                        n.setAlertBody("It's time to take a break and look at me");
                        n.setAlertTitle("Break Time!");
                        //n.setAlertSound("/notification_sound_beep-01a.mp3");
                        // alert sound file name must begin with notification_sound

                        Display.getInstance().scheduleLocalNotification(
                                n,
                                System.currentTimeMillis() , // fire date/time
                                LocalNotification.REPEAT_MINUTE // Whether to repeat and what frequency
                        );
                    } else {
                        Dialog.show("Error", "Request Error", "OK", null);

                    }
                }
            }

        });
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            previous.showBack();
        });

        addAll(tfTitre, tfContenu, tfImage, btnUpload, btnAdd);
    }
}
