package GUI;

import controller.LlamadaController;
import enums.Colors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.LlamadaModel;
import models.ReporteModel;

/**
 *
 * @author KebFelipe
 */
public class PnlReporte extends JPanel {

    private JTextField txt_cuenta;
    private JTable tbl_registro;
    private DefaultTableModel tbl_model;
    private JLabel lbl_total_datos;
    private JLabel lbl_duracion;
    private JLabel lbl_tarifa;

    public PnlReporte() {
        this.initComponents();
    }

    private void initComponents() {
    	setBackground(Color.getColor(Colors.BLANCO.getColor().toString()));
        setLayout(new BorderLayout());
        initEncabezado();
        initTabla();
        initResultado();
    }
    
    private void initEncabezado(){
        JPanel pnl_encabezado = new JPanel();
        pnl_encabezado.setLayout(new BoxLayout(pnl_encabezado, BoxLayout.Y_AXIS));
        
        JLabel lbl_titulo = new JLabel("Reporte Consumo por cuenta");
        lbl_titulo.setFont(new Font("Arial", Font.BOLD, 18));
        lbl_titulo.setHorizontalAlignment(SwingConstants.CENTER);
        pnl_encabezado.add(lbl_titulo);
        
        JPanel pnl_filtro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel lbl_cuenta = new JLabel("Ingrese un numero de Cuenta:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        pnl_filtro.add(lbl_cuenta, gbc);
        
        txt_cuenta = new JTextField(10);
        txt_cuenta.setPreferredSize(new Dimension(150,30));
        txt_cuenta.setHorizontalAlignment(SwingConstants.RIGHT);
        txt_cuenta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnl_filtro.add(txt_cuenta, gbc);
        
        JButton btn_buscar = new JButton("Buscar");
        btn_buscar.addActionListener(e -> buscarDatos());
        gbc.gridx = 2;
        gbc.weightx = 0;
        pnl_filtro.add(btn_buscar, gbc);
        
        pnl_encabezado.add(pnl_filtro);
        add(pnl_encabezado, BorderLayout.NORTH);
    }
    
    private void initTabla(){
        String[] columns = {"No.", "Cuenta", "Emisor", "Receptor", "Fecha", "Duracion", "Tarifa", "Categoria", "Archivo"};
        this.tbl_model = new DefaultTableModel(columns, 0);
        this.tbl_registro = new JTable(tbl_model);
        JScrollPane scl_tabla = new JScrollPane(tbl_registro);
        scl_tabla.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scl_tabla.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scl_tabla, BorderLayout.CENTER);
    }
    
    private void initResultado(){
        JPanel pnl_piepagina = new JPanel();
        pnl_piepagina.setLayout(new BoxLayout(pnl_piepagina, BoxLayout.X_AXIS));
        
        pnl_piepagina.add(Box.createHorizontalGlue());
        
        this.lbl_total_datos = new JLabel("Datos:");
        lbl_total_datos.setFont(new Font("Arial", Font.BOLD, 16));
        pnl_piepagina.add(this.lbl_total_datos);
        
         pnl_piepagina.add(Box.createRigidArea(new Dimension(20,0)));
         
        this.lbl_duracion = new JLabel("Duracion Total: 0 Minutos");
        lbl_duracion.setFont(new Font("Arial", Font.BOLD, 16));
        pnl_piepagina.add(this.lbl_duracion);
        
        pnl_piepagina.add(Box.createRigidArea(new Dimension(20,0)));
        
        this.lbl_tarifa = new JLabel("Tarifa Total: 0");
        lbl_tarifa.setFont(new Font("Arial", Font.BOLD, 16));
        pnl_piepagina.add(this.lbl_tarifa);
        
        pnl_piepagina.add(Box.createRigidArea(new Dimension(20,0)));
        
        add(pnl_piepagina, BorderLayout.SOUTH);
    }
    
    private void buscarDatos(){
        ReporteModel reporte = new LlamadaController().getLlamadaCuenta(txt_cuenta.getText().toString());
        lbl_total_datos.setText("Datos: " + reporte.getLlamadas().size());
        lbl_tarifa.setText(reporte.getDescripcion_tarifa());
        lbl_duracion.setText("Duracion Total: " + reporte.getTotal_duracion());
        tbl_model.setRowCount(0);
        for(int x = 0; x < reporte.getLlamadas().size(); x++){
            LlamadaModel llamada = (LlamadaModel) reporte.getLlamadas().get(x);
            this.tbl_model.addRow(new Object[]{
                x+1,
                llamada.getCuenta(),
                llamada.getEmisor(),
                llamada.getReceptor(),
                llamada.getFecha(),
                llamada.getDuracion(),
                llamada.getTarifa(),
                llamada.getCategoria(),
                llamada.getArchivo()
            });
        }
    }
}
