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
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.date = date;
        this.favorite = favorite;
        this.lattitude = lattitude;
        this.longitude = longitude;
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

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
