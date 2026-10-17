package test;

import domain.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad (ingenieria inversa) de la funcionalidad ADICIONAR
 * de la capa de dominio: NeoFlix.addEpisode(...) y NeoFlix.addSeries(...).
 */
public class NeoFlixAddTest{

    private NeoFlix neoFlix;

    @Before
    public void setUp(){
        neoFlix = new NeoFlix();   
    }



    @Test
    public void shouldStartWithTheSampleContents(){
        assertEquals(6, neoFlix.numberContents());
    }



    @Test
    public void shouldAddAnEpisode(){
        neoFlix.addEpisode("Pilot", "", "10", "5", "2", "15");
        assertEquals(7, neoFlix.numberContents());
    }

    @Test
    public void shouldBeAbleToConsultAnAddedEpisode(){
        neoFlix.addEpisode("Pilot", "", "10", "5", "2", "15");
        Content c = neoFlix.consult("Pilot");
        assertNotNull(c);
        assertTrue(c instanceof Episode);
        assertEquals("Pilot", c.getTitle());
    }


    @Test
    public void shouldConsultAnAddedEpisodeIgnoringCase(){
        neoFlix.addEpisode("Pilot", "", "10", "5", "2", "15");
        assertNotNull(neoFlix.consult("pILOT"));
    }


    @Test
    public void shouldAddASeriesWithExistingEpisodes(){
        neoFlix.addSeries("The Second Ninja", "2024", "The Rival\nThe Tournament");
        assertEquals(7, neoFlix.numberContents());
    }

    @Test
    public void shouldBeAbleToConsultAnAddedSeries(){
        neoFlix.addSeries("The Second Ninja", "2024", "The Rival\nThe Tournament");
        Content c = neoFlix.consult("The Second Ninja");
        assertNotNull(c);
        assertTrue(c instanceof Series);
    }

    @Test
    public void shouldAllowAnEpisodeWithAnExistingTitle(){

        neoFlix.addEpisode("The Hidden Village", "", "199", "120", "50", "430");
        assertEquals(7, neoFlix.numberContents());
    }

    @Test
    public void shouldAllowASeriesWithAnExistingTitle(){
        neoFlix.addSeries("The Neo Ninja", "2020", "The Hidden Village\nThe First Mission");
        assertEquals(7, neoFlix.numberContents());
    }
}