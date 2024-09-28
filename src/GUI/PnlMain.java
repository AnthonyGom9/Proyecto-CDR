package GUI;

import javax.swing.JPanel;

import enums.Colors;

import java.awt.Color;

public class PnlMain extends JPanel {

    public PnlMain() {
        this.initComponents();
    }

    private void initComponents() {
        setBackground(Color.getColor(Colors.FONDO_GRIS.getColor().toString()));
    }
}