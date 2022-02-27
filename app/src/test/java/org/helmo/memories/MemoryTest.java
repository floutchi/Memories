package org.helmo.memories;

import org.helmo.memories.model.Memory;
import org.junit.Test;
import static org.junit.Assert.*;
public class MemoryTest {

    @Test
    public void getTitle() {
        Memory memory = new Memory("a","","","",false,1,1);
        assertEquals("a", memory.getTitle());
    }
    @Test
    public void getDescription() {
        Memory memory = new Memory("","a","","",false,1,1);
        assertEquals("a", memory.getDescription());
    }
    @Test
    public void getImagePath() {
        Memory memory = new Memory("","","a","",false,1,1);
        assertEquals("a", memory.getImagePath());
    }
    @Test
    public void getDate() {
        Memory memory = new Memory("","","","a",false,1,1);
        assertEquals("a", memory.getDate());
    }
    @Test
    public void getFavoriteValueIsFalse() {
        Memory memory = new Memory("","","","a",false,1,1);
        assertFalse(memory.isFavorite());
    }
    @Test
    public void getFavoriteValueIsTrue() {
        Memory memory = new Memory("","","","a",true,1,1);
        assertTrue(memory.isFavorite());
    }
    @Test
    public void getLattitude() {
        Memory memory = new Memory("","","","a",true,1,1);
        assertEquals(1,memory.getLattitude(), 0);
    }
    @Test
    public void getLongitide() {
        Memory memory = new Memory("","","","a",true,1,1);
        assertEquals(1,memory.getLongitude(), 0);
    }
    @Test
    public void setDate() {
        Memory memory = new Memory("","","","a",true,1,1);
        memory.setDate("52");
        assertEquals("52",memory.getDate() );
    }

    @Test
    public void setDescription() {
        Memory memory = new Memory("","","","a",true,1,1);
        memory.setDescription("52");
        assertEquals("52",memory.getDescription() );
    }

    @Test
    public void setTitle() {
        Memory memory = new Memory("","","","a",true,1,1);
        memory.setTitle("52");
        assertEquals("52",memory.getTitle() );
    }

    @Test
    public void chaneStatutStartValueIsTrue() {
        Memory memory = new Memory("","","","a",true,1,1);
        memory.changeStatut();
        assertFalse(memory.isFavorite());
    }

    @Test
    public void chaneStatutStartValueIsFalse() {
        Memory memory = new Memory("","","","a",false,1,1);
        memory.changeStatut();
        assertTrue(memory.isFavorite());
    }

    @Test
    public void changeCoordinate() {
        Memory memory = new Memory("","","","a",false,1,1);
        memory.changeLocalisation(25,25);
        assertEquals(25, memory.getLongitude(),0);
        assertEquals(25, memory.getLattitude(),0);
    }





}
