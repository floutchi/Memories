package org.helmo.memories.presenters;

import org.helmo.memories.model.Memory;
import org.helmo.memories.repository.MemoryRepository;

import java.util.List;

public class MemoryListPresenter {

    private List<Memory> memories;
    private final IMemoryListScreen screen;


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
        Memory memory = new Memory("", "", "", "", false, 0, 0); //TODO
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
