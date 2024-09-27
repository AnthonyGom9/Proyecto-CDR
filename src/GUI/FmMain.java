package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import enums.Colors;
import utils.IconButton;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Insets;

public class FmMain extends JFrame {
    private JPanel pnl_menu;
    private JPanel pnl_view;
    private CardLayout cardLayout;

    public FmMain() {
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("CDR");
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);

        pnl_menu = new JPanel();
        pnl_menu.setLayout(new GridLayout(15, 1));
        pnl_menu.setBackground(Color.decode("#4E73DF")); // Hacer el panel transparente

        JLabel lbl_titulo = new JLabel("CDR");
        lbl_titulo.setForeground(Color.WHITE);
        lbl_titulo.setHorizontalAlignment(JLabel.CENTER);
        lbl_titulo.setVerticalAlignment(JLabel.CENTER);
        lbl_titulo.setFont(new Font("Arial", Font.BOLD, 30));

        JButton btn_Home = new JButton("Inicio");
        configurarBoton(btn_Home, "ic_home.svg");

        JButton btn_DesdeArchivo = new JButton("Escanear");
        configurarBoton(btn_DesdeArchivo, "ic_file.svg");

        JButton btn_Salir = new JButton("Salir");
        configurarBoton(btn_Salir, "ic_power.svg");

        pnl_menu.add(lbl_titulo);
        pnl_menu.add(btn_Home);
        pnl_menu.add(btn_DesdeArchivo);
        pnl_menu.add(btn_Salir);

        pnl_view = new JPanel();
        cardLayout = new CardLayout();
        pnl_view.setLayout(cardLayout);

        // Añadir los paneles a pnl_view con identificadores
        pnl_view.add(new PnlMain(), "1");
        pnl_view.add(new PnlScan(), "2");

        // Acciones de los botones para cambiar de panel
        btn_Home.addActionListener(e -> cardLayout.show(pnl_view, "1"));
        btn_DesdeArchivo.addActionListener(e -> cardLayout.show(pnl_view, "2"));
        btn_Salir.addActionListener(e -> System.exit(0));

        setLayout(new BorderLayout());
        add(pnl_menu, BorderLayout.WEST); // pnl_menu en la parte superior
        add(pnl_view, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton btn, String icon) {
        btn.setMargin(new Insets(10, 20, 10, 20));
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.setBorderPainted(false); // Eliminar los bordes del botón
        btn.setHorizontalAlignment(JButton.LEFT); // Alinear el texto a la izquierda
        btn.setBackground(Color.decode(Colors.FONDO.getColor().toString())); // Color de fondo del botón
        btn.setForeground(Color.WHITE);
        try {
            btn.setIcon(new ImageIcon(new IconButton().showIcon(icon, Colors.FONDO.getColor().toString(),
                    Colors.BLANCO.getColor().toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(Color.decode(Colors.FONDO_HOVER.getColor().toString()));
                btn.setForeground(Color.BLACK); // Cambia el color del texto en hover
                try {
                    btn.setIcon(new ImageIcon(new IconButton().showIcon(icon, Colors.FONDO_HOVER.getColor().toString(),
                            Colors.NEGRO.getColor().toString())));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.decode(Colors.FONDO.getColor().toString()));
                btn.setForeground(Color.WHITE); // Vuelve al color original cuando sale del hover
                try {
                    btn.setIcon(new ImageIcon(new IconButton().showIcon(icon, Colors.FONDO.getColor().toString(),
                            Colors.BLANCO.getColor().toString())));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}
