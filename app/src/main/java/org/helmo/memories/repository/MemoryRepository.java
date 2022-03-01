package org.helmo.memories.repository;

import androidx.lifecycle.LiveData;

import org.helmo.memories.model.Memory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemoryRepository {

    private static MemoryRepository instance;

    private final MemoryDao memoryDao = MemoryDataBase.getInstance().memoryDao();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private MemoryRepository() {}

    public LiveData<List<Memory>> getMemories() {
        return memoryDao.getAllMemories();
    }

    public LiveData<Memory> getMemoryById(int id) { return memoryDao.getMemory(id);}

    public void updateMemory(final Memory memory) {
        executor.execute(() -> memoryDao.update(memory));
    }

    public void insertMemory(final Memory memory) {
        executor.execute(() -> memoryDao.insertMemory(memory));
    }

    public static MemoryRepository getInstance() {
        if(instance == null) {
            instance = new MemoryRepository();
        }
        return instance;
    }

}
