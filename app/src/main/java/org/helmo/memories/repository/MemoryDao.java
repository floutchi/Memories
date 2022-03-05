package org.helmo.memories.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.helmo.memories.model.Memory;

import java.util.List;

@Dao
public interface MemoryDao {

    @Query("SELECT * FROM Memory ORDER BY favorite DESC")
    LiveData<List<Memory>> getAllMemories();

    @Query("SELECT * FROM Memory WHERE uid = (:id)")
    LiveData<Memory> getMemory(int id);

    @Insert
    void insertMemory(Memory... memories);

    @Query("UPDATE Memory SET favorite = 1 WHERE uid = (:id) ")
    void setFavorite(int id);

    @Query("UPDATE Memory SET favorite = 0 WHERE uid = (:id)")
    void setUnFavorite(int id);


    @Query("DELETE FROM Memory WHERE uid = (:id)")
    void deleteByID(int id);
}
