package services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entites.Maison;
import utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaisonService {

    public static MaisonService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Maison> listMaisons;

    private MaisonService() {
        cr = new ConnectionRequest();
    }

    public static MaisonService getInstance() {
        if (instance == null) {
            instance = new MaisonService();
        }
        return instance;
    }

    public ArrayList<Maison> getAll() {
        listMaisons = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "mobile/maison");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listMaisons = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listMaisons;
    }

    private ArrayList<Maison> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Maison maison = new Maison(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (int) Float.parseFloat(obj.get("surface").toString()),
                        (int) Float.parseFloat(obj.get("nbreChambre").toString()),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateAjout")),
                        (String) obj.get("adresse"),
                        (int) Float.parseFloat(obj.get("prix").toString())
                );

                listMaisons.add(maison);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listMaisons;
    }

    public int add(Maison maison) {
        return manage(maison, false);
    }

    public int edit(Maison maison) {
        return manage(maison, true);
    }

    public int manage(Maison maison, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "mobile/maison/edit");
            cr.addArgument("id", String.valueOf(maison.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "mobile/maison/add");
        }
        cr.addArgument("surface", String.valueOf(maison.getSurface()));
        cr.addArgument("nbreChambre", String.valueOf(maison.getNbreChambre()));
        cr.addArgument("dateAjout", new SimpleDateFormat("dd-MM-yyyy").format(maison.getDateAjout()));
        cr.addArgument("adresse", maison.getAdresse());
        cr.addArgument("prix", String.valueOf(maison.getPrix()));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int maisonId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "mobile/maison/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(maisonId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
