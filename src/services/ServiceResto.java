/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entites.Plat;
import entites.Restaurant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author USER
 */
public class ServiceResto {

    public ConnectionRequest req;
    public ArrayList<Restaurant> restaurants;
    public ArrayList<Plat> platsList;
    private static ServiceResto instance;

    public boolean resultOk;

    private ServiceResto() {
        req = new ConnectionRequest();
    }

    public static ServiceResto getInstance() {
        if (instance == null) {
            instance = new ServiceResto();
        }
        return instance;
    }

    public boolean addResto(Restaurant restaurant) {
        String url = Statics.BASE_URL + "api/restaurant/add";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("nomResto", restaurant.getNomResto());
        req.addArgument("adresseResto", restaurant.getAdresseResto());
        req.addArgument("specialite", restaurant.getSpecialite());
        req.addArgument("telResto", restaurant.getTelResto() + " ");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 201;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean addPlat(Plat plat) {

        String url = Statics.BASE_URL + "api/plat/add";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("nomPlat", plat.getNomPlat());
        req.addArgument("prixPlat", plat.getPrixPlat() + " ");
        req.addArgument("ingredients", plat.getIngredients() + " ");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 201;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public ArrayList<Restaurant> parseResto(String jsonText) {
        try {
            restaurants = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> restaurantsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) restaurantsListJson.get("root");
            for (Map<String, Object> obj : list) {

                float telResto = -1;
                if (obj.get("telephone") != null) {
                    telResto = Float.parseFloat(obj.get("telephone").toString());
                }

                /* float idResto = -1;
                if (obj.get("idResto") != null) {
                    idResto = Float.parseFloat(obj.get("idResto").toString());
                }*/
                //
                String nomResto = "null";
                if (obj.get("nom") != null) {
                    nomResto = obj.get("nom").toString();
                }

                //String specialite = "null";
                //if (obj.get("specialite") != null) {
                String specialite = obj.get("specialite").toString();
                //}

                String adresseResto = "null";
                if (obj.get("adresse") != null) {
                    adresseResto = obj.get("adresse").toString();
                }
                Restaurant r = new Restaurant((int) telResto, nomResto, specialite, adresseResto);
                restaurants.add(r);

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return restaurants;
    }

    public ArrayList<Plat> parsePlat(String jsonText) {
        try {
            platsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> postsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) postsListJson.get("root");
            for (Map<String, Object> obj : list) {

                float idPlat = -1;
                if (obj.get("idPlat") != null) {
                    idPlat = Float.parseFloat(obj.get("idPlat").toString());
                }
                String nomPlat = "null";
                if (obj.get("nomPlat") != null) {
                    nomPlat = obj.get("nomPlat").toString();
                }

                float idRestoP = -1;
                if (obj.get("") != null) {
                    idRestoP = Float.parseFloat(obj.get("postId").toString());
                }

                float prixPlat = Float.parseFloat(obj.get("prixPlat").toString());
                String ingredients = "null";
                if (obj.get("ingredients") != null) {
                    ingredients = obj.get("ingredients").toString();
                }
                Plat plat = new Plat((int) idPlat, nomPlat, (int) idRestoP, (int) prixPlat, ingredients);
                platsList.add(plat);

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return platsList;
    }

    public ArrayList<Restaurant> getAllRestaurants() {

        String url = Statics.BASE_URL + "api/restaurant/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                restaurants = parseResto(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return restaurants;
    }

    public ArrayList<Plat> getAllPlats() {

        String url = Statics.BASE_URL + "api/plat/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                platsList = parsePlat(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return platsList;
    }

    public ArrayList<Plat> getPlats(int idPlat) {
        String url = Statics.BASE_URL + "api/getPlats";
        ///
        ConnectionRequest r = new ConnectionRequest();
        r.setPost(false);
        r.setUrl(url);

        r.addArgument("idPlat", "" + idPlat);

        r.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                platsList = parsePlat(new String(r.getResponseData()));
                r.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return platsList;

        ///
    }
}
