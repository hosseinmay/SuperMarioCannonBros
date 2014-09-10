package Cannon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.Timer;
//Draws all actions that happen within the game. Locatedin the center of panel play
public class PnlPlayDrawMovements extends JPanel {

    private ArrayList<Boss> arcBoss = new ArrayList<Boss>();
    private ArrayList<DestructableObject> arcDestructable = new ArrayList<DestructableObject>();
    private Mario mario;
    private Projectile projectile = new Projectile("Images/bulletbill.png");
    private Rectangle.Float rectCannon = new Rectangle.Float(50, 450, 50, 10);
    private Rectangle.Float rectBox = new Rectangle.Float(5, 5, 90, 35);
    private ArrayList<Rectangle.Double> arrHealthBar = new ArrayList<Rectangle.Double>();
    //private Rectangle.Float rectHealth = new Rectangle.Float(-200, -200, 100, 10);
    private BufferedImage bufImgBG,  bufImgExplosion;
    private double dCannonAngle = 0,  dBallAngle = 0;
    private boolean isFired = false,  isExploded = false;
    private int nBallX = 0,  nBallY = 0,  nOldBallX = 0,  nOldBallY = 0,  nLvlPoints = 1000,  nLvlHealth = 1000,  nLevel = 1;
    private String sScore = "Score = 0";
    private Timer timeExplode;

    PnlPlayDrawMovements() {
        Dimension dimSize = new Dimension();
        dimSize.setSize(900, 500);
        this.setPreferredSize(dimSize);
        try {

            bufImgExplosion = ImageIO.read(new File("Images/Explosion.png"));
        } catch (Exception e) {
        }
    //bufImgBall = new BufferedImage(20, 10, BufferedImage.TYPE_INT_RGB);
    //Graphics2D g = bufImgBall.createGraphics();
    //AffineTransform at = AffineTransform.getScaleInstance((double) 20 / bufImgBallScale.getWidth(), (double) 10 / bufImgBallScale.getHeight());
    //g.drawRenderedImage(bufImgBallScale, at); //http://www.rgagnon.com/javadetails/java-0243.html
    }

    public void paintComponent(Graphics g) {
        //Image imgPreshow = createImage(1200, 800); //Moeed
        //Graphics g = imgPreshow.getGraphics();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);//Anti Aliasing from http://www.java2s.com/Code/Java/2D-Graphics-GUI/Arotatingandscalingrectangle.htm
        g2d.drawImage(bufImgBG, 0, 0, null);
        g2d.setColor(Color.WHITE);
        g2d.fill(rectBox);
        g2d.setColor(Color.BLACK);
        g2d.drawString(sScore, 10, 20);
        g2d.drawString("Level: " + nLevel, 10, 34);
        //Draw Bosses
        for (int i = 0; i < arcBoss.size(); i++) {
            if (arcBoss.get(i).isAlive()) {
                g2d.drawImage(arcBoss.get(i).getImage(), arcBoss.get(i).getX(), arcBoss.get(i).getY(), null);

                g2d.setColor(Color.GREEN);
                g2d.fill(arrHealthBar.get(i));
                

            }
        }
        //Draw destructables, basicly bosses without the health bar
        for (int i = 0; i < arcDestructable.size(); i++) {
            if (arcDestructable.get(i).isAlive()) {
                g2d.drawImage(arcDestructable.get(i).getImage(), arcDestructable.get(i).getX(), arcDestructable.get(i).getY(), null);
            }
        }
        if (isFired) {
            //System.out.println("X: " + nBallX + " Y: " + nBallY);
            g2d.drawImage(projectile.getImage(), nBallX, nBallY, null);
        }
        if (isExploded) {
            g2d.drawImage(bufImgExplosion, nBallX - 30, nBallY - 25, null);
        }//Notice placement: Above rotate isn't rotated, below is.
        g2d.setColor(Color.RED);
        g2d.drawImage(mario.getImage(), mario.getX(), mario.getY(), null);
        g2d.rotate(0 - dCannonAngle, rectCannon.getX(), rectCannon.getY() + 5);
        g2d.fill(rectCannon);


    //g1.drawImage(imgPreshow, 0, 0, null);
    }

    public void addBoss(Boss _boss) {
        System.out.println(_boss.getImageName() + " added.");
        arcBoss.add(_boss);
        updateHealthBar();
    }

    public void addDestructable(DestructableObject _destruct) {
        System.out.println(_destruct.getImageName() + " added.");
        arcDestructable.add(_destruct);
    }

    public void addLevel() {
        nLevel += 1;
        repaint();
    }

    public int getBallX() {
        return nBallX;
    }

    public int getBallY() {
        return nBallY;
    }

    public BufferedImage getBackgroundImg() {
        return bufImgBG;
    }

    public Boss getBoss(int _nBossIndex) {
        return arcBoss.get(_nBossIndex);
    }

    public Rectangle2D getBossBounds(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).getBounds();
    }

    public int getBossX(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).getX();
    }

    public int getBossY(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).getY();
    }

    public int getBossWidth(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).getWidth();
    }

    public int getBossHeight(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).getHeight();
    }

    public int getBossHealth(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).getHealth();
    }

    public int getBossPoints(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).getPoints();
    }

    public int getBossQuan() {
        return arcBoss.size();
    }

    public Point2D getCannonLocation() {
        Point2D ptCannon = new Point2D.Double();
        ptCannon.setLocation(rectCannon.getX(), rectCannon.getY());
        return ptCannon;
    }

    public int getCannonX() {
        return (int) rectCannon.getX();
    }

    public int getCannonY() {
        return (int) rectCannon.getY();
    }

    public double getCannonAngle() {
        return dCannonAngle;
    }

    public DestructableObject getDestructableObj(int _nIndex) {
        return arcDestructable.get(_nIndex);
    }

    public Rectangle2D getDestructableObjBounds(int _nIndex) {
        return arcDestructable.get(_nIndex).getBounds();
    }

    public int getDestructableObjX(int _nIndex) {
        return arcDestructable.get(_nIndex).getX();
    }

    public int getDestructableObjY(int _nIndex) {
        return arcDestructable.get(_nIndex).getY();
    }

    public int getDestructableObjWidth(int _nIndex) {
        return arcDestructable.get(_nIndex).getWidth();
    }

    public int getDestructableObjHeight(int _nIndex) {
        return arcDestructable.get(_nIndex).getHeight();
    }

    public int getDestructableObjHealth(int _nIndex) {
        return arcDestructable.get(_nIndex).getHealth();
    }

    public int getDestructableObjPoints(int _nIndex) {
        return arcDestructable.get(_nIndex).getPoints();
    }

    public int getDestructableObjQuan() {
        return arcDestructable.size();
    }

    public int getLevel() {
        return nLevel;
    }

    public int getLevelHealth() {
        return nLvlHealth;
    }

    public int getLevelPoints() {
        return nLvlPoints;
    }

    public Mario getMario() {
        return mario;
    }

    public int getMarioX() {
        return mario.getX();
    }

    public int getMarioY() {
        return mario.getY();
    }

    public int getMarioWidth() {
        return mario.getWidth();
    }

    public int getMarioHeight() {
        return mario.getHeight();
    }

    public int getProjectilePower() {
        return projectile.getPower();
    }

    public int getProjectileHeight() {
        return projectile.getHeight();
    }

    public int getProjectileWidth() {
        return projectile.getWidth();
    }

    public boolean isBossAlive(int _nBossIndex) {
        return arcBoss.get(_nBossIndex).isAlive();
    }

    public boolean isDestructableAlive(int _nIndex) {
        return arcDestructable.get(_nIndex).isAlive();
    }

    public boolean isAnyBossAlive() {
        boolean isAnyAlive = false;
        for (int i = 0; i < arcBoss.size(); i++) {
            if (arcBoss.get(i).isAlive()) {
                isAnyAlive = true;
            }
        }
        return isAnyAlive;
    }

    public boolean isAnyDestructableAlive() {
        boolean isAnyAlive = false;
        for (int i = 0; i < arcDestructable.size(); i++) {
            if (arcDestructable.get(i).isAlive()) {
                isAnyAlive = true;
            }
        }
        return isAnyAlive;
    }

    public boolean isFired() {
        return isFired;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void resetProjectile() {
        projectile = new Projectile("Images/bulletbill.png");
    }

    public void removeBossHealth(int nHealth, int _nBossIndex) {
        arcBoss.get(_nBossIndex).removeHealth(nHealth);
        updateHealthBar();
    }

    public void removeDestructableHealth(int nHealth, int _nIndex) {
        arcDestructable.get(_nIndex).removeHealth(nHealth);
    }

    public void setBallLocation(Point2D ptLocation) {
        double dBase, dHeight, dAngle;
        if (isFired) {
            nOldBallX = nBallX;
            nOldBallY = nBallY;
            nBallX = (int) (ptLocation.getX()) + (int) rectCannon.getX();
            nBallY = (int) (0 - (ptLocation.getY())) + (int) rectCannon.getY();
            if (nBallX > 1000 || nBallX < -100) {
                isFired = false;
                repaint();
                return;
            }
            dBase = nBallX - nOldBallX;
            dHeight = nOldBallY - nBallY;
            dBallAngle = Math.atan2(dHeight, dBase);
            //repaint();
            if (nOldBallX < nBallX) {
                repaint(nOldBallX, nBallY - Math.abs(nOldBallY - nBallY), projectile.getWidth() + Math.abs(nBallX - nOldBallX), projectile.getHeight() + Math.abs(nBallY - nOldBallY) + 10);
            } else {
                repaint(nBallX, nBallY - Math.abs(nOldBallY - nBallY), projectile.getWidth() + Math.abs(nOldBallX - nBallX), projectile.getHeight() + Math.abs(nBallY - nOldBallY) + 10);
            }
        }
    }

    public void setBackground(String sFile) {
        try {
            bufImgBG = ImageIO.read(new File(sFile));
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBackground(BufferedImage _Background) {
        bufImgBG = _Background;
    }

    public void setBoss(Boss _boss) {
        System.out.println(_boss.getImageName() + " Set");
        arcBoss.clear();
        arrHealthBar.clear();
        addBoss(_boss);

    }

    public void setBossArray(ArrayList _arcBoss) {
        System.out.println("Array of Bosses added.");
        arcBoss = _arcBoss;
        arrHealthBar.clear();
        updateHealthBar();
    }

    public void setBossHealth(int _nHealth, int _nBossIndex) {
        System.out.println("Boss health Set to: " + _nHealth);
        arcBoss.get(_nBossIndex).setHealth(_nHealth);
        updateHealthBar();
    }
    public void setBossInitialHealth(int _nInitHealth, int _nBossIndex) {
        System.out.println("Boss initial health Set to: " + _nInitHealth);
        arcBoss.get(_nBossIndex).setInitialHealth(_nInitHealth);
        updateHealthBar();
    }
    public void setBossAlive(boolean _isBossAlive, int _nBossIndex) {
        arcBoss.get(_nBossIndex).setAlive(_isBossAlive);
    }

    public void setBossPoints(int _nPoints, int _nBossIndex) {
        arcBoss.get(_nBossIndex).setPoints(_nPoints);
    }

    public void setCannonAngle(double _dCannonAngle) {
        dCannonAngle = _dCannonAngle;
    }

    public void setCannonX(int _nCannonX) {
        rectCannon.setRect(_nCannonX, rectCannon.getY(), rectCannon.getWidth(), rectCannon.getHeight());
    }

    public void setCannonY(int _nCannonY) {
        rectCannon.setRect(_nCannonY, rectCannon.getY(), rectCannon.getWidth(), rectCannon.getHeight());
    }

    public void setDestructable(DestructableObject _destruct) {
        System.out.println(_destruct.getImageName() + " Set");
        arcDestructable.clear();
        addDestructable(_destruct);

    }

    public void setDestructableArray(ArrayList _arcDestructable) {
        System.out.println("Array of Destructables added.");
        arcDestructable = _arcDestructable;
    }

    public void setDestructableHealth(int _nHealth, int _nIndex) {
        System.out.println("Destructable health Set to: " + _nHealth);
        arcDestructable.get(_nIndex).setHealth(_nHealth);
    }
    public void setDestructableInitialHealth(int _nInitialHealth, int _nIndex) {
        System.out.println("Destructable initial health Set to: " + _nInitialHealth);
        arcDestructable.get(_nIndex).setInitialHealth(_nInitialHealth);
        updateHealthBar();
    }

    public void setDestructableAlive(boolean _isBossAlive, int _nIndex) {
        arcDestructable.get(_nIndex).setAlive(_isBossAlive);
    }

    public void setDestructablePoints(int _nPoints, int _nIndex) {
        arcDestructable.get(_nIndex).setPoints(_nPoints);
    }

    public void setExploded(boolean _isExploded) {
        isExploded = _isExploded;
        repaint();
    }

    public void setFired(boolean _isFired) {
        isFired = _isFired;
    }

    public void setLevel(int _nLevel) {
        nLevel = _nLevel;
    }

    public void setLevelHealth(int _nLvlHealth) {
        nLvlHealth = _nLvlHealth;
    }

    public void setLevelPoints(int _nLvlPoints) {
        nLvlPoints = _nLvlPoints;
    }

    public void setMario(Mario _mario) {
        System.out.println("Mario added");
        mario = _mario;
        rectCannon.setRect(mario.getX() + mario.getWidth(), mario.getY() + mario.getHeight() / 2, rectCannon.getWidth(), rectCannon.getHeight());
    }

    public void setMarioX(int _nX) {
        mario.setX(_nX);
        rectCannon.setRect(mario.getX() + mario.getWidth(), mario.getY() + mario.getHeight() / 2, rectCannon.getWidth(), rectCannon.getHeight());
    }

    public void setMarioY(int _nY) {
        System.out.println("Mario added");
        mario.setY(_nY);
        rectCannon.setRect(mario.getX() + mario.getWidth(), mario.getY() + mario.getHeight() / 2, rectCannon.getWidth(), rectCannon.getHeight());
    }

    public void setMarioImage(BufferedImage _bufImg) {
        System.out.println("Mario added");
        mario.setImage(_bufImg);
        rectCannon.setRect(mario.getX() + mario.getWidth(), mario.getY() + mario.getHeight() / 2, rectCannon.getWidth(), rectCannon.getHeight());
    }

    public void setMarioImage(String sMarioLocation) {
        mario.setImage(sMarioLocation);
        rectCannon.setRect(mario.getX() + mario.getWidth(), mario.getY() + mario.getHeight() / 2, rectCannon.getWidth(), rectCannon.getHeight());
    }

    public void setProjectile(Projectile _projectile) {
        projectile = _projectile;
    }

    public void setScore(int _nScore) {
        sScore = "Score = " + _nScore;
        repaint();
    }

//    public void setWorld(World _world) {
//        arcBoss.set(0, _world.getBoss(0));
//        bufImgBG = _world.getBackground();
//        updateHealthBar(0);
//    }
    public void updateHealthBar() {
        arrHealthBar.clear();
        System.out.println("arcBoss is " + arcBoss.size() + " long.");
        for (int i = 0; i < arcBoss.size(); i++) {
            System.out.println(arcBoss.get(i).getImageName() + " Stats: "+"Health :" + arcBoss.get(i).getHealth() + " Initial:" + arcBoss.get(i).getInitialHealth() + " Level:" + nLvlHealth);
            double dPercent = (double) ((double) arcBoss.get(i).getHealth() / (double) arcBoss.get(i).getInitialHealth());

            double dWidth = arcBoss.get(i).getWidth() * dPercent;
            arrHealthBar.add(new Rectangle2D.Double(arcBoss.get(i).getX(), arcBoss.get(i).getY() - 15, dWidth, 10));
        }
        repaint();
    }
}
