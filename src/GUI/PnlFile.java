package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import CDR.CDRConsumer;
import CDR.CDRProducer;
import CDR.SharedBuffer;
import enums.Colors;
import utils.DateConvert;

/**
 *
 * @author KebFelipe
 */
public class PnlFile extends JPanel {

	private JTextField txt_ruta;

	private JTextField txt_buffer;

	private JTextField txt_hilo_consumidor;
	private JTextField txt_hilo_productor;

	private JTextArea txt_consumidor_resultado;
	private JTextArea txt_productor_resultado;

	public PnlFile() {
		initComponents();
	}

	private void initComponents() {
		setBackground(Color.getColor(Colors.BLANCO.getColor().toString()));
		setLayout(new BorderLayout());
		initEncabezado();
		initBody();
	}

	private void initEncabezado() {
		JPanel pnl_encabezado = new JPanel();
		pnl_encabezado.setLayout(new BoxLayout(pnl_encabezado, BoxLayout.Y_AXIS));

		JLabel lbl_titulo = new JLabel("Escanear archivo");
		lbl_titulo.setFont(new Font("Arial", Font.BOLD, 18));
		lbl_titulo.setHorizontalAlignment(SwingConstants.CENTER);
		pnl_encabezado.add(lbl_titulo);

		JPanel pnl_ruta = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lbl_cuenta = new JLabel("Archivo:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		pnl_ruta.add(lbl_cuenta, gbc);

		txt_ruta = new JTextField(10);
		txt_ruta.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnl_ruta.add(txt_ruta, gbc);

		JButton btn_buscar = new JButton("Buscar");
		btn_buscar.addActionListener(e -> obtenerRutaArchivo());
		gbc.gridx = 2;
		gbc.weightx = 0;
		pnl_ruta.add(btn_buscar, gbc);

		pnl_encabezado.add(pnl_ruta);
		add(pnl_encabezado, BorderLayout.NORTH);
	}

	private void initBody() {
		JPanel pnl_body = new JPanel();
		pnl_body.setLayout(new BoxLayout(pnl_body, BoxLayout.Y_AXIS));

		pnl_body.add(Box.createRigidArea(new Dimension(0, 20)));

		JLabel lbl_buffer = new JLabel("Tamaño máximo del Buffer");
		pnl_body.add(lbl_buffer);

		pnl_body.add(Box.createRigidArea(new Dimension(0, 5)));

		JPanel pnl_buffer = new JPanel();
		pnl_buffer.setLayout(new BoxLayout(pnl_buffer, BoxLayout.X_AXIS));
		pnl_buffer.add(Box.createHorizontalGlue());

		txt_buffer = new JTextField(10);
		txt_buffer.setText("200");
		txt_buffer.setMaximumSize(new Dimension(300, 30));
		txt_buffer.setHorizontalAlignment(SwingConstants.CENTER);
		pnl_buffer.add(txt_buffer);

		pnl_buffer.add(Box.createHorizontalGlue());
		pnl_body.add(txt_buffer);

		pnl_body.add(Box.createRigidArea(new Dimension(0, 20)));

		// ---------- Agregar informacion del consumidor - productor
		// -------consumidor
		JPanel pnl_prod_consumidor = new JPanel();
		pnl_prod_consumidor.setLayout(new BoxLayout(pnl_prod_consumidor, BoxLayout.X_AXIS));

		JPanel pnl_consumidor = new JPanel();
		pnl_consumidor.setLayout(new BoxLayout(pnl_consumidor, BoxLayout.Y_AXIS));

		JLabel lbl_consumidor = new JLabel("Hilo Consumidor");
		pnl_consumidor.add(lbl_consumidor);

		txt_hilo_consumidor = new JTextField(10);
		txt_hilo_consumidor.setText("3");
		txt_hilo_consumidor.setHorizontalAlignment(SwingConstants.CENTER);
		txt_hilo_consumidor.setMaximumSize(new Dimension(200, 30));
		pnl_consumidor.add(txt_hilo_consumidor);

		pnl_prod_consumidor.add(Box.createHorizontalGlue());
		pnl_prod_consumidor.add(pnl_consumidor);
		pnl_prod_consumidor.add(Box.createHorizontalGlue());

		// -------productor
		JPanel pnl_productor = new JPanel();
		pnl_productor.setLayout(new BoxLayout(pnl_productor, BoxLayout.Y_AXIS));

		JLabel lbl_productor = new JLabel("Hilo Productor");
		pnl_productor.add(lbl_productor);

		txt_hilo_productor = new JTextField(10);
		txt_hilo_productor.setText("2");
		txt_hilo_productor.setHorizontalAlignment(SwingConstants.CENTER);
		txt_hilo_productor.setMaximumSize(new Dimension(200, 30));
		pnl_productor.add(txt_hilo_productor);

		pnl_prod_consumidor.add(pnl_productor);
		pnl_prod_consumidor.add(Box.createHorizontalGlue());

		pnl_body.add(pnl_prod_consumidor);

		pnl_body.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel pnl_inicio = new JPanel();
		pnl_inicio.setLayout(new BoxLayout(pnl_inicio, BoxLayout.X_AXIS));
		pnl_inicio.add(Box.createHorizontalGlue());

		JButton btn_inicio = new JButton("Iniciar Escaneo");
		btn_inicio.setHorizontalAlignment(SwingConstants.CENTER);
		btn_inicio.addActionListener(e -> inicarEscaneo());
		pnl_inicio.add(btn_inicio);

		pnl_inicio.add(Box.createHorizontalGlue());
		pnl_body.add(pnl_inicio);
		pnl_body.add(Box.createRigidArea(new Dimension(0, 10)));

		// ---------- Agregar informacion del consumidor - productor
		// -------consumidor
		JPanel pnl_prod_consumidor_resultado = new JPanel();
		pnl_prod_consumidor_resultado.setLayout(new BoxLayout(pnl_prod_consumidor_resultado, BoxLayout.X_AXIS));

		pnl_prod_consumidor_resultado.add(Box.createRigidArea(new Dimension(20, 10)));
		
		JPanel pnl_consumidor_resultado = new JPanel();
		pnl_consumidor_resultado.setLayout(new BoxLayout(pnl_consumidor_resultado, BoxLayout.Y_AXIS));

		txt_consumidor_resultado = new JTextArea();
		txt_consumidor_resultado.setEditable(false); // Hacerlo no editable
		txt_consumidor_resultado.setLineWrap(true); // Ajustar líneas
		txt_consumidor_resultado.setWrapStyleWord(true); // Ajustar en palabras
		txt_consumidor_resultado.setBackground(Color.BLACK);
		txt_consumidor_resultado.setForeground(Color.GREEN);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		txt_consumidor_resultado.setBorder(border);
		pnl_consumidor_resultado.add(txt_consumidor_resultado);

		pnl_prod_consumidor_resultado.add(pnl_consumidor_resultado);

		pnl_prod_consumidor_resultado.add(Box.createRigidArea(new Dimension(20, 10)));
		
		// -------productor
		JPanel pnl_productor_resultado = new JPanel();
		pnl_productor_resultado.setLayout(new BoxLayout(pnl_productor_resultado, BoxLayout.Y_AXIS));

		
		txt_productor_resultado = new JTextArea();
		txt_productor_resultado.setEditable(false); // Hacerlo no editable
		txt_productor_resultado.setLineWrap(true); // Ajustar líneas
		txt_productor_resultado.setWrapStyleWord(true); // Ajustar en palabras
		txt_productor_resultado.setBorder(border);
		txt_productor_resultado.setBackground(Color.BLACK);
		txt_productor_resultado.setForeground(Color.GREEN);
		pnl_productor_resultado.add(txt_productor_resultado);

		pnl_prod_consumidor_resultado.add(pnl_productor_resultado);
		
		pnl_prod_consumidor_resultado.add(Box.createRigidArea(new Dimension(20, 10)));
	
		pnl_prod_consumidor_resultado.add(Box.createHorizontalGlue());
		
		pnl_body.add(pnl_prod_consumidor_resultado);
		pnl_body.add(Box.createRigidArea(new Dimension(0, 40)));

		add(pnl_body, BorderLayout.CENTER);
	}

	private void obtenerRutaArchivo() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Seleccionar archivo CSV");
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
		int userSelection = fileChooser.showOpenDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			txt_ruta.setText(fileToOpen.getAbsolutePath());
		}
	}

	private void inicarEscaneo() {

		String ruta = txt_ruta.getText();
		if (ruta.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un archivo CSV", "Error", JOptionPane.ERROR_MESSAGE);
		} else {

			int buffer_max = Integer.parseInt(txt_buffer.getText());
			SharedBuffer datos = new SharedBuffer(buffer_max);
			boolean finalizar = false;

			int numero_max_productor = Integer.parseInt(txt_hilo_productor.getText());
			ExecutorService productorPool = Executors.newFixedThreadPool(numero_max_productor);
			for (int i = 0; i < numero_max_productor; i++) {
				
				CDRProducer productor = new CDRProducer(new File(txt_ruta.getText().toString()), datos, DateConvert.ObtenerHoraActual(),
						txt_productor_resultado, i + 1);
				productorPool.submit(productor);
			}
			productorPool.shutdown();

			int numero_max_consumidor = Integer.parseInt(txt_hilo_consumidor.getText());
			ExecutorService consumidorPool = Executors.newFixedThreadPool(numero_max_consumidor);
			for (int i = 0; i < numero_max_consumidor; i++) {
				CDRConsumer consumidor = new CDRConsumer(datos, txt_consumidor_resultado, i + 1);
				consumidorPool.submit(consumidor);
			}

			try {
				if (productorPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
					datos.setFinish(true);
					CDRProducer.currentIndex = 0;
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

}
