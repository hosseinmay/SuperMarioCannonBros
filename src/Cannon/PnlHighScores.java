package Cannon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
//Manages the high scores (uses the player class)
public class PnlHighScores extends JPanel {

    private ArrayList<Player> arcPlayer = new ArrayList<Player>();
    String sFileLocation = "Scores.txt";
    PnlScoreText pnlScoreText = new PnlScoreText();
    PnlScoreButtons pnlScoreButtons = new PnlScoreButtons();
    JFileChooser fcScores = new JFileChooser();
    PrintWriter fout;
    File filScores;
    private int nComparator = 0;
    BufferedImage bufImgBg;

    PnlHighScores() {
        this.setSize(900, 600);
        this.setLayout(new BorderLayout());
        this.add(pnlScoreText, BorderLayout.CENTER);
        this.add(pnlScoreButtons, BorderLayout.SOUTH);
        pnlScoreButtons.btnChoose.addActionListener(new ActFileChooser());
        pnlScoreButtons.btnClearScores.addActionListener(new ActClearScores());
        pnlScoreButtons.btnNameSort.addActionListener(new ActNameSort());
        pnlScoreButtons.btnNumSort.addActionListener(new ActNumSort());
        try {
            bufImgBg = ImageIO.read(new File("Images/HighScoresBG.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        fcScores.addChoosableFileFilter(new TxtFilter());

        //***********This seems to cause a strange error only the first time the program is run!***********
        fcScores.setCurrentDirectory(new File("."));//http://www.rgagnon.com/javadetails/java-0370.html
    }

    public void addPlayer(String _sName, int _nScore) {
        Player player = new Player();
        player.setName(_sName);
        player.setScore(_nScore);
        arcPlayer.add(player);

        try {
            fout = new PrintWriter(new FileWriter(sFileLocation));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < arcPlayer.size(); i++) {
            fout.println(arcPlayer.get(i).getName() + " " + arcPlayer.get(i).getScore());
        }
        fout.close();
        pnlScoreText.updateScoreText();
    }

    class PnlScoreText extends JPanel {

        JTextArea areScores = new JTextArea();

        PnlScoreText() {
            this.add(areScores);
            areScores.setEditable(false);
            File file = new File("scores.txt");
            updateScoreFile("scores.txt");
            this.setOpaque(false);


        }

        public void setText(String sText) {
            areScores.setText(sText);
        }

        public void updateScoreText() {
            areScores.setText("");
            if (nComparator == 0) {
                Collections.sort(arcPlayer, new compScore());
            } else {
                Collections.sort(arcPlayer, new compName());
            }
            for (int ii = 0; ii < arcPlayer.size(); ii++) {
                areScores.append(arcPlayer.get(ii).getName() + " " + arcPlayer.get(ii).getScore() + "\n");
            }
        }

        public void updateScoreFile(String sFile) {
            int i = 0;
            try {
                Scanner fin = new Scanner(new FileReader(sFile));
                arcPlayer.clear();
                while (fin.hasNext()) {
                    arcPlayer.add(new Player());
                    arcPlayer.get(i).setName(fin.next());
                    arcPlayer.get(i).setScore(fin.nextInt());
                    i++;
                }
                updateScoreText();
            } catch (Exception e) {
                areScores.setText("Invalid/Missing Scores File/WTF?");
                e.printStackTrace();
            }
        }
    }

    class PnlScoreButtons extends JPanel {

        JButton btnBack = new JButton("Back");
        JButton btnChoose = new JButton("Select Scores File");
        JButton btnClearScores = new JButton("Clear Scores");
        JButton btnNameSort = new JButton("Sort by Name");
        JButton btnNumSort = new JButton("Sort by #");

        PnlScoreButtons() {
            this.add(btnBack);
            this.add(btnChoose);
            this.add(btnClearScores);
            this.add(btnNameSort);
            this.add(btnNumSort);
            this.setOpaque(false);
        }
    }

    class ActFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {//http://download.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemoProject/src/components/FileChooserDemo.java
            int returnVal = fcScores.showOpenDialog(PnlHighScores.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                filScores = fcScores.getSelectedFile();
                sFileLocation = filScores.getAbsolutePath();//.getParent();//http://www.java-forums.org/awt-swing/29485-how-retrieve-path-filechooser.html
                //System.out.println(filScores);
                pnlScoreText.updateScoreFile(sFileLocation);
            }

        }
    }

    class ActClearScores implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            arcPlayer.clear();
            pnlScoreText.updateScoreText();
            try {
                fout = new PrintWriter(new FileWriter(sFileLocation));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            fout.print("");
            fout.close();
        }
    }

    class ActNameSort implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            nComparator = 1;
            pnlScoreText.updateScoreText();
        }
    }

    class ActNumSort implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            nComparator = 0;
            pnlScoreText.updateScoreText();
        }
    }

    class compName implements Comparator {

        public int compare(Object o1, Object o2) {
            String sName1 = ((Player) o1).getName();
            String sName2 = ((Player) o2).getName();
            if (sName1.compareTo(sName2) < 0) {
                return -1;
            } else if (sName1.compareTo(sName2) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    class compScore implements Comparator {

        public int compare(Object o1, Object o2) {
            int nScore1 = ((Player) o1).getScore();
            int nScore2 = ((Player) o2).getScore();
            if (nScore1 > nScore2) {
                return -1;
            } else if (nScore1 < nScore2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufImgBg, null, 0, 0);
    }
    class TxtFilter extends javax.swing.filechooser.FileFilter {//http://www.exampledepot.com/egs/javax.swing.filechooser/Filter.html

        public boolean accept(File file) {
            String filename = file.getName();
            if (file.isDirectory()) {
                return true;
            } else {
                return filename.endsWith(".txt");
            }
        }

        public String getDescription() {
            return "*.txt";
        }
    }
}
