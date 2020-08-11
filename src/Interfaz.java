import java.awt.Color;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.border.LineBorder;

/**
 * Clase auxiliar (Interfaz) que extiende de la clase JFrame. 
 * Se encarga de generar los elementos multimedia utilizados para implementar
 * la interfaz gráfica y los métodos de control usados en la misma.
 *
 * @author EduardoQSR
 */
public class Interfaz extends JFrame {
    /**
     * Array de elementos JMenuItem utilizados para habilitar o no 
     * las opciones relativas al menú de la Interfaz gráfica.
     */
    private JMenuItem[] mi = new JMenuItem[3];
    
    /**
     * Variable etiqueta usada para mostrar una imágen central 
     * en el panel de la Interfaz durante la ejecución de la misma.
     */
    private JLabel estado;
    
    /**
     * Variable de tipo cadena usada para almacenar la ruta del directorio 
     * donde estan almacenados los ficheros que usaremos en el rograma.
     */
    private static final String ruta = "recursos/";
    
    /**
     * Array de etiquetas utilizadas para simular y mostrar el comportamiento 
     * de los filósofos cambiando las imágenes de estados de los mismos.
     */
    private static final JLabel[] estados = new JLabel[5];
    
    /**
     * Array de etiquetas utilizadas para simular y mostrar el comportamiento 
     * de los filósofos cambiando las imágenes de los manos de los mismos.
     */
    private static final JLabel[] manos = new JLabel[5];
    
    /**
     * Array bidimensional de enteros con tantas filas como filósofos. 
     * Cada fila está compuesta por un array de enteros que representan
     * las coordenadas X e Y de las imágenes de estado de los filósofos.
     */
    private final static int coordE[][] = {
        {337, 35},
        {50, 257},
        {145, 592},
        {529, 592},
        {615, 260}
    };

    /**
     * Array bidimensional de enteros con tantas filas como filósofos. 
     * Cada fila está compuesta por un array de enteros que representan
     * las coordenadas X e Y de las imágenes de manos de los filósofos.
     */
    private final static int coordM[][] = {
        {230, 215},
        {170, 400},
        {325, 515},
        {475, 400},
        {415, 215}
    };
    
    /**
     * Variable booleana que habilita o no la ejecución en bucle 
     * de la lógica de los filósofos y sus métodos.
     */
    protected static boolean sw = true;    
    
    /**
     * Constructor de la Interfaz Gráfica implementada.
     * Genera e inicializa la Interfaz y los elementos multimedia utilizados
     * para visualizar de forma interactiva la ejecución de la aplicación.
     */
    protected Interfaz() {
        JPanel panel = new JPanel(null);                                        // Creamos un panel para dibujar la interfaz gráfica
        //JMenuBar mbar = new JMenuBar();                                         // a su vez, creamos un menú para operar con las
        //JMenu menu = new JMenu("Opciones");                                     // distintas opciones que habilitaremos
        String nomMI[] = {"Comenzar", "Salir"};

        JLabel escenario = new JLabel(new ImageIcon(                            // Dibujamos la capa principal de la interfaz 
                Interfaz.class.getResource(ruta + "escenario.png")));           // a partir de una imágen predefinida como base
        escenario.setBorder(new LineBorder(Color.black, 1, true));
        for (int i = 0; i < Main.numFilosofos; i++) {
            estados[i] = new JLabel();                                          // Creamos las etiquetas que usaremos como estado de los filósofos
            panel.add(estados[i]).setBounds(coordE[i][0], coordE[i][1], 30, 30);// alternando entre diferentes imágenes para simular interactividad
            manos[i] = new JLabel();                                            // Hacemos lo mismo con las imágenes de las manos de los filósofos
            panel.add(manos[i]).setBounds(coordM[i][0], coordM[i][1], 50, 50);
        }
        estado = new JLabel(new ImageIcon(
                Interfaz.class.getResource(ruta + "empty.png")));
        panel.add(estado).setBounds(322, 346, 60, 60);
        panel.add(escenario).setBounds(2, 2, 700, 700);
        
        /*for (int i = 0; i < nomMI.length; i++) {
            mi[i] = new JMenuItem(nomMI[i]);                                    // Agregamos los elementos del menú
            menu.add(mi[i]).setAccelerator(KeyStroke.getKeyStroke(              // y una tecla asociada a cada uno de ellos
                    nomMI[i].charAt(0), KeyEvent.CTRL_DOWN_MASK));
            mi[i].addActionListener(al);
        }
        mbar.add(menu);
        mi[1].setEnabled(false); */                                               // Deshabilitamos la segunda opción del menú (Detener)
        
        try{
            Clip audio = AudioSystem.getClip();                                 // Creamos un objeto clip para reproducir 
            audio.open(AudioSystem.getAudioInputStream(                         // un audio de fondo mientras se ejecuta la aplicación
                    Interfaz.class.getResource(ruta + "audio.wav")));           // y lo repetimos en bucle
            audio.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(LineUnavailableException | UnsupportedAudioFileException | 
                IOException e) {
            System.out.println("Ha ocurrido un error: " + e.toString());
        }

        JFrame frame = new JFrame("Filósofos Comensales");                      // Creamos JFrame y le ponemos título
        frame.add(panel);                                                       // agregando el panel previamente creado
        //frame.setJMenuBar(mbar);                                                // y la barra del menú
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
                //Interfaz.class.getResource(ruta + "logo.png")));                // Le ponemos una imágen de icono a la ventana
        frame.setSize(710, 756);                                                // y le asignamos tamaño y demás parámetros
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
   
    /**
     * Creamos un escuchador de eventos para capturar las opciones
     * utilizadas durante la ejecución de la aplicación.
     */
    /*ActionListener al = (ActionEvent evt) -> {                                  // Capturamos los eventos seleccionados del menú
        switch (evt.getActionCommand())
        {                                       // para operar con las diferentes opciones establecidas
            case "Comenzar":                                                    
                //System.out.println("Ejecución comenzada");
                sw = true;                                                      // Habilitamos la ejecución en bucle de los métodos de los filósofos
                Main.iniciarHilos();                                            // e iniciamos los hilos de los mismos.
                estado.setIcon(new ImageIcon(
                        Interfaz.class.getResource(ruta + "loading.gif")));
                mi[0].setEnabled(false);                                        // Deshabilitamos la primera opción del menú (Comenzar)
                //mi[1].setEnabled(true);                                         // y habilitamos la segunda (Detener)
                break;
        }
    };*/

    /**
     * Método estático usado para cambiar las imágenes de los estados a partir 
     * de un entero (id de filósofo) y una cadena (nombre de imágen).
     * 
     * @param n int: entero que identifica a un filósofo.
     * @param nombre String: cadena que identifica un nombre de fichero.
     */
    protected static void setEstado(int n, String nombre) {
        estados[n].setIcon(new ImageIcon(
                Interfaz.class.getResource(ruta + nombre + ".png")));
    }
    
    /**
     * Método estático usado para cambiar las imágenes de las manos a partir 
     * de un entero (id de la mano/tenedor) y una cadena (nombre de imágen).
     * 
     * @param n int: entero que identifica a una mano/tenedor.
     * @param nombre String: cadena que identifica un nombre de fichero.
     */
    protected static void setMano(int n, String nombre) {
        manos[n].setIcon(new ImageIcon(
                Interfaz.class.getResource(ruta + nombre + ".png")));
    }
}