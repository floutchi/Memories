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
    private String date;
    @ColumnInfo
    private boolean favorite;

    public Memory(String title, String description, String imagePath, String date, boolean favorite) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.date = date;
        this.favorite = favorite;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDate() {
        return date;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
