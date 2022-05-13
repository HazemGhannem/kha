package entites;

import java.util.Date;

public class Voiture {

    private int id;
    private String marque;
    private String couleur;
    private Date dateAjout;
    private int prix;

    public Voiture() {
    }

    public Voiture(int id, String marque, String couleur, Date dateAjout, int prix) {
        this.id = id;
        this.marque = marque;
        this.couleur = couleur;
        this.dateAjout = dateAjout;
        this.prix = prix;
    }

    public Voiture(String marque, String couleur, Date dateAjout, int prix) {
        this.marque = marque;
        this.couleur = couleur;
        this.dateAjout = dateAjout;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", couleur='" + couleur + '\'' +
                ", dateAjout=" + dateAjout +
                ", prix=" + prix +
                '}';
    }
}