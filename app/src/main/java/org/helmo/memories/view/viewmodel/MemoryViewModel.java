package org.helmo.memories.view.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

public class MemoryViewModel extends ViewModel {

    private int memoryId;

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public int getMemoryId() {
        return memoryId;
    }
}
