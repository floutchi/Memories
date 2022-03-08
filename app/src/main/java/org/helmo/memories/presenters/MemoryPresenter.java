package org.helmo.memories.presenters;


import org.helmo.memories.model.Memory;
import org.helmo.memories.model.factories.MemoryFactories;
import org.helmo.memories.repository.MemoryRepository;

public class MemoryPresenter {

    IMemoryScreen memoryScreen;
    MemoryListPresenter memoryListPresenter;
    private Memory memory;

    int position;


    public interface IMemoryScreen {
        void showEntireMemory(String title,
                              String description,
                              String imagePath,
                              String date,
                              double lattitude, double longitude,
                              boolean favorit);
    }


    public MemoryPresenter(IMemoryScreen memoryScreen, MemoryListPresenter memoryListPresenter) {
        this.memoryScreen = memoryScreen;
        this.memoryListPresenter = memoryListPresenter;
    }


    public void loadMemory(int position) {
        this.position = position;
        MemoryRepository.getInstance().getMemoryById(position).observeForever(memory -> {
            if(memory != null) {
                this.memory = memory;
                memoryScreen.showEntireMemory(memory.getTitle(),
                        memory.getDescription(),
                        memory.getImagePath(),
                        memory.getDate(),
                        memory.getLattitude(), memory.getLongitude(),
                        memory.isFavorite());
            }
        });
    }

    public void editMemory(String title,
                           String description,
                           String imagePath,
                           String date,
                           double lattitude, double longitude) throws Exception {
        memory = MemoryFactories.createMemory(title, description, imagePath, date, lattitude, longitude);

        MemoryRepository.getInstance().updateMemory(position, memory.getTitle(),
                memory.getDescription(), memory.getImagePath(), memory.getDate(),
                memory.getLattitude(), memory.getLongitude());
    }

    public void deleteMemory(int id) {
        MemoryRepository.getInstance().deleteMemory(id);
    }

    public void setFavorite(int id) {
        if (memory.isFavorite()){
            MemoryRepository.getInstance().setUnFavorite(id);
        } else {
            MemoryRepository.getInstance().setFavorite(id);
        }

    }
}
