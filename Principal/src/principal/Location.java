package principal;

/**
 * Represente uma localização em uma grade retangular.
 * 
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public class Location{
    /** Posicao de linha e coluna.*/
    private int row;
    private int col;

    /**
     * Representa uma localizacao com linha e coluna.
     * @param row A linha
     * @param col A coluna
     */
    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    /**
     * Implementa a igualdade de conteudo.
     * @return a linha e a coluna se for igual,
     *         se nao, retorna false.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }
    
    /**
     * Retorna uma string na forma linha,coluna.
     * @return Uma string representando a localizacao.
     */
    @Override
    public String toString(){
        return row + "," + col;
    }
    
    /**
     * Usa os 16 bits principais para o valor da linha e o inferior para
     * a coluna. Exceto por grades muito grandes, isso deve gerar um
     * codigo hash exclusivo para cada par (linha,coluna).
     * @return 
     */
    @Override
    public int hashCode(){
        return (row << 16) + col;
    }
    
    /**
     * Consulta linha.
     * @return A linha.
     */
    public int getRow(){
        return row;
    }
    
    /**
     * Consulta coluna.
     * @return A coluna.
     */
    public int getCol(){
        return col;
    }
}