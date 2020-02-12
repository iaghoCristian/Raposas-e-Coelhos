package principal;

import java.util.Random;

/**
 * Uma classe abstrata que representa um animal.
 * Animal herda de Desenhavel.
 * Raposas, coelhos e leopardos sao animais portanto herdam dessa classe.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public abstract class Animal extends Desenhavel{

    /** Variavel para controlar a idade do animal.*/
    protected int age;
    /** Um gerador de numeros aleatorios -herdado,compartilhado- para controlar a reproducao.*/
    private static final Random rand = new Random();
    
    /** Obtem a idade de reproducao de um animal.
     * @return Idade de reproducao de um animal.  
    */
    protected abstract int getBREEDING_AGE();
    
    
    /** Obtem a probabilidade de reproducao de um animal.
     * @return A probabilidade de reproducao de uma animal.
    */
    protected abstract double getBREEDING_PROBABILITY();
    
    
    /** Obtem o tamanho maximo da ninhada de um animal.
     * @return O tamanho maximo da ninhada de um animal.
    */
    protected abstract int getMAX_LITTER_SIZE();
    
    /** Obtem a idade maxima de um animal.
     * @return idade maxima de um animal.
     */
    protected abstract int getMAX_AGE();
    
    /**
     * Cria um animal, com idade 0 e indicando que esta vivo.
     */
    public Animal(){
        age = 0;
        alive = true;
    }
    
    /**
     * Consulta a idade do animal.
     * @return idade do animal.
     */
    public int getAge(){
        return age;
    }
    
    /**
     * Define a idade do animal.
     * @param age Idade do animal.
     */
    public void setAge(int age){
        this.age = age;
    }

    /**
     * Incrementa a idade do animal, se a idade
     * passar da idade maxima entao o animal morre
     */
    protected void incrementAge(){
        int age = this.getAge()+1;
        this.setAge(age);
        if(this.getAge() > getMAX_AGE()) {
            this.setAlive(false);
        }
    }

    /**
      * Gera um numero randomico que representa o numero de nascimentos,
      * se o animal puder se reproduzir.
      * @return O numero de nascimentos (pode ser zero).
    */
     protected int breed(){
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBREEDING_PROBABILITY()) {
            births = rand.nextInt(getMAX_LITTER_SIZE()) + 1;
        }
        return births;
    }
     
    /**
      * Um animal pode procriar se chegar na idade de procriacao.
      * @return Se a idade do animal ja esta na idade de procriacao.
    */
    protected boolean canBreed(){
        return this.getAge() >= getBREEDING_AGE();
    }
}
