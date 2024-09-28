package CDR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JTextArea;

import utils.DateConvert;

/**
 *
 * @author KebFelipe
 */
public class CDRProducer implements Runnable {
	private final File path;
	private final SharedBuffer sharedBuffer;
	private final String fecha_hora;
	private JTextArea txt_area;
	private int contador;
	private int hilo;

	public static int currentIndex = 0;
	private static String[] lines;
	private static final Lock lock = new ReentrantLock();
	

	public CDRProducer(File path, SharedBuffer sharedBuffer, String fecha_hora, JTextArea txt_area, int hilo) {
		this.path = path;
		this.sharedBuffer = sharedBuffer;
		this.fecha_hora = fecha_hora;
		this.txt_area = txt_area;
		this.hilo = hilo;
		if (lines == null) {
			loadLines();
		}
	}

	private void loadLines() {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			lines = reader.lines().toArray(String[]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getNextLine() {
		lock.lock();
		try {
			if (currentIndex < lines.length) {
				return lines[currentIndex++];
			} else {
				return null;
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		txt_area.append("\n Inicio Hilo " + hilo +": " + DateConvert.ObtenerHoraActual() + "");
		while (true) {
			String line = getNextLine(); 
			if (line == null) {
				finalizar();
				break;
			}
			line += "," + path.getName() + "," + fecha_hora + "," + path.getAbsolutePath();
			sharedBuffer.agregar(line);
			contador++;
		}
	}
	
	private void finalizar() {
		txt_area.append("\n Finaliza Hilo " + hilo +": " + DateConvert.ObtenerHoraActual() + "");
		txt_area.append("\n Hilo " + hilo +": " + contador + " registros procesados ");
    }
}
