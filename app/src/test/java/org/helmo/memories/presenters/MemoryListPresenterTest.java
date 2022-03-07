package org.helmo.memories.presenters;

import org.helmo.memories.model.Memory;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class MemoryListPresenterTest {


    @Test
    public void verifyReturnItemCount() {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = new MemoryListPresenter(mockedMemoryListScreen);

        // When
        int itemCount = memoryListPresenter.getItemCount();

        //Then
        assertEquals(0, itemCount);
    }

    @Test
    public void verifyReturnMemoryList() {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = new MemoryListPresenter(mockedMemoryListScreen);

        // When
        List<Memory> itemList = memoryListPresenter.getMemoryList();

        //Then
        assertNull(itemList);
    }

}
