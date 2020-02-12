package principal;

import java.util.List;

/**
 * Classe grama.
 * Classe grama herda de Desenhavel e pode ser comida por coelhos.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public class Grama extends Desenhavel{
    /**
     * Cria a grama, indicando que esta viva.
     */
    public Grama(){
        alive = true;
    }
    
    /**
     * Metodo de acao, se a grama esta viva entao deve estar em uma posicao do campo.
     * @param currentField Campo atual.
     * @param updatedField Campo atualizado.
     * @param newGrama Nova Grama.
     */
    @Override
    public void act(Field currentField,Field updatedField, List newGrama){
        if (alive) {
            Location location = this.getLocation();
            updatedField.place(this, location);
        }
    }
}
