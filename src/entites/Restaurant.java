package entites;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
public class Restaurant {
    private int idResto,telResto;
    private String nomResto,specialite,adresseResto;

    public Restaurant() {
    }

    public Restaurant(int idResto, int telResto, String nomResto, String specialite, String adresseResto) {
        this.idResto = idResto;
        this.telResto = telResto;
        this.nomResto = nomResto;
        this.specialite = specialite;
        this.adresseResto = adresseResto;
    }

    public Restaurant(int telResto, String nomResto, String specialite, String adresseResto) {
        this.telResto = telResto;
        this.nomResto = nomResto;
        this.specialite = specialite;
        this.adresseResto = adresseResto;
    }

    
    @Override
    public String toString() {
        return "Restaurant{" + "telResto=" + telResto + ", nomResto=" + nomResto + ", specialite=" + specialite + ", adresseResto=" + adresseResto + '}';
    }

    public Restaurant(String nomResto, String specialite, String adresseResto) {
        this.nomResto = nomResto;
        this.specialite = specialite;
        this.adresseResto = adresseResto;
    }

    

    public int getIdResto() {
        return idResto;
    }

    public void setIdResto(int idResto) {
        this.idResto = idResto;
    }

    public int getTelResto() {
        return telResto;
    }

    public void setTelResto(int telResto) {
        this.telResto = telResto;
    }

    public String getNomResto() {
        return nomResto;
    }

    public void setNomResto(String nomResto) {
        this.nomResto = nomResto;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getAdresseResto() {
        return adresseResto;
    }

    public void setAdresseResto(String adresseResto) {
        this.adresseResto = adresseResto;
    }
    
    
    
}
