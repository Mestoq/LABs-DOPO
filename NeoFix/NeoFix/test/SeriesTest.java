package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SeriesTest{
   
 
    @Test
    public void shouldCalculateSeriessRating(){                              
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,199,50,430));
        s.addEpisode(new Episode("The First Mission",s,140,126,60,510));
        s.addEpisode(new Episode("The Rival",s,120,199,50,430));
        s.addEpisode(new Episode("The Tournament",s,180, 162, 90, 810));
        s.addEpisode(new Episode("The Final Battle",s, 200, 190, 120, 1080));
        try {
           assertEquals(8,s.rating());
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }    
    }    

    
    @Test
    public void shouldThrowExceptionIfTheSeriessHasNoEpisodes(){
        Series s = new Series("The Neo Ninja", 2020);
        try { 
           int value=s.rating();
           fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.CONTENT_EMPTY,e.getMessage());
        }    
    }    
    
    
    @Test
    public void shouldThrowExceptionWhenAnEpisodeRatingIsUnknown(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,199,50,430));
        s.addEpisode(new Episode("The First Mission",s,140,126,60,510));
        s.addEpisode(new Episode("The Rival",s,120,199,0,0));
        s.addEpisode(new Episode("The Tournament",s,180, 162, 90, 810));
        s.addEpisode(new Episode("The Final Battle",s, 200, 190, 120, 1080));
        try { 
           int value=s.rating();
           fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }    
    } 
    
   @Test
    public void shouldThrowExceptionIfAEpisodeContainsInvalidRatingData(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,199,50,430));
        s.addEpisode(new Episode("The First Mission",s,140,126,60,610));
        s.addEpisode(new Episode("The Rival",s,120,199,0,0));
        s.addEpisode(new Episode("The Tournament",s,180, 162, 90, 810));
        s.addEpisode(new Episode("The Final Battle",s, 200, 190, 120, 1080));
        try { 
           int value=s.rating();
           fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.DATA_ERROR,e.getMessage());
        }    
    }     
    
//pruebas de el raiting(default)
    @Test
    public void shouldusedefaultforunknownandignoreErrors(){
        Series s = new Series("s", 2020);
        s.addEpisode(new Episode("a",s,10,5,50,430));
        s.addEpisode(new Episode("b",s,10,5,0,0));
        s.addEpisode(new Episode("c",s,10,5,60,610));
        s.addEpisode(new Episode("d",s,10, 5, 90, 810));
        try { 
           assertEquals((8+2+9)/3, s.rating(2));
        } catch (NeoFlixException e) {
            fail(e.getMessage());
        }    
    }   
    
    @Test
    public void shouldThrowunknownifdefaultratingcnnotbecalculated(){
        Series s = new Series("s", 2020);
        s.addEpisode(new Episode("c",s,10,5,60,610));
        try { 
           s.rating(5);
           fail("didnt throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }    
    }   

//pruebas de el raiting(previous)
    @Test
    public void shoulduseverageofpreviousepisodes(){
        Series s = new Series("s", 2020);
        s.addEpisode(new Episode("a",s,10,5,50,400));
        s.addEpisode(new Episode("b",s,10,5,50,300));
        s.addEpisode(new Episode("c",s,10,5,0,0));
        s.addEpisode(new Episode("d",s,10, 5, 50, 500));
        try { 
           assertEquals((8+6+7+10)/4, s.rating(true));
        } catch (NeoFlixException e) {
            fail(e.getMessage());
        }    
    }   

    
    @Test
    public void shoulduseverageofllepisodes(){
        Series s = new Series("s", 2020);
        s.addEpisode(new Episode("a",s,10,5,0,0));
        s.addEpisode(new Episode("b",s,10,5,50,400));
        s.addEpisode(new Episode("c",s,10,5,50,300));
        try { 
           assertEquals((7+8+6)/3, s.rating(false));
        } catch (NeoFlixException e) {
            fail(e.getMessage());
        }    
    }
    
    
    @Test
    public void shouldthrowunknowniffirstepisodeunknownandprevious(){
        Series s = new Series("s", 2020);
        s.addEpisode(new Episode("a",s,10,5,0,0));
        s.addEpisode(new Episode("b",s,10,5,50,400));
        try { 
            s.rating(true);
            fail("didnt throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }    
    }
//pruebas popularity
    @Test
    public void shouldcalculateseriespopularity(){
        Series s = new Series("s", 2020);
        s.addEpisode(new Episode("a",s,100,50,10,50));
        s.addEpisode(new Episode("b",s,100,90,10,50));
        try { 
            assertEquals(70, s.popularity());
        } catch (NeoFlixException e) {
            fail(e.getMessage());
        }    
    }

    @Test
    public void shouldthrowcontentemptyinpopularity(){
        Series s = new Series("s", 2020);
        try { 
            s.popularity();
            fail("didnt throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.CONTENT_EMPTY,e.getMessage());
        }    
    }
    
    @Test
    public void shouldpropagatedataerrorinpopularity(){
        Series s = new Series("s", 2020);
        s.addEpisode(new Episode("a",s,100,150,10,50));
        try { 
            s.popularity();
            fail("didnt throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.DATA_ERROR,e.getMessage());
        }    
    }
}