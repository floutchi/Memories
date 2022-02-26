package org.helmo.memories.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.helmo.memories.model.Memory;

@Database(entities = {Memory.class}, version = 1)
public abstract class MemoryDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "memories_database";
    public abstract MemoryDao memoryDao();
    private static MemoryDataBase instance;

    public static MemoryDataBase getInstance() {
        if(instance == null) {
            throw new IllegalStateException("Memory report database must be initialized");
        }
        return instance;
    }

    public static void initDatabase(Context context) {
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), MemoryDataBase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    public static void disconnectDatabase() {
        instance = null;
    }
}
