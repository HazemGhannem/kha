/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entites.Comment;
import entites.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author hp
 */
public class ServicePost {

    public ArrayList<Post> posts;
    public ArrayList<Comment> commentsList;

    public ConnectionRequest req;
    boolean resultOK;
    private static ServicePost instance;

    private ServicePost() {
        req = new ConnectionRequest();
    }

    public static ServicePost getInstance() {
        if (instance == null) {
            instance = new ServicePost();
        }
        return instance;
    }

    public boolean addPost(Post post) {

        String url = Statics.BASE_URL + "api/post/add";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("titre", post.getTitre());
        //req.addArgument("nbre_jaime",post.getNbre_jaime()+"");
        req.addArgument("contenu", post.getContenu());
        req.addArgument("image", post.getImage());

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 201;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean addComment(Comment c) {

        String url = Statics.BASE_URL + "api/comment/add";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("idpost", "" + c.getPost_id());
        req.addArgument("iduser", "2");
        req.addArgument("contenu", "" + c.getContenu());
        req.addArgument("nbreJaime", "0");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 201;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Post> parsePost(String jsonText) {
        try {
            posts = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> postsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) postsListJson.get("root");
            for (Map<String, Object> obj : list) {

                float id = -1;
                if (obj.get("id") != null) {
                    id = Float.parseFloat(obj.get("id").toString());
                }
                String titre = "null";
                if (obj.get("titre") != null) {
                    titre = obj.get("titre").toString();
                }
                String image = "null";
                if (obj.get("image") != null) {
                    image = obj.get("image").toString();
                }

                float nbre_jaime = Float.parseFloat(obj.get("nbreJaime").toString());
                String contenu = "null";
                if (obj.get("contenu") != null) {
                    contenu = obj.get("contenu").toString();
                }
                Post p = new Post((int) id, titre, (int) nbre_jaime, contenu, image);
                posts.add(p);

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return posts;
    }

    public ArrayList<Comment> parseComment(String jsonText) {
        try {
            commentsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> postsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) postsListJson.get("root");
            for (Map<String, Object> obj : list) {

                float id = -1;
                if (obj.get("id") != null) {
                    id = Float.parseFloat(obj.get("id").toString());
                }
                String titre = "null";
                if (obj.get("contenu") != null) {
                    titre = obj.get("contenu").toString();
                }

                float postId = -1;
                if (obj.get("postId") != null) {
                    postId = Float.parseFloat(obj.get("postId").toString());
                }

                float nbre_jaime = Float.parseFloat(obj.get("nbreJaime").toString());
                String contenu = "null";
                if (obj.get("contenu") != null) {
                    contenu = obj.get("contenu").toString();
                }

                Comment comment = new Comment((int) id, (int) nbre_jaime, (int) postId, contenu);
                commentsList.add(comment);

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return commentsList;
    }

    public ArrayList<Post> getAllPosts() {

        String url = Statics.BASE_URL + "api/posts/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                posts = parsePost(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return posts;
    }

    public ArrayList<Comment> getComments(int idPost) {
        String url = Statics.BASE_URL + "api/getComments";
        ///
        ConnectionRequest r = new ConnectionRequest();
        r.setPost(false);
        r.setUrl(url);

        r.addArgument("idpost", "" + idPost);

        r.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commentsList = parseComment(new String(r.getResponseData()));
                r.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return commentsList;

        ///
    }

}
