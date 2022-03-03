package org.helmo.memories.presenters;


import org.helmo.memories.model.Memory;
import org.helmo.memories.repository.MemoryRepository;

public class MemoryPresenter {

    IMemoryScreen memoryScreen;
    MemoryListPresenter memoryListPresenter;
    private Memory memory;


    public interface IMemoryScreen {
        void showEntireMemory(String title,
                              String description,
                              String imagePath,
                              String date,
                              double lattitude, double longitude);
    }


    public MemoryPresenter(IMemoryScreen memoryScreen, MemoryListPresenter memoryListPresenter) {
        this.memoryScreen = memoryScreen;
        this.memoryListPresenter = memoryListPresenter;
    }


    public void loadMemory(int position) {
        MemoryRepository.getInstance().getMemoryById(position).observeForever(memory -> {
            if(memory != null) {
                this.memory = memory;
                memoryScreen.showEntireMemory(memory.getTitle(),
                        memory.getDescription(),
                        memory.getImagePath(),
                        memory.getDate(),
                        memory.getLattitude(), memory.getLongitude());
            }
        });
    }

    public void deleteMemory(int id) {
        MemoryRepository.getInstance().deleteMemory(id);
    }

    public void setFavorite(int id) {
        MemoryRepository.getInstance().setFavorite(id);
    }
}
