package enums;

public enum Colores {

    FONDO("#4E73DF"),
    FONDO_GRIS("#F8F9FD"),
    HOVER("#8FA6EB"),
    BLANCO("#FFFFFF"),
    NEGRO("#000000");

    private final String color;

    Colores(String color) {
        this.color = color;
    }

    // Método para obtener el string asociado
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color; // Para que imprima el nombre legible en lugar del nombre del enum
    }
}
