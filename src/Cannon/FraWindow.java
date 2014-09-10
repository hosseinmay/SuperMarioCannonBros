package Cannon;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
//Frame that creates all of the "Main" panels (Play, Help, HighScore, Settings)
public class FraWindow extends JFrame {

    PnlMainMenu pnlMainMenu = new PnlMainMenu();
    PnlPlay pnlPlay = new PnlPlay();
    PnlHighScores pnlHighScores = new PnlHighScores();
    PnlHelp pnlHelp = new PnlHelp();
    PnlSettings pnlSettings = new PnlSettings();
    FraSubmitScore fraSubmitScore = new FraSubmitScore();

    public FraWindow() {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Super Mario Cannon Bro's");
        this.setVisible(true);
        this.setSize(905, 634);
        this.setLayout(new BorderLayout());
        this.add(pnlPlay, BorderLayout.CENTER);
        pnlPlay.setVisible(false);
        this.add(pnlHighScores, BorderLayout.CENTER);
        pnlHighScores.setVisible(false);
        this.add(pnlHelp, BorderLayout.CENTER);
        pnlHelp.setVisible(false);
        this.add(pnlSettings, BorderLayout.CENTER);
        pnlSettings.setVisible(false);
        this.add(pnlMainMenu, BorderLayout.CENTER);
        pnlMainMenu.setVisible(true);
        pnlMainMenu.btnPlay.addActionListener(new ActPlay());
        pnlMainMenu.btnHighScores.addActionListener(new ActHighScores());
        pnlMainMenu.btnSettings.addActionListener(new ActSettings());
        pnlMainMenu.btnHelp.addActionListener(new ActHelp());
        pnlMainMenu.btnExit.addActionListener(new ActExit());
        pnlHighScores.pnlScoreButtons.btnBack.addActionListener(new ActMainMenu());
        pnlHelp.btnBack.addActionListener(new ActMainMenu());
        pnlSettings.btnBack.addActionListener(new ActMainMenu());
        pnlSettings.btnLoadWorld.addActionListener(new ActAddWorld());
        pnlPlay.setMenuButtonListener(new ActMainAndSendScore());
        fraSubmitScore.btnSubmit.addActionListener(new ActSendScore());
        
    }

    public void addWorld(String _sFile) {
        pnlPlay.addWorld(_sFile);
    }

    public void addWorld(File _File) {
        pnlPlay.addWorld(_File);
    }

    public void setAllPnlFalse() {
        pnlMainMenu.setVisible(false);
        pnlHighScores.setVisible(false);
        pnlHelp.setVisible(false);
        pnlSettings.setVisible(false);
        pnlPlay.setVisible(false);
    }

    public class ActAddWorld implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (pnlSettings.isChosen()) {
                File fileWorld = pnlSettings.getWorld();
                pnlSettings.setChosen(false);
                addWorld(fileWorld);
                pnlSettings.setInfoText("File Loaded");
            } else {
                pnlSettings.setInfoText("You must chose a valid file first");
            }
        }
    }
    //Handles cheat codes

    
    public class ActPlay implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            setAllPnlFalse();
            pnlPlay.setVisible(true);

        }
    }

    public class ActEnvironment implements ActionListener {

        public void actionPerformed(ActionEvent e) {//http://download.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemoProject/src/components/FileChooserDemo.java
        }
    }

    public class ActHighScores implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            setAllPnlFalse();
            pnlHighScores.setVisible(true);

        }
    }

    public class ActHelp implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            setAllPnlFalse();
            pnlHelp.setVisible(true);
        }
    }

    public class ActSettings implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            setAllPnlFalse();
            pnlSettings.setVisible(true);
        }
    }

    public class ActExit implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class ActMainMenu implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            setAllPnlFalse();
            pnlMainMenu.setVisible(true);
            pnlPlay.resetGame();
        }
    }

    public class ActMainAndSendScore implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            fraSubmitScore.setVisible(true);
            setAllPnlFalse();
            pnlMainMenu.setVisible(true);

        }
    }

    public class ActSendScore implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            pnlHighScores.addPlayer(fraSubmitScore.txtName.getText(), pnlPlay.getScore());
            fraSubmitScore.setVisible(false);
            pnlPlay.resetGame();
        }
    }
}
