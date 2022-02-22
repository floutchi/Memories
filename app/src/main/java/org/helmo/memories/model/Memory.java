package org.helmo.memories.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Memory {

    private Date date;
    private boolean favorite;
    private String description, path;

    /**
     * Constructeur d'un souvenir
     * @param date date de la photo
     * @param favorite si la photo est favorite
     * @param description description da la photo
     */
    public Memory(Date date, boolean favorite , String description, String path ){
        this.date = date;
        this.favorite = favorite;
        this.description = description;
        this.path = path;
    }

    /**
     * donne la date en format string
     * @return la date
     */
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        return dateFormat.format(this.date);
    }

    /**
     * Permet ajouter une date
     * @param date nouvelle date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * renvoie la description
     * @return string de la description
     */
    public String getDescription() {
        return description;
    }

    /**
     * change la description da la photo
     * @param description nouvelle description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * permet de changer le si la vidéo en favorit
     * @param favorite
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * retourn le statut des favorit
     * @return bool de statut du favorit
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     * le chemien de image dans le téléphone
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * permet de changer le path de l'image
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
