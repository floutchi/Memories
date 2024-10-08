package org.helmo.memories.repository;

import androidx.lifecycle.LiveData;

import org.helmo.memories.model.Memory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemoryRepository {

    protected static MemoryRepository instance;

    private final MemoryDao memoryDao = MemoryDataBase.getInstance().memoryDao();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public MemoryRepository() {}

    public LiveData<List<Memory>> getMemories() {
        return memoryDao.getAllMemories();
    }

    public LiveData<List<Memory>> filterMemory(String word) {
        return memoryDao.filterMemory("%"+word+"%");
    }

    public LiveData<Memory> getMemoryById(int id) { return memoryDao.getMemory(id);}

    public void deleteMemory(int id) {
        executor.execute(() -> memoryDao.deleteByID(id));
    }

    public void insertMemory(final Memory memory) {
        executor.execute(() -> memoryDao.insertMemory(memory));
    }

    public void updateMemory(int id, String title,
                             String description,
                             String imagePath,
                             String date,
                             double lattitude, double longitude) {
        executor.execute(() -> memoryDao.updateMemory(id, title, description, imagePath, date, lattitude, longitude));

    }

    public void setFavorite(int id) {
        executor.execute(() -> memoryDao.setFavorite(id));
    }

    public void setUnFavorite(int id) {
        executor.execute(() -> memoryDao.setUnFavorite(id));
    }

    public static MemoryRepository getInstance() {
        if(instance == null) {
            instance = new MemoryRepository();
        }
        return instance;
    }

}
