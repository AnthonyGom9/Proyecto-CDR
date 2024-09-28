
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KebFelipe
 */
public class ReporteModel {
    private List llamadas;
    private float total_duracion;
    private float total_tarifa;
    private String descripcion_tarifa;

    public ReporteModel() {
        this.total_duracion = 0;
        this.total_tarifa = 0;
        this.descripcion_tarifa = "";
        this.llamadas = new ArrayList();
    }
    
    public ReporteModel(float total_duracion, float total_tarifa, String descripcion_tarifa, List llamadas) {
        this.total_duracion = total_duracion;
        this.total_tarifa = total_tarifa;
        this.descripcion_tarifa = descripcion_tarifa;
        this.llamadas = llamadas;
    }

    public List getLlamadas() {
        return llamadas;
    }

    public void setLlamadas(List llamadas) {
        this.llamadas = llamadas;
    }

    public float getTotal_duracion() {
        return total_duracion;
    }

    public void setTotal_duracion(float total_duracion) {
        this.total_duracion = total_duracion;
    }

    public float getTotal_tarifa() {
        return total_tarifa;
    }

    public void setTotal_tarifa(float total_tarifa) {
        this.total_tarifa = total_tarifa;
    }

    public String getDescripcion_tarifa() {
        return descripcion_tarifa;
    }

    public void setDescripcion_tarifa(String descripcion_tarifa) {
        this.descripcion_tarifa = descripcion_tarifa;
    }
    
 
}
