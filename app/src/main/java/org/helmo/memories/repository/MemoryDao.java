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

    @Query("SELECT * FROM Memory")
    LiveData<List<Memory>> getAllMemories();

    @Query("SELECT * FROM Memory WHERE uid = (:id)")
    LiveData<Memory> getMemory(int id);

    @Insert
    void insertMemory(Memory... memories);

    @Update
    void update(Memory memory);

    @Delete
    void delete(Memory memory);

    @Query("DELETE FROM Memory WHERE uid = (:uuid)")
    void deleteByUUID(int uuid);
}
