package entites;

import java.util.Date;

public class Location {

    private int id;
    private Maison maison;
    private Voiture voiture;
    private Date dateDebut;
    private Date dateFin;

    public Location() {
    }

    public Location(int id, Maison maison, Voiture voiture, Date dateDebut, Date dateFin) {
        this.id = id;
        this.maison = maison;
        this.voiture = voiture;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Location(Maison maison, Voiture voiture, Date dateDebut, Date dateFin) {
        this.maison = maison;
        this.voiture = voiture;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Maison getMaison() {
        return maison;
    }

    public void setMaison(Maison maison) {
        this.maison = maison;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }


    @Override
    public String toString() {
        return "Location : " +
                "id=" + id
                + ", Maison=" + maison
                + ", Voiture=" + voiture
                + ", DateDebut=" + dateDebut
                + ", DateFin=" + dateFin
                ;
    }

}