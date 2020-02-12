package principal;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Um modelo simples de uma raposa.
 * As raposas envelhecem, se movem, comem coelhos e morrem.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public class Fox extends Animal {
    /** Caracteristicas compartilhadas por todas as raposas(campos estaticos).*/
    
    /** A idade em que uma raposa pode comecar a reproduzir.*/
    private static final int BREEDING_AGE = 10;
    /** A idade maxima que uma raposa pode viver.*/
    private static final int MAX_AGE = 150;
    /** A probabilidade de uma raposa procriar.*/
    private static final double BREEDING_PROBABILITY = 0.11;
    /** O numero maximo de nascimentos.*/
    private static final int MAX_LITTER_SIZE = 5;
    /** O numero de passos que uma raposa pode seguir antes de comer novamente.*/
    private static final int RABBIT_FOOD_VALUE = 11;
    /** Um gerador de numeros aleatorios -compatilhado- para controlar a reproducao.*/
    private static final Random rand = new Random();

    /** Caracteristicas individuais (campos de instancia).*/
    
    /** O nivel de alimento de uma raposa, que é aumentado comendo coelhos.*/
    private int foodLevel;

    /**
     * Cria uma raposa. Uma raposa pode ser criada como um recem-nascido
     * (idade zero e sem fome), ou com idade aleatoria.
     * @param randomAge Se true, a raposa terá idade aleatoria e nivel de fome.
     */
    public Fox(boolean randomAge){
        super();
        if(randomAge) {
            int age = rand.nextInt(MAX_AGE);
            this.setAge(age);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            // leave age at 0
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * Isso é o que a raposa faz a maior parte do tempo: caça coelhos.
     * No processo, pode se reproduzir, morrer de fome ou morrer de velhice.
     * @param currentField Campo atual.
     * @param updatedField Campo atualizado.
     * @param newFoxes Nova raposa.
     */
    @Override
    public void act(Field currentField, Field updatedField, List newFoxes){
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            // New foxes are born into adjacent locations.
            int births = super.breed();
            for(int b = 0; b < births; b++) {
                Fox newFox = new Fox(false);
                newFoxes.add(newFox);
                Location loc = updatedField.randomAdjacentLocation(this.getLocation());
                newFox.setLocation(loc);
                updatedField.place(newFox, loc);
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
     * Faz a raposa ficar mais faminta, isso pode resultar na morte da raposa.
     */
    private void incrementHunger(){
        foodLevel--;
        if(foodLevel <= 0) {
            this.setAlive(false);
        }
    }
    
    /**
     * Diz à raposa para procurar coelhos adjacentes a sua localizacao atual.
     * @param field O campo em que deve aparecer.
     * @param location Onde esta localizado o campo.
     * @return Onde a comida foi encontrada, ou null, se nao foi.
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
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Diga a raposa que ela esta morta agora :(
     */
    protected void setEaten(){
        this.setAlive(false);
    }
    
    /**
     * Consulta idade maxima de uma raposa.
     * @return Idade maxima de uma raposa.
     */
    @Override
    public int getMAX_AGE(){
        return MAX_AGE;
    }

    /**
     * Consulta idade de reproducao de uma raposa.
     * @return Idade de reproducao de uma raposa.
     */
    @Override
    public int getBREEDING_AGE(){
        return BREEDING_AGE;
    }
    
    /**
     * Consulta probabilidade de uma raposa procriar.
     * @return Probabilidade de uma raposa procriar.
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
