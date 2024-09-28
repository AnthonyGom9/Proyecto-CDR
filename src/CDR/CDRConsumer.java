package CDR;

import controller.LlamadaController;
import utils.DateConvert;

/**
 *
 * @author KebFelipe
 */
public class CDRConsumer implements Runnable {

    private final SharedBuffer buffer;

    public CDRConsumer(SharedBuffer sharedBuffer) {
        this.buffer = sharedBuffer;
    }

    @Override
    public void run() {
        while (true) {
            if (buffer.isEmpty() && buffer.isFinish()) {
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
                    } catch (NumberFormatException e) {
                        System.out.println("Error: " + e);
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
