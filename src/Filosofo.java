import java.util.Random;
import java.util.concurrent.Semaphore;

 /**
 * @author EduardoQSR
 */

public class Filosofo extends Thread
{
    private final int nFilosofo;

    /**
     * Array bidimensional de enteros que almacena los palillos que 
     * necesita un filósofo concreto para comer (a partir del número de fila).
     */
    private final int[][] palFilosofo;

    /**
     * Array de Semaphores que representa a cada palillo.
     */
    private final Semaphore[] palSemaforo;

    /**
     * Variable entera que indentifica al palillo a la izquierda 
     * del filósofo, obtenida a partir del constructor.
     */
    private final int palIzquierdo;

    /**
     * Variable entera que indentifica al palillo a la derecha 
     * del filósofo, obtenida también a partir del constructor.
     */
    private final int palDerecho;

    /**
     * Constructor de Filosófos cuyos parámetros se almacenan en 
     * variables locales para usarlas posteriormente.
     *
     * @param nFilosofo int: entero que identifica a cada filósofo.
     * @param palSemaforo Semaphore[]: array que representa a cada palillo.
     * @param palFilosofo int[][]: array bidimensional de enteros que almacena 
     * los palillos que necesita un filósofo concreto para comer.
     */
    protected Filosofo(int nFilosofo, Semaphore[] palSemaforo, int[][] palFilosofo) {
        this.nFilosofo = nFilosofo;
        this.palFilosofo = palFilosofo;
        this.palSemaforo = palSemaforo;
        this.palIzquierdo = palFilosofo[nFilosofo][0];
        this.palDerecho = palFilosofo[nFilosofo][1];
    }

    /**
     * Método que simula la lógica "comer" de los filósofos.
     * Si es posible obtener el palillo a la izquierda y derecha del filósofo
     * cambiará la imágen del estado del mismo en la interfaz, asignando 
     * un tiempo de espera y liberando los palillos una vez terminado el mismo.
     */
    protected void comer()
    {
        if (palSemaforo[palIzquierdo].tryAcquire()) {                           // El método tryAcquire adquiere un permiso del semáforo si es posible denegándolo en caso contrario
            if (palSemaforo[palDerecho].tryAcquire()) {                         // Si el filósofo puede de coger el palillo de su izquierda y de su derecha
                //System.out.println("Filósofo " + nFilosofo + " está comiendo.");
                Interfaz.setEstado(nFilosofo, "comiendo");                      // cambiamos la imágen de estado de ese filósofo concreto,
                
                Interfaz.setMano(palIzquierdo, "mano");                         // y la de los tenedores usados por el mismo,
                System.out.println("Filosofo " + nFilosofo + " tiene tenedor " + palIzquierdo);
                
                Interfaz.setMano(palDerecho, "mano");
                System.out.println("Filosofo " + nFilosofo + " tiene tenedor " + palDerecho);
                System.out.println("Filosofo " + nFilosofo + " comiendo");
                try {
                    sleep(new Random().nextInt(2000) + 1000);                   // asignándole un tiempo aleatorio que simula lo que tarda en comer (entre 1 y 2 segundos)
                } catch (InterruptedException ex) {
                    System.out.println("Error en método comer: "+ex.toString());
                }
                //System.out.println("Filósofo " + nFilosofo + " ha terminado de comer. Suelta los palillos " + palIzquierdo + " y " + palDerecho);
                Interfaz.setEstado(nFilosofo, "servido");                       // Volvemos a cambiar sus imágenes de estado y manos una vez que está servido.
                
                Interfaz.setMano(palIzquierdo, "empty");
                Interfaz.setMano(palDerecho, "empty");
                palSemaforo[palDerecho].release();                              // Puesto que ha terminado de comer liberámos el recurso (Palillo/Semáforo) de la derecha del filósofo.
            }
            palSemaforo[palIzquierdo].release();                                // Liberámos el recurso (Palillo/Semáforo) de la izquierda del filósofo.
        } else { 
            //System.out.println("Filósofo " + nFilosofo + " está esperando."); 
            Interfaz.setEstado(nFilosofo, "esperando");                         // Puesto que el filósofo no ha podido coger el palillo, cambiamos su imágen de estado correspondiente.
            System.out.println("Filosofo " + nFilosofo + " pensando");
        }
    }
    protected void pensar()
    {
        try
        {
            Filosofo.sleep(new Random().nextInt(2000) + 1000);                  // Simulamos el tiempo que tarda un filósofo en pensar (entre 1 y 2 segundos)
            Interfaz.setEstado(nFilosofo, "pensando");                          // Cambiamos la imágen correspondiente del filósofo
            System.out.println("Filosofo " + nFilosofo + " pensando");
            System.out.println("Filosofo " + nFilosofo + " hambriento");
        } catch (InterruptedException ex) {
            System.out.println("Error en método pensar: "+ex.toString());
        }
    }

    protected void Hambriento()
    {
        try {
            Filosofo.sleep(new Random().nextInt(2000) + 1000);                  // Simulamos el tiempo que tarda un filósofo en estar hambriento (entre 1 y 2 segundos)
            System.out.println("Filosofo " + nFilosofo + " hambriento");
        } catch (InterruptedException ex)
        {
            System.out.println("Error en método Hambriento: "+ex.toString());
        }
    }
    @Override
    public void run() {
        while (Interfaz.sw) {                                                   // Se realiza un bucle infinito mientras este habilitada la variable "sw"
            pensar();
            Hambriento();
            comer();
        }
    }
}