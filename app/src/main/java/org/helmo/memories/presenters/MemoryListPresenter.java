package org.helmo.memories.presenters;

import org.helmo.memories.model.Memory;
import org.helmo.memories.repository.MemoryRepository;

import java.util.List;

public class MemoryListPresenter {

    private List<Memory> memories;
    private final IMemoryListScreen screen;

    public interface IMemoryItemScreen {
        void showMemory(int id, String title, String description, String imagePath, String date);
    }

    public interface IMemoryListScreen {
        void refreshView(); // Pour rafraichir la vue
    }

    public MemoryListPresenter(IMemoryListScreen screen) {
        this.screen = screen;
    }

    public void loadMemories() {
        MemoryRepository.getInstance().getMemories().observeForever(memories -> {
            this.memories = memories;
            screen.refreshView();
        });
    }

    public Memory getMemory(int position) {
        return memories.get(position);
    }

    public void addMemory(String title, String description, String imagePath, String date, double lattitude, double longitude) {
        Memory memory = new Memory(title, description, imagePath, date, false, lattitude, longitude);
        memories.add(memory);
        MemoryRepository.getInstance().insertMemory(memory);
        screen.refreshView();
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
        holder.showMemory(m.getUid(), m.getTitle(), m.getDescription(), m.getImagePath(), m.getDate());
    }

}
