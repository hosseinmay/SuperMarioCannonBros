package Cannon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
//Allows the user input of user created worlds --DOES NOT handle the information, passes it to panel play
public class PnlSettings extends JPanel {

    JLabel lblInfo = new JLabel();
    JButton btnBack = new JButton("Back");
    JButton btnChooseWorld = new JButton("Choose World");
    JButton btnLoadWorld = new JButton("Load World");
    JFileChooser fcEnv = new JFileChooser();
    File fileWorld;
    String sFileLocation;
    boolean isChosen = false;
    BufferedImage bufImgBg;

    PnlSettings() {
        this.add(lblInfo);
        this.add(btnBack);
        this.add(btnChooseWorld);
        this.add(btnLoadWorld);
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setText("After choosing and loading a world, click back to begin playing using it.");
        btnChooseWorld.addActionListener(new ActFileChooser());
        this.setSize(900, 600);
        try {
            bufImgBg = ImageIO.read(new File("Images/SettingsBG.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        fcEnv.addChoosableFileFilter(new EnvFilter());

        //***********This seems to cause a strange error only the first time the program is run!***********
        fcEnv.setCurrentDirectory(new File("."));//http://www.rgagnon.com/javadetails/java-0370.html



    }

    public File getWorld() {
        return fileWorld;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean _isChosen) {
        isChosen = _isChosen;
    }

    public void setInfoText(String _sInfo) {
        lblInfo.setText(_sInfo);
    }

    class ActFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {//http://download.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemoProject/src/components/FileChooserDemo.java
            int returnVal = fcEnv.showOpenDialog(PnlSettings.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fileWorld = fcEnv.getSelectedFile();
                sFileLocation = fileWorld.getAbsolutePath();//.getParent();//http://www.java-forums.org/awt-swing/29485-how-retrieve-path-filechooser.html
                if (sFileLocation.endsWith("env")) {
                    lblInfo.setText("File successfully chosen");
                    isChosen = true;
                } else {
                    lblInfo.setText("Invalid File");
                }
                //System.out.println(filScores);
            }

        }
    }

    public void paintComponent(Graphics g) {
        super.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufImgBg, null, 0, 0);
    }

    class EnvFilter extends javax.swing.filechooser.FileFilter {//http://www.exampledepot.com/egs/javax.swing.filechooser/Filter.html

        public boolean accept(File file) {
            String filename = file.getName();
            if (file.isDirectory()) {
                return true;
            } else {
                return filename.endsWith(".env");
            }
        }

        public String getDescription() {
            return "*.env";
        }
    }
}
