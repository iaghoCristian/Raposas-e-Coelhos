package principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe AnimatedView cria uma visualizacao animada.
 * Implementa a interface SimulatorView.
 * Exibe um retângulo colorido para cada local
 * representando seu conteúdo. Ele usa uma cor de plano de fundo padrão.
 * As cores para cada tipo de espécie podem ser definidas usando o metodo setColor.
 * @author Iagho_Joseane_Tulio.
 * @version 05-12-2018.
 */
public class AnimatedView extends JFrame implements SimulatorView{
    
    /** Cores usadas para locais vazios.*/
    private static final Color EMPTY_COLOR = Color.white;
    /**Cor usada para objetos sem cor definida.*/
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    
    /** Um mapa para armazenar cores dos participantes da simulação.*/
    private HashMap colors;
    /** Um objeto de estatísticas que computa e armazena informações de simulação.*/
    private FieldStats stats;

    /**
     * Cria uma visualização da largura e altura especificadas.
     */
    public AnimatedView(int height, int width){
        stats = new FieldStats();
        colors = new HashMap();

        setTitle("Simulação de Caçada");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        
        setLocation(100, 50);
        
        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    
    /**
     * Define uma cor a ser usada para uma determinada classe de objetos.
     */
    @Override
    public void setColor(Class animalClass, Color color){
        colors.put(animalClass, color);
    }

    /**
     * Consulta uma cor a ser usada para uma determinada classe de objetos.
     */
    @Override
    public Color getColor(Class animalClass){
        Color col = (Color)colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Mostra o status atual do campo.
     * @param step Mostra qual etapa de iteracao.
     * @param field Campo atual.
     */
    @Override
    public void showStatus(int step, Field field){
        if(!isVisible())
            setVisible(true);

        stepLabel.setText(STEP_PREFIX + step);

        stats.reset();
        fieldView.preparePaint();
            
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }
    

    /**
     * Determina se a simulação deve continuar a ser executada.
     * @return true Se houver mais de uma espécie viva.
     */
    @Override
    public boolean isViable(Field field){
        return stats.isViable(field);
    }
    
    /**
     * Fornece uma visão gráfica de um campo retangular. Isto é
     * uma classe aninhada (uma classe definida dentro de uma classe) que
     * define um componente personalizado para a interface do usuário. 
     * Este componente exibe o campo.
     * Isso é bastante avançado GUI stuff - você pode ignorar isso
     *  para o seu projeto, se quiser.
     */
    private class FieldView extends JPanel{
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Crie um novo componente FieldView.
         */
        public FieldView(int height, int width){
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }
        
        /**
         * Prepara para uma nova rodada de pintura. Desde o componente
         * pode ser redimensionado, calcula o fator de escala novamente.
         */
        public void preparePaint(){
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Pinte na localização da grade neste campo em uma determinada cor.
         */
        public void drawMark(int x, int y, Color color){
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        @Override
        public void paintComponent(Graphics g){
            if(fieldImage != null) {
                g.drawImage(fieldImage, 0, 0, null);
            }
        }
    }
}

