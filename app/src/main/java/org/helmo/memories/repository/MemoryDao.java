package org.helmo.memories.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.helmo.memories.model.Memory;

import java.util.List;

@Dao
public interface MemoryDao {

    @Query("SELECT * FROM Memory")
    List<Memory> getAllMemories();

    @Insert
    void insertMemory(Memory... memories);

    @Delete
    void delete(Memory memory);
}
