package Cannon;

import java.awt.geom.Point2D;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
//Handles the entire Game portion, including creation of the Shop, triggering firing, etc.
class PnlPlay extends JPanel {

    private ArrayList<World> arcWorld = new ArrayList<World>();
    private ArrayList<String> arsLaunches = new ArrayList<String>();
    private ArrayList<String> arsExplosions = new ArrayList<String>();
    private ArrayList<String> arsSelections = new ArrayList<String>();
    private ArrayList<String> arsWins = new ArrayList<String>();
    private Timer timeBall,  timeExplode;
    PnlPlayDrawMovements pnlDrawMovements = new PnlPlayDrawMovements();
    private PnlPlayButtons pnlPlayButtons = new PnlPlayButtons();
    private PnlPlayShop pnlPlayShop = new PnlPlayShop();
    private PlotProjectile plotProjectile = new PlotProjectile();
    private boolean isGoomba = false,  isSpike = false,  isStar = false,  isExplode = false;
    private int nScore = 0;
    private double dVelosity = 40;
    private Rectangle2D.Double rectBallBounds;
    private Rectangle2D.Double rectRadius;
    private String sCodes = "";

    //Loads everythign up
    public PnlPlay() {
        this.setVisible(true);
        this.setSize(900, 600);
        BorderLayout layout = new BorderLayout();
        layout.setVgap(0);
        layout.setHgap(0);
        this.setLayout(layout);
        this.add(pnlPlayShop);
        pnlPlayShop.setVisible(false);
        this.add(pnlDrawMovements, layout.CENTER);
        this.add(pnlPlayButtons, layout.SOUTH);


        pnlDrawMovements.addMouseMotionListener(new ActMouseMovement());
        pnlDrawMovements.addMouseListener(new ActMouseAction());
        pnlPlayButtons.btnFire.addActionListener(new ActFireButton());

        pnlPlayButtons.sliAngle.addChangeListener(new ActAngleSlider());
        pnlPlayButtons.btnShop.addActionListener(new ActShopButton());
        pnlPlayShop.pnlShopBuy.btnBuyGoomba.addActionListener(new ActBuyGoomba());
        pnlPlayShop.pnlShopBuy.btnBuySpike.addActionListener(new ActBuySpike());
        pnlPlayShop.pnlShopBuy.btnBuyStar.addActionListener(new ActBuyStar());
        pnlPlayShop.pnlShopReturn.btnBack.addActionListener(new ActBackToGameButton());
        pnlPlayButtons.AddBulletButton(new ActBulletButton("Images/bulletbill.png", new ImageIcon("Images/bulletbill.png"), 100));
        pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new keyDispatcher());//http://stackoverflow.com/questions/286727/java-keylistener-for-jframe-is-being-unresponsive

        loadWorlds();
        loadSettings();

    }

    //Handles cheat codes
    private class keyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
                System.out.println("ActCheats called with an " + e.getKeyChar());
                sCodes += String.valueOf(e.getKeyChar());
                System.out.println(sCodes);
                if (sCodes.contains("Graham")) {
                    sCodes = "";
                    nScore += 1000;
                    pnlDrawMovements.setScore(nScore);
                } else if (sCodes.contains("Hossein")) {
                    sCodes = "";
                    nScore -= 1000;
                    pnlDrawMovements.setScore(nScore);
                } else if (sCodes.contains("$$$")) {
                    sCodes = "";
                    pnlPlayShop.addMoney(1000);
                    pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());
                } else if (sCodes.contains("powder")) {
                    sCodes = "";
                    pnlPlayShop.addPowder(1000);
                    pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());
                }
            }
            return false;
        }
    }

    //Abstract Action used for adding buttons to MenuBar
    public class ActBulletButton extends AbstractAction { //CoreJava8 Menubar Code (Heavily Modified)

        int nPower;

        public ActBulletButton(String sFile, Icon icon, int _nPower) {
            putValue(Action.NAME, sFile);
            putValue(Action.SMALL_ICON, icon);
            putValue("File", sFile);
            nPower = _nPower;
            putValue(Action.SHORT_DESCRIPTION, sFile);
        }

        public void actionPerformed(ActionEvent event) {
            if (!pnlDrawMovements.isFired() && !pnlDrawMovements.isExploded()) {
                String sLocation = (String) getValue("File");
                System.out.println(sLocation);
                Projectile projectile = new Projectile(sLocation);
                projectile.setPower(nPower);
                pnlDrawMovements.setProjectile(projectile);
                if (arsSelections.size() > 0) {
                    SoundPlayer.playSoundFile(arsSelections.get((int) Math.random() * arsSelections.size()));
                }
            }
        }
    }

    //the ActBuy... classes allow the purchase of the games various projectiles
    public class ActBuyGoomba implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!isGoomba) {
                if (pnlPlayShop.getMoney() > pnlPlayShop.getGoombaPrice()) {
                    pnlPlayShop.removeMoney(pnlPlayShop.getGoombaPrice());
                    isGoomba = true;
                    pnlPlayButtons.AddBulletButton(new ActBulletButton("Images/goomba.png", new ImageIcon("Images/goomba.png"), 200));
                    pnlPlayShop.setResponse("Goomba successfully unlocked");
                } else {
                    pnlPlayShop.setResponse("Not enough money");
                }
            } else {
                pnlPlayShop.setResponse("You already unlocked the goomba");
            }
        }
    }

    public class ActBuySpike implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!isSpike) {
                if (pnlPlayShop.getMoney() > pnlPlayShop.getSpikePrice()) {
                    pnlPlayShop.removeMoney(pnlPlayShop.getSpikePrice());
                    isSpike = true;
                    pnlPlayButtons.AddBulletButton(new ActBulletButton("Images/spike.png", new ImageIcon("Images/spike.png"), 400));
                    pnlPlayShop.setResponse("Spike shell successfully unlocked");
                } else {
                    pnlPlayShop.setResponse("Not enough money");
                }
            } else {
                pnlPlayShop.setResponse("You already unlocked the spike shell");
            }
        }
    }

    public class ActBuyStar implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!isStar) {
                if (pnlPlayShop.getMoney() > pnlPlayShop.getStarPrice()) {
                    pnlPlayShop.removeMoney(pnlPlayShop.getStarPrice());
                    isStar = true;
                    pnlPlayButtons.AddBulletButton(new ActBulletButton("Images/star.gif", new ImageIcon("Images/star.gif"), 1000));
                    pnlPlayShop.setResponse("Star successfully unlocked");
                } else {
                    pnlPlayShop.setResponse("Not enough money");
                }
            } else {
                pnlPlayShop.setResponse("You already unlocked the star of death");
            }
        }
    }
    // The heart and soul of the game, this class triggeres a number of methods and classes thet "run" the game

    public class ActFireButton implements ActionListener {

        double dTime = 0;

        public void actionPerformed(ActionEvent e) {
            if (!pnlDrawMovements.isFired()) {
                if (!pnlDrawMovements.isExploded()) {
                    if (plotProjectile.getVelocity() < pnlPlayShop.getPowder()) {
                        if (arsLaunches.size() > 0) {
                            int nRand = (int) (Math.random() * arsLaunches.size());
                            SoundPlayer.playSoundFile(new File(arsLaunches.get(nRand)));
                        }
                        pnlPlayShop.removePowder((int) plotProjectile.getVelocity());
                        pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());
                        dTime = 0;
                        pnlDrawMovements.setFired(true);
                        plotProjectile.setAngle(pnlDrawMovements.getCannonAngle());
                        plotProjectile.setVelocity(pnlPlayButtons.sliGunpowder.getValue());


                        AbstractAction updateLocation = new AbstractAction() {

                            public void actionPerformed(ActionEvent e) {
                                if (pnlDrawMovements.isFired()) {
                                    dTime += 0.04;
                                    Point2D ptBall = plotProjectile.getLocation(dTime);
                                    ptBall.setLocation(ptBall.getX() * 5, ptBall.getY() * 5);
                                    pnlDrawMovements.setBallLocation(ptBall);
                                    int nBallX, nBallY;
                                    nBallX = (int) (ptBall.getX() + pnlDrawMovements.getCannonX());
                                    nBallY = (int) (0 - ptBall.getY() + pnlDrawMovements.getCannonY());
                                    rectBallBounds = new Rectangle2D.Double(nBallX, nBallY, pnlDrawMovements.getProjectileWidth(), pnlDrawMovements.getProjectileHeight());

                                    //From here down checks for colissions, if any are detected isExpolde is flaged true to trigger the explode method

                                    for (int i = 0; i < pnlDrawMovements.getBossQuan(); i++) {
                                        if (pnlDrawMovements.isBossAlive(i)) {
                                            if (rectBallBounds.intersects(pnlDrawMovements.getBossBounds(i))) {//In class presentation, Keith and Cole?
                                                isExplode = true;
                                                break;
                                            }

                                        }
                                    }
                                    for (int i = 0; i < pnlDrawMovements.getDestructableObjQuan(); i++) {
                                        if (pnlDrawMovements.isDestructableAlive(i)) {
                                            if (rectBallBounds.intersects(pnlDrawMovements.getDestructableObjBounds(i))) {//*****In class presentation Keith and Cole?
                                                isExplode = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (plotProjectile.getGravity() < 0) {
                                        if (nBallY > 450) {
                                            isExplode = true;
                                        }
                                    } else {
                                        if (nBallY < 0) {
                                            isExplode = true;
                                        }
                                    }
                                    if (isExplode) {
                                        explode();
                                        isExplode = false;
                                    }
                                } else {
                                    timeBall.stop();
                                    plotProjectile.randomizeWind();
                                    pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());
                                }
                            }
                        };
                        timeBall = new Timer(20, updateLocation);
                        timeBall.start();
                    } else if (pnlPlayShop.getPowder() == 0) {
                        System.out.println("Out of Powder");
                    } else {
                        System.out.println("Not Enough Powder");
                    }
                }
            }
        }
    }
    //Handles slider event

    class ActAngleSlider implements ChangeListener {

        public void stateChanged(ChangeEvent e) {

            JSlider source = (JSlider) e.getSource();
            double dAngle = (double) source.getValue();
            pnlDrawMovements.setCannonAngle(Math.toRadians(dAngle));
            Point2D ptCannon = pnlDrawMovements.getCannonLocation();
            pnlDrawMovements.repaint((int) ptCannon.getX() - 55, (int) ptCannon.getY() - 55, 110, 110);
        }
    }

    //Switches from shop to game
    class ActBackToGameButton implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            pnlPlayShop.setVisible(false);
            pnlPlayButtons.setVisible(true);
            pnlDrawMovements.setVisible(true);
            pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());

        }
    }
    //Moves cannon based on mouse

    public class ActMouseMovement implements MouseMotionListener {

        public void mouseDragged(MouseEvent e) {
            double dBase, dHeight, dAngle;
            Point2D ptCannonLocation = pnlDrawMovements.getCannonLocation();
            dBase = e.getX() - ptCannonLocation.getX();
            dHeight = ptCannonLocation.getY() - 5 - e.getY() + 10;
            dAngle = Math.atan2(dHeight, dBase);
            pnlDrawMovements.setCannonAngle(dAngle);
            Point2D ptCannon = pnlDrawMovements.getCannonLocation();
            pnlDrawMovements.repaint((int) ptCannon.getX() - 55, (int) ptCannon.getY() - 55, 110, 110);
            pnlPlayButtons.setAngleSlider((int) Math.toDegrees(dAngle));
        }//http://download.oracle.com/javase/tutorial/uiswing/examples/events/MouseMotionEventDemoProject/src/events/MouseMotionEventDemo.java

        public void mouseMoved(MouseEvent e) {
        }
    }
    //Moves cannon based on mouse

    public class ActMouseAction implements MouseListener {

        public void mousePressed(MouseEvent e) {
            double dBase, dHeight, dAngle;
            Point2D ptCannonLocation = pnlDrawMovements.getCannonLocation();
            dBase = e.getX() - ptCannonLocation.getX();
            dHeight = ptCannonLocation.getY() - 5 - e.getY() + 10;
            dAngle = Math.atan2(dHeight, dBase);
            pnlDrawMovements.setCannonAngle(dAngle);
            Point2D ptCannon = pnlDrawMovements.getCannonLocation();
            pnlDrawMovements.repaint((int) ptCannon.getX() - 55, (int) ptCannon.getY() - 55, 110, 110);
            pnlPlayButtons.setAngleSlider((int) Math.toDegrees(dAngle));
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        } // From http://www.rgagnon.com/javadetails/java-0236.html
    }
    //Opens Shop

    class ActShopButton implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            pnlPlayShop.setVisible(true);
            pnlPlayButtons.setVisible(false);
            pnlDrawMovements.setVisible(false);

        }
    }

    public void addWorld(File _fileWorld) {
        addWorld(_fileWorld.getAbsolutePath());
    }
    //Parses the code from env files into a World class file

    public void addWorld(String _sFile) {
        boolean isUnique = true;
        System.out.println(_sFile + " has made it to pnlPlay");
        for (int i = 0; i < arcWorld.size(); i++) {
            if (_sFile.equalsIgnoreCase(arcWorld.get(i).getPath())) {
                isUnique = false;
            }
        }

        if (isUnique) {
            System.out.println(_sFile + " is the first of its kind");
            try {
                PrintWriter fout = new PrintWriter(new FileWriter("worlds.txt"));
                Scanner fin = new Scanner(new FileReader(_sFile));

                String sImgBG, sImgBoss, sImgDestructable, sDestructableSpecial = "", sImgMario, sTrash, sName;
                int nBossX, nBossY, nBossHP, nBossPts, nWindVarience, nMarioX, nMarioY, nDestructableX, nDestructableY, nDestructableHP = 100, nDestructableHeight, nDestructableWidth;
                double dGravity;
                World world;
                ArrayList<Boss> arcBoss = new ArrayList<Boss>();
                ArrayList<DestructableObject> arcDestructable = new ArrayList<DestructableObject>();
                sTrash = fin.next();
                sName = fin.next();
                sTrash = fin.next();
                sImgBG = fin.next();
                sTrash = fin.next();
                while (true) {

                    if (sTrash.equalsIgnoreCase("BossImage:")) {

                        sImgBoss = fin.next();
                        System.out.println("Boss " + sImgBoss + " Discovered");
                        sTrash = fin.next();
                        nBossX = fin.nextInt();
                        sTrash = fin.next();
                        nBossY = fin.nextInt();
                        sTrash = fin.next();
                        arcBoss.add(new Boss(sImgBoss, 1000, 1000, nBossX, nBossY));

                    } else if (sTrash.equalsIgnoreCase("DestructableImage:")) {
                        sImgDestructable = fin.next();
                        sTrash = fin.next();
                        nDestructableX = fin.nextInt();
                        sTrash = fin.next();
                        nDestructableY = fin.nextInt();
                        sTrash = fin.next();
                        nDestructableHP = 100;
                        nDestructableWidth = 31;
                        nDestructableHeight = 33;
                        sDestructableSpecial = "";
                        while (sTrash.startsWith("Destructable")) {
                            if (sTrash.equalsIgnoreCase("DestructableHealth:")) {
                                nDestructableHP = fin.nextInt();
                                sTrash = fin.next();
                            } else if (sTrash.equalsIgnoreCase("DestructableWidth:")) {
                                nDestructableWidth = fin.nextInt();
                                sTrash = fin.next();
                            } else if (sTrash.equalsIgnoreCase("DestructableHeight:")) {
                                nDestructableHeight = fin.nextInt();
                                sTrash = fin.next();
                            } else if (sTrash.equalsIgnoreCase("DestructableSpecial:")) {
                                sDestructableSpecial = fin.next();
                                sTrash = fin.next();
                            }
                        }

                        arcDestructable = fillDistructables(sImgDestructable, nDestructableX, nDestructableY, nDestructableHP, nDestructableWidth, nDestructableHeight, sDestructableSpecial);
                    } else {
                        break;
                    }
                }
                //sTrash = fin.next();
                dGravity = fin.nextDouble();
                sTrash = fin.next();
                nWindVarience = fin.nextInt();
                BufferedImage buffImgBG = ImageIO.read(new File(sImgBG));
                sTrash = fin.next();
                sImgMario = fin.next();
                sTrash = fin.next();
                nMarioX = fin.nextInt();
                sTrash = fin.next();
                nMarioY = fin.nextInt();
                Mario mario = new Mario(sImgMario, nMarioX, nMarioY);
                if (arcDestructable.size() == 0) {
                    world = new World(arcBoss, buffImgBG, dGravity, nWindVarience, _sFile, mario);
                } else {
                    world = new World(arcBoss, buffImgBG, dGravity, nWindVarience, _sFile, mario, arcDestructable);
                }
                arcWorld.add(world);
                for (int i = 0; i < arcWorld.size(); i++) {
                    fout.println(arcWorld.get(i).getPath());
                }
                fout.close();
                System.out.println(world.getPath() + " loaded");
            //pnlPlay.setEnvironment(sFileBG, sFileBoss,nBossX,nBossY, nBossHP, nBossPts, dGravity, nWindVarience);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //Runs the "explosion" removing health for a direct hit, and slightly less for anything within the blast radius
    public void explode() {
        if (arsExplosions.size() > 0) {
            int nRand = (int) (Math.random() * arsExplosions.size());
            SoundPlayer.playSoundFile(new File(arsExplosions.get(nRand)));
        }
        pnlDrawMovements.setExploded(true);
        pnlDrawMovements.setFired(false);
        timeBall.stop();

        plotProjectile.randomizeWind();
        pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());

        AbstractAction updateExplosion = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {

                pnlDrawMovements.setExploded(false);
                if (!pnlDrawMovements.isAnyBossAlive()) {
                    levelUp();
                }
                timeExplode.stop();

            }
        };
        timeExplode = new Timer(500, updateExplosion);
        timeExplode.start();
        //Create a bounding box of the blast radius
        int nPower = pnlDrawMovements.getProjectilePower();
        rectRadius = new Rectangle2D.Double(pnlDrawMovements.getBallX() - (nPower / 10), pnlDrawMovements.getBallY() - (nPower / 10), pnlDrawMovements.getProjectileWidth() + nPower / 5, pnlDrawMovements.getProjectileHeight() + nPower / 5);
        if (pnlDrawMovements.isAnyBossAlive()) {
            for (int i = 0; i < pnlDrawMovements.getBossQuan(); i++) {
                if (pnlDrawMovements.isBossAlive(i)) {
                    if (rectBallBounds.intersects(pnlDrawMovements.getBossBounds(i))) {//*****In class presentation, Keith and Cole?******
                        if (pnlDrawMovements.getBossHealth(i) > nPower) {
                            pnlDrawMovements.removeBossHealth(nPower, i);
                        } else {
                            pnlDrawMovements.setBossHealth(0, i);
                            pnlDrawMovements.setBossAlive(false, i);
                            nScore += pnlDrawMovements.getBossPoints(i);
                        }
                        pnlPlayShop.addMoney(100);
                    }
                    if (rectRadius.intersects(pnlDrawMovements.getBossBounds(i))) {//*****In class presentation, Keith and Cole?******
                        if (pnlDrawMovements.getBossHealth(i) > nPower / 2) {
                            pnlDrawMovements.removeBossHealth(nPower / 2, i);
                        } else {
                            pnlDrawMovements.setBossHealth(0, i);
                            pnlDrawMovements.setBossAlive(false, i);
                            nScore += pnlDrawMovements.getBossPoints(i);
                        }
                        pnlPlayShop.addMoney(100);
                    }

                }
            }
        }
        if (pnlDrawMovements.isAnyDestructableAlive()) {
            for (int i = 0; i < pnlDrawMovements.getDestructableObjQuan(); i++) {
                if (pnlDrawMovements.isDestructableAlive(i)) {
                    if (rectBallBounds.intersects(pnlDrawMovements.getDestructableObjBounds(i))) {//*****In class presentation Keith and Cole?
                        if (pnlDrawMovements.getDestructableObjHealth(i) > nPower) {
                            pnlDrawMovements.removeDestructableHealth(nPower, i);
                        } else {
                            pnlDrawMovements.setDestructableHealth(0, i);
                            pnlDrawMovements.setDestructableAlive(false, i);
                            nScore += pnlDrawMovements.getDestructableObjPoints(i);
                        }
                        pnlPlayShop.addMoney(10);

                    }
                    if (rectRadius.intersects(pnlDrawMovements.getDestructableObjBounds(i))) {//*****In class presentation Keith and Cole?
                        if (pnlDrawMovements.getDestructableObjHealth(i) > nPower / 2) {
                            pnlDrawMovements.removeDestructableHealth(nPower / 2, i);
                        } else {
                            pnlDrawMovements.setDestructableHealth(0, i);
                            pnlDrawMovements.setDestructableAlive(false, i);
                            nScore += pnlDrawMovements.getDestructableObjPoints(i);
                        }
                        pnlPlayShop.addMoney(10);

                    }
                }
            }
        }
        pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());
        pnlDrawMovements.setScore(nScore);

    }

    //Populates a given rectangle with enough destructable obects with the given image to fill the whole area
    public ArrayList fillDistructables(String _sImgLocation, int _nX, int _nY, int _nHealth, int _nWidth, int _nHeight, String _sSpecial) {
        ArrayList<DestructableObject> arcDestructable = new ArrayList<DestructableObject>();
        int nXTiles, nYTiles;
        try {
            BufferedImage bufImgDestructable = ImageIO.read(new File(_sImgLocation));
            nXTiles = ((int) ((double) _nWidth / (double) bufImgDestructable.getWidth())) + 1;
            nYTiles = ((int) ((double) _nHeight / (double) bufImgDestructable.getHeight())) + 1;
            for (int i = 0; i < nYTiles; i++) {
                for (int j = 0; j < nXTiles; j++) {
                    //DestructableObject dest = new DestructableObject(String _sImgLocation, int _nHealth,int _nHealth,int _nX,int _nY, String _sSpecial);
                    arcDestructable.add(new DestructableObject(_sImgLocation, _nHealth, _nX + (j * bufImgDestructable.getWidth()), _nY + (i * bufImgDestructable.getHeight()), _sSpecial));
                }
            }
        } catch (Exception e) {
        }
        return arcDestructable;
    }

    public int getScore() {
        return nScore;
    }

    //Runs things required for a level up
    public void levelUp() {
        if (arsWins.size() > 0) {
            SoundPlayer.playSoundFile(arsWins.get((int) Math.random() * arsWins.size()));
        }
        pnlPlayShop.setVisible(true);
        pnlPlayButtons.setVisible(false);
        pnlPlayButtons.sliAngle.setValue(0);
        pnlDrawMovements.setVisible(false);
        pnlDrawMovements.addLevel();
        int nWorld, nCurrentHP, nCurrentPts;
        pnlDrawMovements.setLevelHealth(pnlDrawMovements.getLevelHealth() + 1000);
        pnlDrawMovements.setLevelPoints(pnlDrawMovements.getLevelPoints() + 1000);
        System.out.println(arcWorld.size());
        nWorld = (int) (Math.random() * arcWorld.size());
        System.out.println("*******World #: " + nWorld);
        selectWorld(nWorld);


    }

    //Parses the Game Settings.txt file (currently only sounds)
    public void loadSettings() {
        try {
            String sTrash;
            //File fileWorlds = new File("worlds.txt");
            System.out.println("Loading Settings");
            if (new File("GameSettings.txt").exists()) {
                System.out.println("GameSettings.txt exists");
                Scanner fin = new Scanner(new FileReader("GameSettings.txt"));
                while (fin.hasNext()) {
                    sTrash = fin.next();
                    if (sTrash.equalsIgnoreCase("LaunchSound:")) {
                        arsLaunches.add(fin.next());
                        System.out.println("Launch Sound " + arsLaunches.get(arsLaunches.size() - 1) + "Added");

                    } else if (sTrash.equalsIgnoreCase("ExplodeSound:")) {
                        arsExplosions.add(fin.next());
                        System.out.println("Explode Sound " + arsExplosions.get(arsExplosions.size() - 1) + "Added");
                    } else if (sTrash.equalsIgnoreCase("VictorySound:")) {
                        arsWins.add(fin.next());
                        System.out.println("Win Sound " + arsWins.get(arsWins.size() - 1) + "Added");
                    } else if (sTrash.equalsIgnoreCase("SelectSound:")) {
                        arsSelections.add(fin.next());
                        System.out.println("Selection Sound " + arsSelections.get(arsSelections.size() - 1) + "Added");
                    } else if (sTrash.equalsIgnoreCase("BackgroundSound:")) {
                        SoundPlayer.loop(fin.next());
                        System.out.println("Background Sound Added");
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No settings eh? FAIL!");
            e.printStackTrace();

        }
    }

    //Parses worlds.txt if it exists (loading environments) or loeads mushroomKingdom if it doesn't
    public void loadWorlds() {
        try {
            //File fileWorlds = new File("worlds.txt");
            System.out.println("Loading Worlds");
            if (new File("worlds.txt").exists()) {
                System.out.println("worlds.txt exists");
                Scanner fin = new Scanner(new FileReader("worlds.txt"));
                arcWorld.clear();
                System.out.println("arcWorld cleared");
                if (fin.hasNext()) {
                    addWorld(fin.next());
                    while (fin.hasNext()) {
                        addWorld(fin.next());
                        System.out.println("Multiple env detected");
                    }
                    selectWorld(0);
                } else {
                    addWorld("Environments/mushroomkingdom.env");
                    selectWorld(0);
                }
            } else {
                addWorld("Environments/mushroomkingdom.env");
                selectWorld(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addWorld("Environments/mushroomkingdom.env");
            selectWorld(0);
        }
    }

    //Resets all variables that need to be reset when the game restarts
    public void resetGame() {
        nScore = 0;
        pnlDrawMovements.setScore(nScore);
        isGoomba = false;
        isStar = false;
        isSpike = false;
        pnlDrawMovements.setVisible(true);
        pnlDrawMovements.setLevel(1);
        pnlPlayButtons.setVisible(true);
        pnlPlayShop.setVisible(false);
        pnlPlayShop.setPowder(1000);
        pnlPlayShop.setMoney(100000);
        pnlDrawMovements.setFired(false);
        pnlDrawMovements.setExploded(false);
        pnlDrawMovements.setCannonAngle(0);
        pnlDrawMovements.resetProjectile();
        pnlPlayButtons.barBullets.removeAll();
        pnlPlayButtons.AddBulletButton(new ActBulletButton("Images/bulletbill.png", new ImageIcon("Images/bulletbill.png"), 0));
        pnlPlayButtons.updateStats(pnlPlayShop.getMoney(), pnlPlayShop.getPowder(), plotProjectile.getWind(), plotProjectile.getGravity());
    }

    //Sets the action listenener for the buttons that read "Main Manu"
    public void setMenuButtonListener(ActionListener actMenu) {
        pnlPlayButtons.btnMenu.addActionListener(actMenu);
        pnlPlayShop.pnlShopReturn.btnMainMenu.addActionListener(actMenu);
    }

    //Changes in a specified world
    public void selectWorld(World _world) {
        int nBossHP;
        System.out.println("selectWorld called, setting boss array");
        pnlDrawMovements.setBossArray(_world.getBossArray());
        pnlDrawMovements.setMario(_world.getMario());
        pnlDrawMovements.setDestructableArray(_world.getDestructableArray());
        pnlDrawMovements.setBackground(_world.getBackground());
        plotProjectile.setGravity(_world.getGravity());
        plotProjectile.setWindVariance(_world.getWind());
        for (int i = 0; i < _world.getBossQuan(); i++) {
            nBossHP = (int) (pnlDrawMovements.getLevelHealth() / pnlDrawMovements.getBossQuan());
            System.out.println("*************************************");
            System.out.println("Boss " + (i + 1) + " Health:" + nBossHP);
            pnlDrawMovements.setBossPoints(pnlDrawMovements.getLevelPoints() / pnlDrawMovements.getBossQuan(), i);
            pnlDrawMovements.setBossHealth(nBossHP, i);
            pnlDrawMovements.setBossInitialHealth(nBossHP, i);
            pnlDrawMovements.setBossAlive(true, i);
        }
        for (int i = 0; i < pnlDrawMovements.getDestructableObjQuan(); i++) {
            pnlDrawMovements.setDestructablePoints(pnlDrawMovements.getDestructableObj(i).getInitialHealth(), i);
            pnlDrawMovements.setDestructableHealth(pnlDrawMovements.getDestructableObj(i).getInitialHealth(), i);
            pnlDrawMovements.setDestructableInitialHealth(pnlDrawMovements.getDestructableObj(i).getInitialHealth(), i);
            pnlDrawMovements.setDestructableAlive(true, i);
        }


    }

    public void selectWorld(int _nIndex) {
        selectWorld(arcWorld.get(_nIndex));
    }
}
