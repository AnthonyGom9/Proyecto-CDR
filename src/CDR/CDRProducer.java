package CDR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author KebFelipe
 */
public class CDRProducer implements Runnable {
    private final File path;
    private final SharedBuffer sharedBuffer;
    private final String fecha_hora;

    public CDRProducer(File path, SharedBuffer sharedBuffer, String fecha_hora) {
        this.path = path;
        this.sharedBuffer = sharedBuffer;
        this.fecha_hora = fecha_hora;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line += "," + path.getName() + "," + fecha_hora + "," + path.getAbsolutePath();
                sharedBuffer.agregar(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
