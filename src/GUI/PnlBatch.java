package GUI;

import javax.swing.JPanel;

import enums.Colors;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CDR.CDRConsumer;
import CDR.CDRProducer;
import CDR.SharedBuffer;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JFileChooser;
import java.io.File;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PnlBatch extends JPanel {

    private JTextField txt_buscar = new JTextField(30);
    private JButton btn_Buscar = new JButton("Buscar Archivo");
    private JButton btn_Iniciar = new JButton("Iniciar");
    private JLabel lbl_progreso = new JLabel();

    

    public PnlBatch() {
        this.initComponents();
    }

    private void initComponents() {
        setBackground(Color.getColor(Colors.FONDO_GRIS.getColor().toString()));
        setLayout(new FlowLayout());

        btn_Buscar.addActionListener(e -> buscarArchivo());
        btn_Iniciar.addActionListener(e -> escanearCarpeta());

        add(txt_buscar);
        add(btn_Buscar);
        add(btn_Iniciar);
        add(lbl_progreso);
    }

    private void buscarArchivo() {
        JFileChooser seleccionador = new JFileChooser();
        seleccionador.setDialogTitle("Seleccione una carpeta");
        seleccionador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int seleccion = seleccionador.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File carpetaSeleccionada = seleccionador.getSelectedFile();
            txt_buscar.setText(carpetaSeleccionada.getAbsolutePath());
        }
    }

    private void escanearCarpeta() {
         File carpeta = new File(txt_buscar.getText().toString());
         if (carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles((dir, nombre) -> nombre.toLowerCase().endsWith(".csv"));
            if (archivos != null && archivos.length > 0) {
                String nombre = "";
                ejecutarScanner(archivos);
                lbl_progreso.setText(nombre);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron archivos CSV en la carpeta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
         }else{
            JOptionPane.showMessageDialog(this, "La ruta ingresada no es una carpeta", "Error", JOptionPane.ERROR_MESSAGE);
         }
    }

    private void ejecutarScanner(File[] archivos) {
        SharedBuffer datos = new SharedBuffer(100);
        boolean finalizar = false;

        ExecutorService productorPool = Executors.newFixedThreadPool(3); // Crear un pool de 3 hilos para productores
        for (File archivo : archivos) {
            LocalDateTime now = LocalDateTime.now();
            String fecha_hora = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            CDRProducer productor = new CDRProducer(archivo, datos, fecha_hora);
            productorPool.submit(productor); // Enviar el productor al pool
        }
        productorPool.shutdown();

        ExecutorService consumidorPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            CDRConsumer consumidor = new CDRConsumer(datos);
            consumidorPool.submit(consumidor); // Enviar el consumidor al pool
        }
        
        try {
            if(productorPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
                datos.setFinish(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumidorPool.shutdownNow(); 
        
        try {
            if(consumidorPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
                
                System.out.println("Finalizado");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
