package org.helmo.memories.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.helmo.memories.model.Memory;

@Database(entities = {Memory.class}, version = 1)
public abstract class MemoryDataBase extends RoomDatabase {


    public abstract MemoryDao memoryDao();
    private static MemoryDataBase instance;

    public static MemoryDataBase getInstance() {
        if(instance == null) {
            throw new IllegalStateException("Memory report database must be initialized");
        }
        return instance;
    }
}
