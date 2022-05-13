/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/**
 *
 * @author hp
 */
public class Post {

    private int id;
    private String titre;
    private int nbre_jaime;
    private String contenu;
    private String image;

    public Post(String titre, int nbre_jaime, String contenu, String image) {
        this.titre = titre;
        this.nbre_jaime = nbre_jaime;
        this.contenu = contenu;
        this.image = image;
    }

    public Post(int id, String titre, int nbre_jaime, String contenu, String image) {
        this.id = id;
        this.titre = titre;
        this.nbre_jaime = nbre_jaime;
        this.contenu = contenu;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public String getImage() {
        return image;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNbre_jaime() {
        return nbre_jaime;
    }

    public void setNbre_jaime(int nbre_jaime) {
        this.nbre_jaime = nbre_jaime;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "post{" + "titre=" + titre + ", nbre_jaime=" + nbre_jaime + ", contenu=" + contenu + ", image=" + image + '}';
    }

}
