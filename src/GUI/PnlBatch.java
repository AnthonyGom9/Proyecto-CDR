package GUI;

import javax.swing.JPanel;

import enums.Colors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import CDR.CDRConsumer;
import CDR.CDRProducer;
import CDR.CDRProducerBatch;
import CDR.SharedBuffer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFileChooser;
import java.io.File;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PnlBatch extends JPanel {

	private JTextField txt_ruta = new JTextField(30);
	private JButton btn_Buscar = new JButton("Buscar Archivo");
	private JButton btn_Iniciar = new JButton("Iniciar");
	private JLabel lbl_progreso = new JLabel();

	public PnlBatch() {
		initComponents();
	}

	private void initComponents() {
		setBackground(Color.getColor(Colors.BLANCO.getColor().toString()));
		setLayout(new BorderLayout());
		initEncabezado();
	}

	private void initEncabezado() {
		JPanel pnl_encabezado = new JPanel();
		pnl_encabezado.setLayout(new BoxLayout(pnl_encabezado, BoxLayout.Y_AXIS));

		JLabel lbl_titulo = new JLabel("Escanear carpeta");
		lbl_titulo.setFont(new Font("Arial", Font.BOLD, 18));
		lbl_titulo.setHorizontalAlignment(SwingConstants.CENTER);
		pnl_encabezado.add(lbl_titulo);

		JPanel pnl_ruta = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lbl_ruta = new JLabel("Carpeta:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		pnl_ruta.add(lbl_ruta, gbc);

		txt_ruta = new JTextField(10);
		txt_ruta.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnl_ruta.add(txt_ruta, gbc);

		JButton btn_buscar = new JButton("Buscar");
		btn_buscar.addActionListener(e -> buscarArchivo());
		gbc.gridx = 2;
		gbc.weightx = 0;
		pnl_ruta.add(btn_buscar, gbc);

		pnl_encabezado.add(pnl_ruta);
		add(pnl_encabezado, BorderLayout.NORTH);
	}

	private void buscarArchivo() {
		JFileChooser seleccionador = new JFileChooser();
		seleccionador.setDialogTitle("Seleccione una carpeta");
		seleccionador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int seleccion = seleccionador.showOpenDialog(this);
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File carpetaSeleccionada = seleccionador.getSelectedFile();
			txt_ruta.setText(carpetaSeleccionada.getAbsolutePath());
		}
	}

	private void escanearCarpeta() {
		File carpeta = new File(txt_ruta.getText().toString());
		if (carpeta.isDirectory()) {
			File[] archivos = carpeta.listFiles((dir, nombre) -> nombre.toLowerCase().endsWith(".csv"));
			if (archivos != null && archivos.length > 0) {
				String nombre = "";
				ejecutarScanner(archivos);
				lbl_progreso.setText(nombre);
			} else {
				JOptionPane.showMessageDialog(this, "No se encontraron archivos CSV en la carpeta.", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "La ruta ingresada no es una carpeta", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void ejecutarScanner(File[] archivos) {
		SharedBuffer datos = new SharedBuffer(100);
		boolean finalizar = false;

		ExecutorService productorPool = Executors.newFixedThreadPool(3); 
		for (File archivo : archivos) {
			LocalDateTime now = LocalDateTime.now();
			String fecha_hora = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			CDRProducerBatch productor = new CDRProducerBatch(archivo, datos, fecha_hora);
			productorPool.submit(productor); 
		}
		productorPool.shutdown();

		ExecutorService consumidorPool = Executors.newFixedThreadPool(2);

		try {
			if (productorPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
				datos.setFinish(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		consumidorPool.shutdownNow();

		try {
			if (consumidorPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {

				System.out.println("Finalizado");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
