package principal;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Esta classe coleta e fornece alguns dados estatísticos sobre o estado 
 * de um campo.
 * E flexivel: criara e mantera um contador para qualquer classe de 
 * objeto encontrada dentro do campo.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */

public class FieldStats{
    /** Contador para cada tipo de entidade (fox, rabbit, etc.) na simulacao.*/
    private HashMap counters;
    /** Se os contadores estao atualmente atualizados.*/
    private boolean countsValid;

    /**
      * Constroi um objeto de estatistica de campo.
    */
    public FieldStats(){
        /** inicializa uma colecao de contadores para cada tipo de animal
        //que podemos encontrar.*/
        counters = new HashMap();
        countsValid = true;
    }

    /**
     * Consulta a populacao de um campo.
      * @return Uma string descrevendo quais objetos estao no campo.
    */
    public String getPopulationDetails(Field field){
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
     
    /**
      * Invalidar o conjunto atual de estatísticas; 
      * redefinir todas as contagens para zero.
    */
    public void reset(){
        countsValid = false;
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter cnt = (Counter) counters.get(keys.next());
            cnt.reset();
        }
    }

    /**
      * Incrementar a contagem para uma classe de animal.
    */
    public void incrementCount(Class animalClass){
        Counter cnt = (Counter) counters.get(animalClass);
        if(cnt == null) {
            // we do not have a counter for this species yet - create one
            cnt = new Counter(animalClass.getName());
            counters.put(animalClass, cnt);
        }
        cnt.increment();
    }

    /**
      * Indique que uma contagem de animais foi concluida.
    */
    public void countFinished(){
        countsValid = true;
    }

    /**
      * Determine se a simulação ainda e viavel.
      * I.e., deve continuar a ser executado.
      * @param field Campo a ser avaliado
      * @return true Se houver mais de uma especie viva.
    */
    public boolean isViable(Field field){
        // How many counts are non-zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            if(info.getCount() > 0 ) {
                if (info.getName() != "principal.Grama") {
                    nonZero++;
                }
            }
        }
        return nonZero > 1;
    }
    
    /**
      * Gera contagens do número de raposas, coelhos e leopardos.
      * Estes não são mantidos atualizados como raposas, leopardos
      * e coelhos, são colocados no campo, mas somente quando 
      * uma solicitação é feita para a informação.
    */
    private void generateCounts(Field field){
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
}