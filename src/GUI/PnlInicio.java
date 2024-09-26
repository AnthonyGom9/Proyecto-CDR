package GUI;

import javax.swing.JPanel;

import enums.Colores;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class PnlInicio extends JPanel {

    public PnlInicio() {
        this.initComponents();
    }

    private void initComponents() {
        setBackground(Color.getColor(Colores.FONDO_GRIS.getColor().toString()));

        
    }
}