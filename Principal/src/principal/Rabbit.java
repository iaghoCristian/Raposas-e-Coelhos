package principal;

import java.util.List;
import java.util.Random;

/**
 * O modelo simples de um coelho.
 * Coelhos envelhecem, se movem, procriam, morrem e comem grama.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public class Rabbit extends Animal {
    /** Caracteristicas compartilhadas por todas os coelhos(campos estaticos).*/

    /** A idade em que um coelho pode comecar a se reproduzir.*/
    private static final int BREEDING_AGE = 5;
    /** A idade maxima que um coelho pode viver.*/
    private static final int MAX_AGE = 50;
    /** A probabilidadde de uma coelho reproduzir.*/
    private static final double BREEDING_PROBABILITY = 0.03;
    /** O numero maximo de nascimentos.*/
    private static final int MAX_LITTER_SIZE = 5;
    /** Um gerador de numeros aleatorios -compatilhado- para controlar a reproducao.*/
    private static final Random rand = new Random();
    
    /**
     * Cria um novo coelho. Um coelho pode ser criado com a idade zero 
     * (um recém-nascido) ou com uma idade aleatória. 
     * @param randomAge Se true, o coelho terá idade aleatoria 
     */
    public Rabbit(boolean randomAge){
        super();
        if(randomAge) {
            int age;
            age = rand.nextInt(MAX_AGE);
            this.setAge(age);
        }
    }
    
    /**
     * Isto é o que o coelho faz a maior parte do tempo: corre ao redor e come grama.
     * Às vezes, ele se reproduz ou morre de velhice.
     * @param newRabbits Lista com coelhos a serem adicionados.
     */
    @Override
    public void act(Field currentField,Field updatedField, List newRabbits) {
        incrementAge();
        if(isAlive()) {
            // New rabbits are born into adjacent locations.
            int births = super.breed();
            for(int b = 0; b < births; b++) {
                Rabbit newRabbit = new Rabbit(false);
                newRabbits.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                newRabbit.setLocation(loc);
                updatedField.place(newRabbit, loc);
            }
            Location newLocation = updatedField.freeAdjacentLocation(this.getLocation());
            // Only transfer to the updated field if there was a free location
            if(newLocation == null) {  
                newLocation = updatedField.freeAdjacentLocation(this.getLocation());
            }
            if(newLocation != null) {
                Object talvez = currentField.getObjectAt(newLocation);
                if (talvez instanceof Grama) {
                    ((Grama) talvez).setAlive(false);
                }
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                // can neither move nor stay - overcrowding - all locations taken
                this.setAlive(false);
            }
        }
    }
    
    /**
     * Diz ao coelho que ele esta morto agora :(
     */
    protected void setEaten(){
        this.setAlive(false);
    }
    
    /**
     * Consulta idade maxima de um coelho.
     * @return idade maxima de um coelho.
     */
    @Override
    public int getMAX_AGE(){
        return MAX_AGE;
    }
    
    /**
     * Consulta idade de procriacao de um coelho
     * @return idade de procriacao de um coelho.
     */
    @Override
    public int getBREEDING_AGE(){
        return BREEDING_AGE;
    }
    
    /**
     * Consulta probabilidade de procriacao de um coelho.
     * @return probabilidade de procriacao de um coelho.
     */
    @Override
    public double getBREEDING_PROBABILITY(){
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Consulta tamanho maximo de uma ninhada.
     * @return Tamanho maximo de uma ninhada.
     */
    @Override
    public int getMAX_LITTER_SIZE(){
        return MAX_LITTER_SIZE;
    }
}