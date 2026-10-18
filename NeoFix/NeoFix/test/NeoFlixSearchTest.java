package test;

import domain.*;
import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad (ingenieria inversa) de la funcionalidad Buscar
 * 
 */
public class NeoFlixSearchTest{

    private NeoFlix neoFlix;

    @Before
    public void setUp(){
        neoFlix = new NeoFlix();   
    }
    @Test
    public void shouldSelectEverythingStartingWithT(){
        assertEquals(6, neoFlix.select("T").size());
    }
    
    @Test
    public void shouldSelectOnlyTheMatchingContentIgnoringCase(){
        ArrayList<Content> selected = neoFlix.select("the r");
        assertEquals(1, selected.size());
        assertEquals("The Rival", selected.get(0).getTitle());
    }
    
    @Test
    public void shouldFindNothingWhenNoTitleMatches(){
        assertEquals("0 elementos\n", neoFlix.search("Zzz"));
    }
}   