/**
 * @author EduardoQSR
**/

import java.util.concurrent.Semaphore;

public class Main
{
    
    //Variable entera que establece la cantidad de filósofos a utilizar.
    
    protected final static int numFilosofos = 5;
    
    protected final static int[][] TenedorFilosofo = {
        {0, 4},                                                                 // Filósofo 1
        {1, 0},                                                                 // Filósofo 2
        {2, 1},                                                                 // Filósofo 3
        {3, 2},                                                                 // Filósofo 4
        {4, 3}                                                                  // Filósofo 5
    };

    /**
     * Array de Semaphores que simbolizan los palillos de los filósofos.
     * Existen tantos palillos como filósofos declarados.
     */
    protected final static Semaphore[] palSemaforo = new Semaphore[numFilosofos];
    protected static void iniciarHilos()
    {
        for (int i = 0; i < numFilosofos; i++) {                                // Creamos tantos Semaphores como número de filosofos declarados, agregándoles 1 permiso a cada uno
            palSemaforo[i] = new Semaphore(1);                                  // ya que cada palillo/Semaphore sólo puede estar siendo usado por un único filósofo al mismo tiempo.
        }
        for (int i = 0; i < numFilosofos; i++) {                                // Creamos los objetos de tipo Filosofo enviándole al constructor la variable que itera el bucle
            new Filosofo(i, palSemaforo, TenedorFilosofo).start();                  // (a modo de id), el array de Semaphores y el array de palillos, y posteriormente los iniciamos.
            Interfaz.setEstado(i, "pensando");                                  // Además les asignamos las imágenes iniciales a las etiquetas de la interfaz.
            Interfaz.setMano(i, "empty");
        }
    }
    
    /**
     * Genera una Interfaz que controla y visualiza la ejecución del programa.
     *
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Interfaz interfaz = new Interfaz();
        iniciarHilos();
    }
}