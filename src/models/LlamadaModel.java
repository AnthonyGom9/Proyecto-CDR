package models;

/**
 *
 * @author KebFelipe
 */
public class LlamadaModel {
    
    private int cuenta;
    private String emisor;
    private String receptor;
    private String fecha;
    private float duracion;
    private float tarifa;
    private String categoria;
    private String archivo;

    public LlamadaModel(int cuenta, String emisor, String receptor, String fecha, float duracion, float tarifa, String categoria, String archivo) {
        this.cuenta = cuenta;
        this.emisor = emisor;
        this.receptor = receptor;
        this.fecha = fecha;
        this.duracion = duracion;
        this.tarifa = tarifa;
        this.categoria = categoria;
        this.archivo = archivo;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public float getTarifa() {
        return tarifa;
    }

    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
    
    
}
