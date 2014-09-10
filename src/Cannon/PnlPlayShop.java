package Cannon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
//Shop panel of panel play. Allows buying powder and upgrades
public class PnlPlayShop extends JPanel {

    PnlShopBuy pnlShopBuy = new PnlShopBuy();
    PnlShopReturn pnlShopReturn = new PnlShopReturn();
    BufferedImage bufImgBg;
    private int nPowder = 1000,  nMoney = 100000,  nGoombaPrice = 100,  nSpikePrice = 200,  nStarPrice = 1000;

    PnlPlayShop() {

        this.setSize(900, 600);
        this.setLayout(new BorderLayout());
        this.add(pnlShopBuy, BorderLayout.CENTER);
        this.add(pnlShopReturn, BorderLayout.SOUTH);
        pnlShopBuy.btnBuyPowder.addActionListener(new ActBuyPowder());
        pnlShopBuy.lblMoney.setText("You have $" + nMoney + ".");
        pnlShopBuy.lblPowder.setText("You have " + nPowder + " powder.");
        pnlShopBuy.btnBuyGoomba.setText("Unlock the Goomba $" + nGoombaPrice);
        pnlShopBuy.btnBuySpike.setText("Unlock Spike $" + nSpikePrice);
        pnlShopBuy.btnBuyStar.setText("Unlock the Super Star of Death $" + nStarPrice + " (no relation to the death star)");
        try {
            bufImgBg = ImageIO.read(new File("Images/SettingsBG.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class PnlShopBuy extends JPanel {

        JLabel lblTitle = new JLabel("Welcome to the shop!");
        JLabel lblMoney = new JLabel();
        JLabel lblPowder = new JLabel();
        JLabel lblResponse = new JLabel("You haven't bought anything yet.");
        JButton btnBuyPowder = new JButton("Buy 100g of Gun Powder");
        JButton btnBuyGoomba = new JButton();
        JButton btnBuySpike = new JButton();
        JButton btnBuyStar = new JButton();

        PnlShopBuy() {
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            lblTitle.setForeground(Color.WHITE);
            this.add(lblTitle, c);
            c.gridy = 1;
            lblMoney.setForeground(Color.WHITE);
            this.add(lblMoney, c);
            c.gridy = 2;
            lblPowder.setForeground(Color.WHITE);
            this.add(lblPowder, c);
            c.gridy = 3;
            this.add(btnBuyPowder, c);
            c.gridy = 4;
            lblResponse.setForeground(Color.WHITE);
            this.add(lblResponse, c);
            c.gridy = 5;
            this.add(btnBuyGoomba, c);
            c.gridy = 6;
            this.add(btnBuySpike, c);
            c.gridy = 7;
            this.add(btnBuyStar, c);
            this.setOpaque(false);

        }
    }

    class PnlShopReturn extends JPanel {

        JButton btnMainMenu = new JButton("Main Menu");
        JButton btnBack = new JButton("Back");

        PnlShopReturn() {
            this.add(btnBack);
            this.add(btnMainMenu);
            this.setOpaque(false);
        }
    }

    public class ActBuyPowder implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (nMoney >= 100) {
                addPowder(100);
                removeMoney(100);
                setResponse("Powder Purchased Successfully");
            } else {
                setResponse("Not Enough Money");
            }
        }
    }

    public void addPowder(int _nAddPowder) {
        nPowder += _nAddPowder;
        pnlShopBuy.lblPowder.setText("You have " + nPowder + " powder.");
    }

    public void addMoney(int _nAddMoney) {
        nMoney += _nAddMoney;
        pnlShopBuy.lblMoney.setText("You have $" + nMoney + ".");
    }

    public int getMoney() {
        return nMoney;
    }

    public int getPowder() {
        return nPowder;
    }

    public int getGoombaPrice() {
        return nGoombaPrice;
    }

    public int getStarPrice() {
        return nStarPrice;
    }

    public int getSpikePrice() {
        return nSpikePrice;
    }

    public void setResponse(String sResponse) {
        pnlShopBuy.lblResponse.setText(sResponse);
    }

    public void setMoney(int _nMoney) {
        nMoney = _nMoney;
        pnlShopBuy.lblMoney.setText("You have $" + nMoney + ".");
    }

    public void setPowder(int _nPowder) {
        nPowder = _nPowder;
        pnlShopBuy.lblPowder.setText("You have " + nPowder + " powder.");
    }

    public void removeMoney(int _nSpentMoney) {
        nMoney -= _nSpentMoney;
        pnlShopBuy.lblMoney.setText("You have $" + nMoney + ".");
    }

    public void removePowder(int _nSpentPowder) {
        nPowder -= _nSpentPowder;
        pnlShopBuy.lblPowder.setText("You have " + nPowder + " powder.");
    }

    public void paintComponent(Graphics g) {
        super.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufImgBg, null, 0, 0);
    }
}
