package Cannon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
//Panel that contains all the main menu buttons seen in FraWindow (Play, Settings ect)
public class PnlMainMenu extends JPanel {

    JButton btnPlay = new JButton("Play");
    JButton btnHighScores = new JButton("High Scores");
    JButton btnHelp = new JButton("Help");
    JButton btnSettings = new JButton("Settings");
    JButton btnExit = new JButton("Exit");
    BufferedImage bufImgBg;

    PnlMainMenu() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 3;
        c.ipady = 3;
        this.add(btnPlay, c);
        c.gridy = 1;
        this.add(btnHighScores, c);
        c.gridy = 2;
        this.add(btnHelp, c);
        c.gridy = 3;
        this.add(btnSettings, c);
        c.gridy = 4;
        this.add(btnExit, c);
        try {
            bufImgBg = ImageIO.read(new File("Images/MainMenuBG.png"));
        } catch (IOException ex) {
            Logger.getLogger(PnlMainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void paintComponent(Graphics g) {
        super.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufImgBg, null, 0, 0);


    }
}

