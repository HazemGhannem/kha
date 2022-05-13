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
public class Comment {

    private int id;
    private int nbre_jaime;
    private int post_id;
    private String contenu;
    private int user_id;

    public Comment(int nbre_jaime, int post_id, String contenu) {
        this.nbre_jaime = nbre_jaime;
        this.post_id = post_id;
        this.contenu = contenu;
        
    }
    public Comment(int nbre_jaime, int post_id, String contenu, int user_id) {
        this.nbre_jaime = nbre_jaime;
        this.post_id = post_id;
        this.contenu = contenu;
        this.user_id = user_id;
    }

    public Comment(int id, int nbre_jaime, int post_id, String contenu) {
        this.id = id;
        this.nbre_jaime = nbre_jaime;
        this.post_id = post_id;
        this.contenu = contenu;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbre_jaime() {
        return nbre_jaime;
    }

    public void setNbre_jaime(int nbre_jaime) {
        this.nbre_jaime = nbre_jaime;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    

    @Override
    public String toString() {
        return "comment{" + "nbre_jaime=" + nbre_jaime + ", post_id=" + post_id + ", contenu=" + contenu +  ", user_id=" + user_id +'}';
    }

}
