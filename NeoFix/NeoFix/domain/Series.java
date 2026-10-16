package domain;  
 
import java.util.ArrayList;

public class Series extends Content{
   
    private int year;
    private ArrayList<Episode> episodes;
    

    public Series(String title, int year){
        super(title);
        this.year=year;
        episodes= new ArrayList<Episode>();
    }


  
    public void addEpisode(Episode e){
        episodes.add(e);
    }
       
    
   /**
   * Returns the calculated rating
   * @return
   * @throws NeoFlixException, CONTENT_EMPTY if the series has no episodes
   *                         , VALUE_UNKNOWN if the rating for any episode is unknown
   *                         , DATA_ERROR if the rating for any episode cannot be calculated due to a data error
   */
   @Override
   public int rating() throws NeoFlixException{
       int sumaraiting=0;
       if (episodes.size()==0) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       if (episodes==null) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       for (Episode epi : episodes){
           try{
                sumaraiting += epi.rating();
            } catch(NeoFlixException excepcion){
                if(excepcion.getMessage().equals(NeoFlixException.VALUE_UNKNOWN)){
                    throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
                }
                else{
                    throw new NeoFlixException(NeoFlixException.DATA_ERROR);
                }
            }
        }
        return sumaraiting/ episodes.size();
   }
    
 
   /**
   * Returns the calculated rating, using the default value for episodes with an unknown rating and ignoring those with errors.
   * @return
   * @throws NeoFlixException, UNKNOWN_VALUE if the rating cannot be calculated.
   */

   public int rating(int default_) throws NeoFlixException{
       int sumaraiting=0;
       int contador=0;
       for (Episode epi : episodes){
           try{
                sumaraiting += epi.rating();
                contador++;
            } catch(NeoFlixException excepcion){
                if(excepcion.getMessage().equals(NeoFlixException.VALUE_UNKNOWN)){
                    //cuando no tiene valoracion se usa la que es por defecto 
                    sumaraiting += default_;
                    contador++;
                }//no incluimos el else ya que basicamente si hay un error ignora el episodio y sigu con el siguiente
            }
        }
        if (contador == 0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);//basicamente si salta esto es que fue imposible calcular el raiting
        return sumaraiting/contador;

   }
 
   //If an episode has no rating, use the average of the previous episodes or of all episodes, depending on the value of the previous parameter.
   //Throw CONTENT_EMPTY and VALUE_UNKNOWN if either of these cases occurs.
   public int rating(boolean previous) throws NeoFlixException{
       if (episodes.size()==0) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       int suma_total=0;    //suma de todos los puntaajes
       int suma_puntuacion_conocida=0; //suma de los puntajes que se conocen
       int puntuaciones_conocidas=0;  //la cantidad de puntajes que se conocen
       int ep_sin_puntuacion=0; //la cantidad de episodios que no tieneun puntaje 
       for (Episode epi : episodes){
           try{
                int resultado= epi.rating();
                suma_total += resultado;
                suma_puntuacion_conocida += resultado;
                puntuaciones_conocidas++;
            } catch(NeoFlixException excepcion){
                if(!excepcion.getMessage().equals(NeoFlixException.VALUE_UNKNOWN)){
                    throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
                }
                if(previous){
                    if (puntuaciones_conocidas==0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
                    suma_total+=suma_puntuacion_conocida/puntuaciones_conocidas;
                }
                else{
                    ep_sin_puntuacion++;
                }
            }
        }
        if(!previous){
            if (puntuaciones_conocidas==0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
            suma_total+=ep_sin_puntuacion*(suma_puntuacion_conocida/puntuaciones_conocidas); //basicamente este es el promedio de todos 
        }
        return suma_total/episodes.size();
   }
    
   @Override
   public int popularity() throws NeoFlixException{
       if (episodes.size()==0) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       int suma=0;
       for (Episode epi: episodes){
            suma += epi.popularity();
        }
        return suma/episodes.size();
   }
    
    
    @Override
    public String data(boolean withIndicators) throws NeoFlixException{
        StringBuffer answer=new StringBuffer();
        answer.append(title+": "+year+ ( withIndicators ? " ( " +popularity()+" - "+ rating()+" )":""));
        for(Episode e: episodes) {
            answer.append("\n\t"+(e.data(withIndicators)));
        }
        return answer.toString();
    } 
    

}
