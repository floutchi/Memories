package org.helmo.memories.presenters;


import org.helmo.memories.model.Memory;
import org.helmo.memories.repository.MemoryRepository;

public class MemoryPresenter {

    IMemoryScreen memoryScreen;
    private Memory memory;


    public interface IMemoryScreen {
        void showEntireMemory(String title);
    }


    public MemoryPresenter(IMemoryScreen memoryScreen) {
        this.memoryScreen = memoryScreen;
    }


    public void loadMemory(int position) {
        MemoryRepository.getInstance().getMemoryById(position).observeForever(memory -> {
            this.memory = memory;
            memoryScreen.showEntireMemory(memory.getTitle()); //TODO
        });
    }
}
