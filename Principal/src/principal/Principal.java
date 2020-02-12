package principal;

/**
 * Metodo principal que cria e executa a simulacao.
 * @author Iagho_Joseane_Tulio
 * @version 05-12-2018.
 */
public class Principal{
    /**
     * Metodo main que cria o simulador e inicia a simulacao.
     * @param args 
     */
    public static void main(String[] args){
    Simulator simulator = new Simulator();

    simulator.runLongSimulation();
    //simulator.simulateOneStep();
  }
    
}