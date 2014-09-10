package Cannon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
//Contains all the buttons, sliders, and some game information, Located to the south in panel play
public class PnlPlayButtons extends JPanel {

    private double dMoney, dPowder, dWind, dGravity;
    private BufferedImage bufImgBg;
    private JLabel lblPowder = new JLabel("Gun Powder");
    private JLabel lblAngle = new JLabel("Angle");
    private JLabel lblStatPowder = new JLabel("Powder:");
    private JLabel lblStatWind = new JLabel("Wind:");
    private JLabel lblStatGravity = new JLabel("Gravity:");
    private JLabel lblStatMoney = new JLabel("Gravity:");
    JButton btnFire = new JButton("Fire!");
    JButton btnMenu = new JButton("Main Menu");
    JButton btnShop = new JButton("Shop");
    JPanel pnlPowder = new JPanel();
    JPanel pnlAngle = new JPanel();
    JPanel pnlStats = new JPanel();
    JPanel pnlBullets = new JPanel();
    JToolBar barBullets = new JToolBar();
    JSlider sliGunpowder = new JSlider(JSlider.HORIZONTAL, 0, 80, 40);
    JSlider sliAngle = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);

    PnlPlayButtons() {
        try {
            bufImgBg = ImageIO.read(new File("Images/border1.png"));
        } catch (IOException ex) {
            Logger.getLogger(PnlPlayButtons.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setOpaque(false);
        //this.setBackground(Color.black);
        Dimension dimSize = new Dimension();
        dimSize.setSize(900, 100);
        this.setPreferredSize(dimSize);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbcConstraints = new GridBagConstraints();
        //gbcConstraints.fill=gbcConstraints.HORIZONTAL;
        gbcConstraints.ipadx = 4;
        gbcConstraints.gridx = 0;
        gbcConstraints.gridy = 0;
        this.add(btnFire, gbcConstraints);
        sliGunpowder.setMajorTickSpacing(80);
        sliGunpowder.setMinorTickSpacing(0);
        sliGunpowder.setPaintTicks(true);
        sliGunpowder.setPaintLabels(true);
        sliGunpowder.setName("Gun Powder");
        sliGunpowder.setOpaque(false);
        pnlPowder.setLayout(new BorderLayout());
        pnlPowder.setOpaque(false);
        pnlPowder.add(sliGunpowder, BorderLayout.SOUTH);
        pnlPowder.add(lblPowder, BorderLayout.CENTER);
        gbcConstraints.gridx = 1;
        this.add(pnlPowder, gbcConstraints);
        sliAngle.setMajorTickSpacing(360);
        sliAngle.setMinorTickSpacing(10);
        sliAngle.setPaintTicks(true);
        sliAngle.setPaintLabels(true);
        sliAngle.setName("Angle");
        sliAngle.setOpaque(false);
        pnlAngle.setLayout(new BorderLayout());
        pnlAngle.setOpaque(false);
        pnlAngle.add(sliAngle, BorderLayout.SOUTH);
        pnlAngle.add(lblAngle, BorderLayout.CENTER);
        gbcConstraints.gridx = 2;
        this.add(pnlAngle, gbcConstraints);
        gbcConstraints.gridx = 3;
        this.add(btnMenu, gbcConstraints);
        gbcConstraints.gridx = 4;
        this.add(btnShop, gbcConstraints);
        pnlStats.setLayout(new GridLayout(4, 1));
        pnlStats.setOpaque(false);
        //pnlStats.setSize(200, 100);
        pnlStats.add(lblStatMoney);
        pnlStats.add(lblStatPowder);
        pnlStats.add(lblStatWind);
        pnlStats.add(lblStatGravity);
        gbcConstraints.gridx = 5;
        this.add(pnlStats, gbcConstraints);

        gbcConstraints.gridx = 6;
        add(pnlBullets, gbcConstraints);
        //barBullets.addSeparator();
        barBullets.setFloatable(false);
        gbcConstraints.gridx = 7;
        add(barBullets, gbcConstraints);

        //setJMenuBar(menuBar);
    }

    public void AddBulletButton(AbstractAction ActBullet) {
        barBullets.add(ActBullet);
    }

    public void setAngleSlider(int nSilderAngle) {
        sliAngle.setValue(nSilderAngle);
    }

    public void updateStats(double _dMoney, double _dPowder, double _dWind, double _dGravity) {
        dMoney = _dMoney;
        lblStatMoney.setText("Money: " + dMoney);
        dPowder = _dPowder;
        lblStatPowder.setText("Powder: " + dPowder);
        dWind = _dWind;
        lblStatWind.setText("Wind: " + (int) dWind);
        dGravity = _dGravity;
        lblStatGravity.setText("Gravity: " + dGravity);
    }

    public void paintComponent(Graphics g) {
        super.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufImgBg, null, 0, 0);
    }
}
