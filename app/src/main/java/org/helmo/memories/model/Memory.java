package org.helmo.memories.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Memory {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private String title;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private String imagePath;
    @ColumnInfo
    private String date;
    @ColumnInfo
    private boolean favorite;
    @ColumnInfo
    private double lattitude;
    @ColumnInfo
    private double longitude;

    public Memory(String title, String description, String imagePath, String date, boolean favorite, double lattitude, double longitude) {
        this.title = title.trim();
        this.description = description.trim();
        this.imagePath = imagePath;
        this.date = date;
        this.favorite = favorite;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    /**
     *  renvoie id de image
     * @return uid du souvenir
     */
    public int getUid() {
        return uid;
    }

    /**
     * renvoie le titre du souvenir
     * @return le titre
     */
    public String getTitle() {
        return title;
    }

    /**
     * renvoie la description du souvenir
     * @return la description
     */
    public String getDescription() {
        return description;
    }

    /**
     * renvoie le chemain du souvenir
     * @return le chemain de l'image
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * renvoie la date du souvenir
     * @return la date
     */
    public String getDate() {
        return date;
    }

    /**
     * renvoie la lattitude du souvenir
     * @return la lattitude
     */
    public double getLattitude() {
        return lattitude;
    }

    /**
     * renvoie la longitude du souvenir
     * @return la longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *  renvoie statut du souvenir
     * @return true si image est favorite ou false si elle ne l'est pas
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     * affect un nouvel id au souvenir
     * @param uid nouvelle id
     */
    public void setUid(int uid) { this.uid = uid; }

    /**
     * Change le titre d'un souvenir
     * @param title
     */
    public void setTitle(String title) {this.title = title; }

    /**
     * Change la description du souvenir
     * @param description
     */
    public void setDescription(String description) {this.description = description; }

    /**
     * Change la date du souvenir
     * @param date
     */
    public void setDate(String date) { this.date = date; }

    /**
     * Change les cordonnée gps
     * @param newLongitude
     * @param newLattitude
     */
    public void changeLocalisation(double newLongitude, double newLattitude){
        this.longitude = newLongitude;
        this.lattitude = newLattitude;
    }

    /**
     * permet de chnager le staut de "image" si elle est favorit ou pas
     */
    public void changeStatut(){
        favorite = !favorite;
    }
}
