package Cannon;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
//Help Panel, currently empty
public class PnlHelp extends JPanel {

    JLabel lblRTFM = new JLabel("RTFM");
    JButton btnBack = new JButton("Back");
    BufferedImage bufImgBg;

    PnlHelp() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        lblRTFM.setHorizontalAlignment(SwingConstants.CENTER );
        this.add(lblRTFM, c);
        c.gridy = 1;
        this.add(btnBack, c);
        this.setSize(900, 600);
        try {
            bufImgBg = ImageIO.read(new File("Images/HighScoresBG.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufImgBg, null, 0, 0);
    }
}
