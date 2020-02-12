package principal;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * Um simples simulador de predador-presa, baseado em um campo contendo
 * raposas, leopardos e coelhos.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public class Simulator {
    
    /** As variáveis finais estáticas privadas representam
       informações de configuração para a simulação.*/
    /** A largura padrao da grade.*/
    private static final int DEFAULT_WIDTH = 50;
    /** A profundidade padrão da grade.*/
    private static final int DEFAULT_DEPTH = 50;
    /** A probabilidade de que uma raposa seja criada em qualquer posição da grade.*/
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    /** A probabilidade de que um coelho seja criada em qualquer posição da grade.*/
    private static final double RABBIT_CREATION_PROBABILITY = 0.07;    
    /** A probabilidade de que um leopardo seja criada em qualquer posição da grade.*/
    private static final double LEOPARDO_CREATION_PROBABILITY = 0.01;   
   
    /** Lista de animais no campo*/
    private List animals;
    /** Lista de animais recem nascidos*/
    private List newAnimals;
    /** O estado atual do campo*/
    private Field field;
    /** Um segundo campo, usado para construir o próximo estágio da simulação.*/
    private Field updatedField;
    /** O passo atual da simulação.*/
    private int step;
    /** Uma visão gráfica da simulação.*/
    private SimulatorView view;
    
    /**
     * Constroi um campo de simulação com tamanho padrão.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Cria um campo de simulação com o tamanho dado.
     * @param depth Profundidade do campo. Deve ser maior que zero.
     * @param width Largura do campo. Deve ser maior que zero.
     */
    public Simulator(int depth, int width) {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        animals = new ArrayList();
        newAnimals = new ArrayList();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new AnimatedView(depth, width);
        view.setColor(Fox.class, Color.red);
        view.setColor(Rabbit.class, Color.pink);
        view.setColor(Leopardo.class,Color.black);
        view.setColor(Grama.class, Color.green);
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Executa a simulação a partir do seu estado atual por um período razoavelmente longo,
     * ex. 500 passos.
     */
    public void runLongSimulation() {
        simulate(500);
    }
    
    /**
     * Executa a simulacao a partir do seu estado atual para o número de etapas especificado.
     * Para antes do número de passos dado, se ele deixar de ser viável.
     * @param numSteps numero de passos a ser executado.
     */
    public void simulate(int numSteps) {
        try {
            for(int step = 1; step <= numSteps && view.isViable(field); step++) {
                simulateOneStep();
                Thread.sleep(500);
            }
             
        }
        catch (Exception e) {
        System.out.println(e);
        } 
    }
    
    /**
     * Executa a simulação a partir do seu estado atual para um unico passo.
     * Itera sobre todo o campo atualizando o estado de cada
     * raposa, coelho e leopardo.
     */
    public void simulateOneStep() {
        step++;
        newAnimals.clear();
        
        // let all animals act
        for(Iterator<Desenhavel> it = animals.iterator(); it.hasNext(); ) {
            Desenhavel animal = it.next();
            animal.act(field,updatedField,newAnimals);
            
            if (! animal.isAlive()) {
                it.remove();
            }
        }
        // add new born animals to the list of animals
        animals.addAll(newAnimals);
        
        // Swap the field and updatedField at the end of the step.
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // display the new field on screen
        view.showStatus(step, field);
    }
        
    /**
     * Reseta a simulação para uma posição inicial.
     */
    public void reset() {
        step = 0;
        animals.clear();
        field.clear();
        updatedField.clear();
        populate(field);
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Preenche o campo com raposas, coelhos, leopardos e grama.
     * @param field Campo a ser populado.
     */
    private void populate(Field field){
        Random rand = new Random();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    animals.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    animals.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                }
                else if(rand.nextDouble() <= LEOPARDO_CREATION_PROBABILITY){
                    Leopardo leo = new Leopardo(true);
                    animals.add(leo);
                    leo.setLocation(row, col);
                    field.place(leo, row, col);
                
                }
                else{
                    Grama grama = new Grama();
                    animals.add(grama);
                    grama.setLocation(row,col);
                    field.place(animals, row,col);
                    
                }
                // else leave the location empty.
            }
        }
        Collections.shuffle(animals);
    }
}