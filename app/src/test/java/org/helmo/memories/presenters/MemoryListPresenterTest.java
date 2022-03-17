package org.helmo.memories.presenters;

import org.helmo.memories.MemoriesApplication;
import org.helmo.memories.model.Memory;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
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
    public void verifyReturnMemoryListOnNoLoad() {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = new MemoryListPresenter(mockedMemoryListScreen);

        // When
        List<Memory> itemList = memoryListPresenter.getMemoryList();

        //Then
        assertNull(itemList);
    }


    @Test
    public void verifyLoadMemoryList()  {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = mock(MemoryListPresenter.class);
        MemoriesApplication memoriesApplication = mock(MemoriesApplication.class);

        memoriesApplication.onCreate();

        memoryListPresenter.loadMemories();
        verify(memoryListPresenter).loadMemories();

    }

    @Test
    public void verifyToAddElementMemoryList() throws Exception {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = mock(MemoryListPresenter.class);
        MemoriesApplication memoriesApplication = mock(MemoriesApplication.class);

        memoriesApplication.onCreate();

        memoryListPresenter.addMemory("rezr","fezrz","efzef","fez",5,5);

        verify(memoryListPresenter).addMemory("rezr","fezrz","efzef","fez",5,5);

    }

    @Test
    public void filterElementMemoryList()  {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = mock(MemoryListPresenter.class);
        MemoriesApplication memoriesApplication = mock(MemoriesApplication.class);

        memoriesApplication.onCreate();

        memoryListPresenter.filterMemory("mots");

        verify(memoryListPresenter).filterMemory("mots");

    }

    @Test
    public void verifyshowMemoryOn()  {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter.IMemoryItemScreen iMemoryItemScreen = mock(MemoryListPresenter.IMemoryItemScreen.class);
        MemoryListPresenter memoryListPresenter = mock(MemoryListPresenter.class);


        Memory memory =  new Memory("","","","",true, 5 ,5);
        List<Memory> memories =  new ArrayList<>();
        memories.add(memory);
        memoryListPresenter.setMemories(memories);
        // When


        memoryListPresenter.showMemoryOn(iMemoryItemScreen, 0);

        //Then
        verify(memoryListPresenter).showMemoryOn(iMemoryItemScreen, 0);

    }


}
