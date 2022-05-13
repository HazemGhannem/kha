/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entites;

/**
 *
 * @author hp
 */
public class Plat{

    private int idPlat;
    private String nomPlat;
    private int idRestoP;
    private int prixPlat;
    private String ingredients;

    public Plat(int idPlat, String nomPlat, int idRestoP, int prixPlat, String ingredients) {
        this.idPlat = idPlat;
        this.nomPlat = nomPlat;
        this.idRestoP = idRestoP;
        this.prixPlat = prixPlat;
        this.ingredients = ingredients;
    }

    public Plat(String nomPlat, int idRestoP, int prixPlat, String ingredients) {
        this.nomPlat = nomPlat;
        this.idRestoP = idRestoP;
        this.prixPlat = prixPlat;
        this.ingredients = ingredients;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public int getIdRestoP() {
        return idRestoP;
    }

    public void setIdRestoP(int idRestoP) {
        this.idRestoP = idRestoP;
    }

    public int getPrixPlat() {
        return prixPlat;
    }

    public void setPrixPlat(int prixPlat) {
        this.prixPlat = prixPlat;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Plat{" + "nomPlat=" + nomPlat + ", idRestoP=" + idRestoP + ", prixPlat=" + prixPlat + ", ingredients=" + ingredients + '}';
    }

   

}