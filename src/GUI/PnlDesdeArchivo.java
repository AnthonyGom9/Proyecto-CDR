package GUI;

import javax.swing.JPanel;

import enums.Colores;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JFileChooser;
import java.io.File;

public class PnlDesdeArchivo extends JPanel {

    private JTextField txt_buscar = new JTextField(30);
    private JButton buscarButton = new JButton("Buscar Archivo");

    public PnlDesdeArchivo() {
        this.initComponents();
    }

    private void initComponents() {
        setBackground(Color.getColor(Colores.FONDO_GRIS.getColor().toString()));

        setLayout(new FlowLayout());
       
        buscarButton.addActionListener(e -> buscarArchivo());

        add(new JLabel("Panel 1"));
        add(txt_buscar);
        add(buscarButton);
    }

    private void buscarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));

        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            File archivoSeleccionado = fileChooser.getSelectedFile();
            // Actualizar la barra de texto con la ruta del archivo
            txt_buscar.setText(archivoSeleccionado.getAbsolutePath());
        }
    }
}