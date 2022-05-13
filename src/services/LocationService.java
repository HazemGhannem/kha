package services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import entites.Location;
import entites.Maison;
import entites.Voiture;
import utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationService {

    public static LocationService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Location> listLocations;

    private LocationService() {
        cr = new ConnectionRequest();
    }

    public static LocationService getInstance() {
        if (instance == null) {
            instance = new LocationService();
        }
        return instance;
    }

    public ArrayList<Location> getAll() {
        listLocations = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "mobile/location");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listLocations = getList();
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

        return listLocations;
    }

    private ArrayList<Location> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Location location = new Location(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        makeMaison((Map<String, Object>) obj.get("maison")),
                        makeVoiture((Map<String, Object>) obj.get("voiture")),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateDebut")),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateFin"))
                );

                listLocations.add(location);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listLocations;
    }

    public Maison makeMaison(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Maison maison = new Maison();
        maison.setId((int) Float.parseFloat(obj.get("id").toString()));
        maison.setAdresse((String) obj.get("adresse"));
        return maison;
    }

    public Voiture makeVoiture(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Voiture voiture = new Voiture();
        voiture.setId((int) Float.parseFloat(obj.get("id").toString()));
        voiture.setMarque((String) obj.get("marque"));
        return voiture;
    }

    public int add(Location location) {
        return manage(location, false);
    }

    public int edit(Location location) {
        return manage(location, true);
    }

    public int manage(Location location, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "mobile/location/edit");
            cr.addArgument("id", String.valueOf(location.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "mobile/location/add");
        }

        cr.addArgument("voiture", String.valueOf(location.getVoiture().getId()));
        cr.addArgument("maison", String.valueOf(location.getMaison().getId()));
        cr.addArgument("dateDebut", new SimpleDateFormat("dd-MM-yyyy").format(location.getDateDebut()));
        cr.addArgument("dateFin", new SimpleDateFormat("dd-MM-yyyy").format(location.getDateFin()));

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

    public int delete(int locationId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "mobile/location/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(locationId));

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
