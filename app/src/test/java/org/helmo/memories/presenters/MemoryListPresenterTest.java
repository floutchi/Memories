package org.helmo.memories.presenters;

import org.helmo.memories.MemoriesApplication;
import org.helmo.memories.model.Memory;
import org.helmo.memories.repository.MemoryRepository;
import org.helmo.memories.repository.RepositoryUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
public class MemoryListPresenterTest {


    @Before
    public void init(){
        RepositoryUtil.initRepository(mock(MemoryRepository.class));
    }

    @Test
    public void verifyCallRefreshViewOnAddMemory() {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = new MemoryListPresenter(mockedMemoryListScreen);

        memoryListPresenter.setMemories(new ArrayList<>());
        try {
            memoryListPresenter.addMemory("test", "test", "test", "test", 10, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(mockedMemoryListScreen).refreshView(Mockito.any());

    }


    @Test
    public void verifyReturnItemCount() {

        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter memoryListPresenter = new MemoryListPresenter(mockedMemoryListScreen);

        // When
        memoryListPresenter.setMemories(new ArrayList<>());
        int itemCount = memoryListPresenter.getItemCount();

        //Then
        assertEquals(0, itemCount);
    }

    @Test
    public void verifyReturnZeroOnNoInitMemoryList() {
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

        memoryListPresenter.setMemories(new ArrayList<>());
        // When
        List<Memory> itemList = memoryListPresenter.getMemoryList();

        //Then
        assertEquals(0, itemList.size());
    }

    @Test
    public void verifyshowMemoryOn()  {
        // Given
        MemoryListPresenter.IMemoryListScreen mockedMemoryListScreen = mock(MemoryListPresenter.IMemoryListScreen.class);
        MemoryListPresenter.IMemoryItemScreen iMemoryItemScreen = mock(MemoryListPresenter.IMemoryItemScreen.class);
        MemoryListPresenter memoryListPresenter = new MemoryListPresenter(mockedMemoryListScreen);


        Memory memory =  new Memory("","","","",true, 5 ,5);
        List<Memory> memories =  new ArrayList<>();
        memories.add(memory);
        memoryListPresenter.setMemories(memories);
        // When


        memoryListPresenter.showMemoryOn(iMemoryItemScreen, 0);

        //Then
        verify(iMemoryItemScreen).showMemory(0, "", "", "", "", true);

    }


}
