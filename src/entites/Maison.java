package entites;

import java.util.Date;

public class Maison {

    private int id;
    private int surface;
    private int nbreChambre;
    private Date dateAjout;
    private String adresse;
    private int prix;

    public Maison() {
    }

    public Maison(int id, int surface, int nbreChambre, Date dateAjout, String adresse, int prix) {
        this.id = id;
        this.surface = surface;
        this.nbreChambre = nbreChambre;
        this.dateAjout = dateAjout;
        this.adresse = adresse;
        this.prix = prix;
    }

    public Maison(int surface, int nbreChambre, Date dateAjout, String adresse, int prix) {
        this.surface = surface;
        this.nbreChambre = nbreChambre;
        this.dateAjout = dateAjout;
        this.adresse = adresse;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNbreChambre() {
        return nbreChambre;
    }

    public void setNbreChambre(int nbreChambre) {
        this.nbreChambre = nbreChambre;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }


    @Override
    public String toString() {
        return "Maison : " +
                "id=" + id
                + ", Surface=" + surface
                + ", NbreChambre=" + nbreChambre
                + ", DateAjout=" + dateAjout
                + ", Adresse=" + adresse
                + ", Prix=" + prix
                ;
    }

}