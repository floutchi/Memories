package org.helmo.memories.presenters;

import androidx.lifecycle.Observer;

import org.helmo.memories.model.Memory;
import org.helmo.memories.repository.MemoryRepository;

import java.util.List;

public class MemoryListPresenter {

    private List<Memory> memories;
    private final IMemoryListScreen screen;

    public interface IMemoryItemScreen {
       void showMemory(int id, String name, String description, String date); // Pour afficher le souvenir
    }

    public interface IMemoryListScreen {
        void refreshView(); // Pour rafraichir la vue
    }

    public MemoryListPresenter(IMemoryListScreen screen) {
        this.screen = screen;
    }

    public void loadMemories() {
        MemoryRepository.getInstance().getMemories().observeForever(memories -> {
            MemoryListPresenter.this.memories = memories;
            screen.refreshView();
        });
    }

    public int addMemory() {
        Memory memory = new Memory("", "", "", "", false); //TODO
        MemoryRepository.getInstance().insertMemory(memory);
        return memory.getUid();
    }

    public int getItemCount() {
        if(memories == null) {
            return 0;
        } else {
            return memories.size();
        }
    }

}
