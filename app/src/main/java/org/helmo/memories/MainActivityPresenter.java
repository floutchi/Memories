package org.helmo.memories;

import org.helmo.memories.model.Memory;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter {

    List<Memory> memoryList = new ArrayList<>();

    public List<Memory> getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(List<Memory> memoryList) {
        this.memoryList = memoryList;
    }

    public void addMemory(Memory memory) {
        memoryList.add(memory);
    }
}
