package test;

import domain.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class NeoFlixListTest{

    private NeoFlix neoFlix;

    private static final String SAMPLE_LIST =
        "6 elementos\n" +
        ">The Hidden Village\n" +
        ">The First Mission\n" +
        ">The Rival\n" +
        ">The Tournament\n" +
        ">The Final Battle\n" +
        ">The Neo Ninja: 2020\n" +
        "\tThe Hidden Village\n" +
        "\tThe First Mission\n" +
        "\tThe Rival\n" +
        "\tThe Tournament\n" +
        "\tThe Final Battle\n";

    @Before
    public void setUp(){
        neoFlix = new NeoFlix();
    }


    @Test
    public void shouldListTheSampleContents(){
        assertEquals(SAMPLE_LIST, neoFlix.toString());
    }

    @Test
    public void shouldStartTheListWithTheNumberOfContents(){
        assertTrue(neoFlix.toString().startsWith("6 elementos\n"));
    }

    @Test
    public void shouldIndentTheEpisodesOfASeriesWithATab(){
        String[] lines = neoFlix.toString().split("\n");
        for (int i = 7; i <= 11; i++){
            assertEquals('\t', lines[i].charAt(0));
        }
    }


    @Test
    public void shouldListTheSameWhenCalledTwice(){
        assertEquals(neoFlix.toString(), neoFlix.toString());
    }

    @Test
    public void shouldIncludeAnAddedEpisodeInTheList(){
        neoFlix.addEpisode("Pilot", "", "10", "5", "2", "15");
        String list = neoFlix.toString();
        assertTrue(list.startsWith("7 elementos\n"));
        assertTrue(list.endsWith(">Pilot\n"));
    }

    @Test
    public void shouldListContentsInInsertionOrder(){
        neoFlix.addEpisode("Zeta", "", "10", "5", "2", "15");
        neoFlix.addEpisode("Alpha", "", "10", "5", "2", "15");
        String list = neoFlix.toString();
        assertTrue(list.indexOf(">Zeta") < list.indexOf(">Alpha"));
    }



    @Test
    public void shouldListZeroElementsWhenNothingIsSelected(){
        assertEquals("0 elementos\n", neoFlix.data(new ArrayList<Content>(), false));
    }

    @Test
    public void shouldListOnlyTheSelectedContents(){
        ArrayList<Content> selected = new ArrayList<Content>();
        selected.add(neoFlix.consult("The Rival"));
        assertEquals("1 elementos\n>The Rival\n", neoFlix.data(selected, false));
    }


}