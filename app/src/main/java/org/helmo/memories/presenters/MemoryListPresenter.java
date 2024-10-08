package org.helmo.memories.presenters;

import org.helmo.memories.model.Memory;
import org.helmo.memories.model.factories.MemoryFactories;
import org.helmo.memories.repository.MemoryRepository;

import java.util.List;

public class MemoryListPresenter {

    private List<Memory> memories;
    private final IMemoryListScreen screen;

    public interface IMemoryItemScreen {
        void showMemory(int id, String title, String description, String imagePath, String date, boolean favorite);
    }

    public interface IMemoryListScreen {
        void refreshView(List<Memory> memories); // Pour rafraichir la vue
        void refreshView();
    }


    public MemoryListPresenter(IMemoryListScreen screen) {
        this.screen = screen;
    }

    public void loadMemories() {
        System.out.println(MemoryRepository.getInstance().getMemories());
        MemoryRepository.getInstance().getMemories().observeForever(memories -> {
            this.memories = memories;
            screen.refreshView();
        });
    }

    public void filterMemory(String word) {
        MemoryRepository.getInstance().filterMemory(word).observeForever(memories -> {
            this.memories = memories;
            screen.refreshView(memories);
        });
    }

    public void addMemory(String title, String description, String imagePath, String date, double lattitude, double longitude) throws Exception {
        Memory memory = MemoryFactories.createMemory(title, description, imagePath, date, lattitude, longitude);
        memories.add(memory);
        MemoryRepository.getInstance().insertMemory(memory);
        screen.refreshView(memories);
    }

    public int getItemCount() {
        if(memories == null) {
            return 0;
        } else {
            return memories.size();
        }
    }

    public List<Memory> getMemoryList() {
        return memories;
    }

    public void showMemoryOn(IMemoryItemScreen holder, int position) {
        Memory m = memories.get(position);
        holder.showMemory(m.getUid(), m.getTitle(), m.getDescription(), m.getImagePath(), m.getDate(), m.isFavorite());
    }


    public void setMemories(List<Memory> memories) {
        this.memories = memories;
    }
}
