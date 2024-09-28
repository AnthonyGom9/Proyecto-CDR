package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
/**
 *
 * @author KebFelipe
 */
public class DateConvert {

    public static String ConvetirFormatoSQL(String fechaOriginal) {
        try {
            // Definir los formatos de entrada y salida
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Convertir la cadena a un objeto Date
            Date fecha = formatoEntrada.parse(fechaOriginal);

            // Convertir la fecha al nuevo formato
            String fechaConvertida = formatoSalida.format(fecha);

            // Imprimir la fecha convertida
            return fechaConvertida;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
