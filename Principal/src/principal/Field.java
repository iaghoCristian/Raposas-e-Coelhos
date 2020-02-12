package principal;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Representa uma grade retangular de posicoes do campo.
 * Cada posicao pode contem apenas um animal.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */

public class Field{
    private static final Random rand = new Random();
    
    /** A largura e altura do campo.*/
    private int depth, width;
    /** Armazenamento de animais.*/
    private Object[][] field;

    /**
      * Cria um campo com as dimensoes dadas.
      * @param depth A altura do campo.
      * @param width A largura do campo.
    */
    public Field(int depth, int width){
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
    }
    
    /**
      * Esvazia o campo.
    */
    public void clear(){
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
      * Coloca um animal em uma localizacao.
      * Se ja existe um animal nesse lugar, ele vai ser perdido.
      * @param animal Animal que vai ser colocado.
      * @param row A coordenada da linha.
      * @param col A coordenada da coluna.
    */
    public void place(Object animal, int row, int col){
        place(animal, new Location(row, col));
    }
    
    /**
      * Coloca um animal em uma localizacao.
      * Se ja existe um animal nesse lugar, ele vai ser perdido.
      * @param animal O animal que vai ser colocado.
      * @param location Onde o animal vai ser colocado.
    */
    public void place(Object animal, Location location){
        field[location.getRow()][location.getCol()] = animal;
    }
    
    /**
      * Consulta o objeto na localizacao, se existir um.
      * @param location Um local no campo.
      * @return O objeto na localizacao, ou Null, se nao existir nenhum.
    */
    public Object getObjectAt(Location location){
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
      * Retorna o objeto na localizacao, se existir um.
      * @param row A linha da localizacao.
      * @param col A coluna da localizacao.
      * @return O objeto na localizacao, ou Null, se nao existir nenhum..
    */
    public Object getObjectAt(int row, int col){
        return field[row][col];
    }
    
    /**
      * Gera uma localizacao randomica dos adjacentes da localizacao
      * passada,ou a mesma localizacao.
      * A localizacao retornada e sempre valida para os limites do campo
      * @param location A localizacao que queremos gegar uma adjacente.
      * @return Uma localizacao valida no campo. Esse que pode ser o mesmo 
      * da localizacao passada por parametro.
    */
    public Location randomAdjacentLocation(Location location){
        int row = location.getRow();
        int col = location.getCol();
        // Gere um deslocamento de -1, 0 ou +1 para a linha e coluna atual.
        int nextRow = row + rand.nextInt(3) - 1;
        int nextCol = col + rand.nextInt(3) - 1;
        // Verifique se o novo local está fora dos limites do campo.
        if(nextRow < 0 || nextRow >= depth || nextCol < 0 || nextCol >= width) {
            return location;
        }
        else if(nextRow != row || nextCol != col) {
            return new Location(nextRow, nextCol);
        }
        else {
            return location;
        }
    }
    
    /**
      * Tenta encontrar uma localizacao vazia nos adjacentes da localizacao
      * dada. Se não exitir, retorna ela mesma se estiver vazia, se não
      * retorna null.
      * A localizacao retornada vai estar dentro dos limites do campo.
      * @param location A localizacao que queremos gegar uma adjacente.
      * @return Uma localizacao valida dentro dos limites do campo.
      * Este pode ser o mesmo do objeto que foi passado por parametro,
      * ou returna null se todas as localizacoes ao redor estiverem cheias.
    */
    public Location freeAdjacentLocation(Location location){
        Iterator adjacent = adjacentLocations(location);
        while(adjacent.hasNext()) {
            Location next = (Location) adjacent.next();
            if(field[next.getRow()][next.getCol()] == null || field[next.getRow()][next.getCol()] instanceof Grama) {
                return next;
            }
        }
        // Verifica se a localizacao atual está vazia
        if(field[location.getRow()][location.getCol()] == null || field[location.getRow()][location.getCol()] instanceof Grama) {
            return location;
        } 
        else {
            return null;
        }
    }

    /**
      * Gere um iterador em uma lista aleatoria de locais adjacentes
      * ao determinado.
      * A lista não incluira o local em si.
      * Todos os locais estarão dentro do limite do campo.
      * @param location A localizacao a partir da qual gerar adjacencias.
      * @return Um iterador sobre locais adjacentes aquele passado.
    */
    public Iterator adjacentLocations(Location location){
        int row = location.getRow();
        int col = location.getCol();
        LinkedList locations = new LinkedList();
        for(int roffset = -1; roffset <= 1; roffset++) {
            int nextRow = row + roffset;
            if(nextRow >= 0 && nextRow < depth) {
                for(int coffset = -1; coffset <= 1; coffset++) {
                    int nextCol = col + coffset;
                    // Excluir locais invalidos e o local original.
                    if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                        locations.add(new Location(nextRow, nextCol));
                    }
                }
            }
        }
        Collections.shuffle(locations,rand);
        return locations.iterator();
    }

    /**
     * Consulta a altura do campo;
      * @return A altura do campo.
    */
    public int getDepth(){
        return depth;
    }
    
    /**
     * Consulta a largura do campo;.
      * @return A largura do campo.
    */
    public int getWidth(){
        return width;
    }
}