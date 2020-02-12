package principal;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Um modelo simples de um Leopardo.
 * Os leopardos envelhecem, se movem, comem coelhos e raposas, 
 * alem de poderem pocriar e morrer de fome ou velhice.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public class Leopardo extends Animal {
    /** Caracteristicas compartilhadas por todos os leopardos(campos estaticos).*/
    
    /** A idade em que um leopardo pode comecar a se reproduzir.*/
    private static final int BREEDING_AGE = 10;
    /** A idade maxima que um leopardo pode viver.*/
    private static final int MAX_AGE = 80;
    /** A probabilidadde de uma leopardo reproduzir.*/
    private static final double BREEDING_PROBABILITY = 0.05;
    /** O numero maximo de nascimentos.*/
    private static final int MAX_LITTER_SIZE = 2;
    /** O numero de passos que um leopardo pode seguir antes de comer novamente.*/
    private static final int ANIMALS_FOOD_VALUE = 9;
    /** Um gerador de numeros aleatorios -compatilhado- para controlar a reproducao.*/
    private static final Random rand = new Random();
   
    /** Caracteristicas individuais (campos de instancia).*/
    
    /** O nível de comida dos leopardos, que é aumentado comendo animais.*/
    private int foodLevel;
    
    /**
     * Cria um leopardo. Um leopardo pode ser criado como recem-nascido
     * (idade zero e sem fome) ou com idade aleatoria.
     * @param randomAge Se true, o leopardo terá idade aleatoria e nivel de fome
     */
    public Leopardo(boolean randomAge){
        super();
        if(randomAge) {
            int age = rand.nextInt(MAX_AGE);
            this.setAge(age);
            foodLevel = rand.nextInt(ANIMALS_FOOD_VALUE);
        }
        else {
            // leave age at 0
            foodLevel = ANIMALS_FOOD_VALUE;
        }
    }
    
    /**
     * Faz o leopardo ficar mais faminto, isso pode causar a morte do leopardo
     */
    private void incrementHunger(){
        foodLevel--;
        if(foodLevel <= 0) {
            this.setAlive(false);
        }
    }
    
    /**
     * Diz ao leopardo para procurar animais adjacentes a sua localizacao atual
     * @param field O campo em que deve aparecer
     * @param location Onde esta localizado o campo
     * @return Onde a comida foi encontrada, ou null, se nao foi
     */
    private Location findFood(Field field, Location location){
        Iterator adjacentLocations =
                          field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setEaten();
                    foodLevel = ANIMALS_FOOD_VALUE;
                    return where;
                }
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setEaten();
                    foodLevel = ANIMALS_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Isso é o que o leopardo faz a maior parte do tempo: caça animais.
     * No processo pode se reproduzir ou morrer - de fome ou de velhice.
     * @param currentField Campo atual
     * @param updatedField Campo atualizado
     * @param newAnimals Novo animal
     */
    @Override
    public void act(Field currentField,Field updatedField, List newAnimals){
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            // New Leopardos are born into adjacent locations.
            int births = super.breed();
            for(int b = 0; b < births; b++) {
                Leopardo newLeo = new Leopardo(false);
                newAnimals.add(newLeo);
                Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                newLeo.setLocation(loc);
                updatedField.place(newLeo, loc);
            }
            // Move towards the source of food if found.
            Location newLocation = findFood(currentField, this.getLocation());
            if(newLocation == null) {  // no food found - move randomly
                newLocation = updatedField.freeAdjacentLocation(this.getLocation());
            }
            if(newLocation != null) {
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
     * Diz ao leopardo que ele esta morto agora :(
     */
    protected void setEaten(){
        this.setAlive(false);
    }
    
    /**
     * Consulta idade maxima de um leopardo.
     * @return idade maxima de um leopardo.
     */
    @Override
    public int getMAX_AGE(){
        return MAX_AGE;
    }
    
    /**
     * Consulta a idade de procriacao de um leopardo.
     * @return idade de procriacao de um leopardo.
     */
    @Override
    public int getBREEDING_AGE(){
        return BREEDING_AGE;
    }
    
    /**
     * Consulta probabilidade de procriacao de um leopardo.
     * @return probabilidade de procriacao de um leopardo.
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
