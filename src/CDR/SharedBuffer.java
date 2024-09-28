package CDR;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 *
 * @author KebFelipe
 */
public class SharedBuffer {
    private final ConcurrentLinkedQueue<String> buffer = new ConcurrentLinkedQueue<>();
    private final int limite;
    private boolean finalizar;

    public SharedBuffer(int limite) {
        this.limite = limite;
        this.finalizar = false;
    }

    public synchronized void agregar(String linea) {
        while (buffer.size() >= limite) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("error: " + e);
                Thread.currentThread().interrupt();
            }
        }
        buffer.add(linea);
        notifyAll(); 
    }

    public synchronized String eliminar() {
        String line = buffer.poll();
        notifyAll();
        return line;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }
    
    public void setFinish(boolean finalizar){
        this.finalizar = finalizar;
    }
    
    public boolean isFinish(){
        return this.finalizar;
    }
}
