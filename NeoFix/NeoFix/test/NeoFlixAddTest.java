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
        try
        {
            neoFlix.addEpisode("Pilot", "", "10", "5", "2", "15");
        }
        catch (NeoFlixException nfe)
        {
            nfe.printStackTrace();
        }
        assertEquals(7, neoFlix.numberContents());
    }

    @Test
    public void shouldBeAbleToConsultAnAddedEpisode(){
        try
        {
            neoFlix.addEpisode("Pilot", "", "10", "5", "2", "15");
        }
        catch (NeoFlixException nfe)
        {
            nfe.printStackTrace();
        }
        Content c = neoFlix.consult("Pilot");
        assertNotNull(c);
        assertTrue(c instanceof Episode);
        assertEquals("Pilot", c.getTitle());
    }


    @Test
    public void shouldConsultAnAddedEpisodeIgnoringCase(){
        try
        {
            neoFlix.addEpisode("Pilot", "", "10", "5", "2", "15");
        }
        catch (NeoFlixException nfe)
        {
            nfe.printStackTrace();
        }
        assertNotNull(neoFlix.consult("pILOT"));
    }


    @Test
    public void shouldAddASeriesWithExistingEpisodes(){
        try
        {
            neoFlix.addSeries("The Second Ninja", "2024", "The Rival\nThe Tournament");
        }
        catch (NeoFlixException nfe)
        {
            nfe.printStackTrace();
        }
        assertEquals(7, neoFlix.numberContents());
    }

    @Test
    public void shouldBeAbleToConsultAnAddedSeries(){
        try
        {
            neoFlix.addSeries("The Second Ninja", "2024", "The Rival\nThe Tournament");
        }
        catch (NeoFlixException nfe)
        {
            nfe.printStackTrace();
        }
        Content c = neoFlix.consult("The Second Ninja");
        assertNotNull(c);
        assertTrue(c instanceof Series);
    }

    @Test
    public void shouldAllowAnEpisodeWithAnExistingTitle(){

        try
        {
            neoFlix.addEpisode("The Hidden Village", "", "199", "120", "50", "430");
        }
        catch (NeoFlixException nfe)
        {
            nfe.printStackTrace();
        }
        assertEquals(7, neoFlix.numberContents());
    }

    @Test
    public void shouldAllowASeriesWithAnExistingTitle(){
        try
        {
            neoFlix.addSeries("The Neo Ninja", "2020", "The Hidden Village\nThe First Mission");
        }
        catch (NeoFlixException nfe)
        {
            nfe.printStackTrace();
        }
        assertEquals(7, neoFlix.numberContents());
    }
    
    /**
     *Pruebas para el caso uno de la parte II.C.i
     * 
     */
    @Test(expected = NeoFlixException.class)
    public void shouldDenyASeriesWithAnExistingTitle() throws NeoFlixException{
        neoFlix.addSeries("The Neo Ninja", "2020", "The Hidden Village\nThe First Mission");
    }
    
    /**
     *Pruebas para el caso uno de la parte II.C.ii
     * 
     */
        @Test(expected = NeoFlixException.class)
    public void shouldWarnAboutTheWrongTypeOfData() throws NeoFlixException{
    neoFlix.addSeries("TestSeries", "abc", "");
    }
    
    /**
     *Pruebas para el caso uno de la parte II.C.iii
     */
     @Test(expected = NeoFlixException.class)
     public void shouldDenyAnEpisodeWithMoreCompletedThanAttempts() throws NeoFlixException{
         neoFlix.addEpisode("Pilot", "", "10", "20", "2", "15");
     }
     
    @Test(expected = NeoFlixException.class)
     public void shouldDenyAnEpisodeWithSumVotesGreaterThanTenTimesVotes() throws NeoFlixException{
         neoFlix.addEpisode("Pilot", "", "10", "5", "2", "30");
    }

    /**
     * Pruebas para el caso uno de la parte II.C.iv
     */
    @Test(expected = NeoFlixException.class)
    public void shouldDenyASeriesWithAnEmptyName() throws NeoFlixException{
        neoFlix.addSeries("", "2020", "The Rival\nThe Tournament");
    }
    
    @Test(expected = NeoFlixException.class)
    public void shouldDenyASeriesWithABlankName() throws NeoFlixException{
        neoFlix.addSeries("   ", "2020", "The Rival\nThe Tournament");
    }
    
    /**
     * Prueba de ejecución normal
     */
    @Test
    public void shouldAddASeriesWithAName() throws NeoFlixException{
        neoFlix.addSeries("Nueva Serie", "2024", "The Rival\nThe Tournament");
        assertEquals(7, neoFlix.numberContents());
    }
}