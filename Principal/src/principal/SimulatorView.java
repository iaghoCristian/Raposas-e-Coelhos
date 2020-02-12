package principal;

import java.awt.*;

/**
 * Uma interface da visão gráfica da grade de simulação.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018
*/
public interface SimulatorView {
    
    /**
     * Define uma cor a ser usada para uma determinada classe de animal.
     * @param animalClass Classe animal a receber a cor
     * @param color Cor a ser recebida pela classe
     */
    public void setColor(Class animalClass, Color color);
    
    /**
     * Consulta uma cor que esta sendo usada para uma determinada classe de animal.
     * @param animalClass
     * @return 
     */
    public Color getColor(Class animalClass);

    /**
     * Mostra o status atual do campo.
     * @param step Mostra qual etapa de iteracao
     * @param field Campo a ser exibido
     */
    public void showStatus(int step, Field field);
    
    /**
     * Determina se a simulação deve continuar a ser executada.
     * @param field Campo que deve ser analisado
     * @return true Se houver mais de uma espécie viva.
     */
    public boolean isViable(Field field);
    
}
