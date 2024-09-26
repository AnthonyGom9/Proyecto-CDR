package utils;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import java.net.URI;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
/**
 *
 * @author KebFelipe
 */
public class IconButton {
    private SVGUniverse svgUniverse = new SVGUniverse();

    public IconButton() {

    }

    public Image showIcon(String icon, String color_fondo_hex, String color_relleno_hex) throws SVGException {
        URI svgUri = svgUniverse.loadSVG(getClass().getResource("/assets/" + icon)); // Reemplaza con la ruta de tu SVG
        SVGDiagram svgDiagram = svgUniverse.getDiagram(svgUri);

        svgDiagram.setIgnoringClipHeuristic(true);
        svgDiagram.updateTime(0);

        int iconSize = 25; // Cambia el tamaño a 60px
        BufferedImage img = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        // Establecer el color para el SVG
        g.clearRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(Color.decode(color_fondo_hex)); // Establecer color de fondo
        g.fillRect(0, 0, img.getWidth(), img.getHeight()); // Dibujar el fondo

        // Redimensionar el SVG
        g.scale((double) iconSize / svgDiagram.getWidth(), (double) iconSize / svgDiagram.getHeight());
        svgDiagram.render(g);

        g.setComposite(AlphaComposite.SrcAtop); // Aplica el color solo a lo visible
        g.setColor(Color.decode(color_relleno_hex)); // Color del dibujo
        g.fillRect(0, 0, img.getWidth(), img.getHeight()); 

        g.dispose();// Limpia el contexto gráfico

        return img;
    }
}
