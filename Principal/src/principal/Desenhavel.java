package principal;

import java.util.List;

/**
 * Classe abstrata Desenhavel.
 * Um objeto desenhavel e um objeto que pode estar no campo.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public abstract class Desenhavel {
    /** Varivel para controlar se o objeto desenhavel esta vivo ou nao.*/
    protected boolean alive;
    /** Variavel da posicao do objeto desenhavel.*/
    protected Location location;
    
    /***
     * metodo de acao.
     * Cada metodo tem uma acao diferente.
     * @param currentField Campo atual.
     * @param updatedField Campo atualizado.
     * @param newAnimals Novo animal.
     */
    public abstract void act(Field currentField,Field updatedField, List newAnimals);
    
    /**
     * Metodo para verificar se o objeto esta vivo.
     * @return vivo.
     */
    protected boolean isAlive() {
        return alive;
    }
    
    /**
     * Define se o objeto está vivo ou nao.
     * @param alive true se esta vivo, false se nao.
     */
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    
    /**
     * Consulta localizacao de objeto.
     * @return localizacao do objeto.
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Define a localizacao do objeto.
     * @param row A linha da localizacao.
     * @param col A coluna da localizacao.
     */
     public void setLocation(int row, int col){
        Location novo = new Location(row,col);
        this.setLocation(novo);
    }
     
    /**
     * Define a localizacao do objeto.
     * @param location A localizacao do objeto.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
}
