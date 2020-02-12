package principal;

/**
  * Faz um contador para um participante na simulacao.
  * Esta classe inclui uma string de identificacao e um contador
  * de quantos participantes deste tipo existem na simulacao.
  * @author Iagho_Joseane_Tulio.
  * @version 05-12-2018.
*/
public class Counter{
    
    /** Um nome para um tipo na simulacao.*/
    private String name;
    /** Quantos deste tipo existem na simulacao.*/
    private int count;

    /**
      * Cria um contado para cada tipo e atribui um nome a ele.
      * @param name  Um nome, e.g. "Fox".
    */
    public Counter(String name){
        this.name = name;
        count = 0;
    }
    
    /**
      * Consulta uma descricao de um tipo. 
      * @return Uma pequena descricao desse tipo.
    */
    public String getName(){
        return name;
    }

    /**
      * Consulta o contador de um tipo.
      * @return o contador desse tipo.
    */
    public int getCount(){
        return count;
    }

    /**
      * Incrementa o contador de um tipo de um em um.
    */
    public void increment(){
        count++;
    }
    
    /**
      * Reseta o contador para zero.
    */
    public void reset(){
        count = 0;
    }
}