package utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
/**
 *
 * @author KebFelipe
 */
public class DateConvert {

	
    public static String ConvetirFormatoSQL(String fechaOriginal) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha = formatoEntrada.parse(fechaOriginal);
            String fechaConvertida = formatoSalida.format(fecha);
            return fechaConvertida;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static String ObtenerHoraActual() {
    	LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
