package CDR;

import java.time.LocalDateTime;

import javax.swing.JTextArea;

import controller.LlamadaController;
import utils.DateConvert;

/**
 *
 * @author KebFelipe
 */
public class CDRConsumer implements Runnable {

    private final SharedBuffer buffer;
    private JTextArea txt_area;
    private int hilo;
    private int contador;

    public CDRConsumer(SharedBuffer sharedBuffer, JTextArea txt_consumidor_resultado, int hilo) {
        this.buffer = sharedBuffer;
        this.txt_area = txt_consumidor_resultado;
        this.hilo = hilo;
    }

    @Override
    public void run() {
    	txt_area.append("\n Inicio Hilo " + hilo +": " + DateConvert.ObtenerHoraActual() + "");
        while (true) {
            if (buffer.isEmpty() && buffer.isFinish()) {
            	finalizar();
                break;
            }
            String linea = buffer.eliminar();
            if (linea != null) {
                String[] datos = linea.split(",");
                LlamadaController controlador = new LlamadaController();
                if (datos.length >= 9) {
                    try {
                        String fecha_archivo = DateConvert.ConvetirFormatoSQL(datos[8]);
                        int cuenta = Integer.parseInt(datos[0]);
                        float duracion = Float.parseFloat(datos[4]);
                        float tarifa = Float.parseFloat(datos[5]);
                        String fecha_llamada = DateConvert.ConvetirFormatoSQL(datos[3]);
                        controlador.insertarLlamada(datos[7], datos[9],fecha_archivo, cuenta, datos[1], datos[2], fecha_llamada, duracion, tarifa, datos[6]);
                        contador++;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: " + e);
                        Thread.currentThread().interrupt();
                        finalizar();
                    }
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    finalizar();
                }
            }
        }
        
    }
    
    private void finalizar() {
    	txt_area.append("\n Finaliza Hilo " + hilo +": " + DateConvert.ObtenerHoraActual() + "");
		txt_area.append("\n Hilo " + hilo +": " + contador + " registros procesados ");
    }
}
