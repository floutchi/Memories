package org.helmo.memories.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Date date;
    @ColumnInfo
    private boolean favorite;

    /**
     * Constructeur d'un souvenir
     * @param title
     * @param date date de la photo
     * @param favorite si la photo est favorite
     * @param description description da la photo
     */
    public Memory(String title, Date date, boolean favorite, String description, String path){
        this.title = title;
        this.date = date;
        this.favorite = favorite;
        this.description = description;
        this.imagePath = path;
    }

}
