package Cannon;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
//Contains all information from a world or .env file. Includes boss(es), Destructable Objects, Backgrounds, wind, mario, etc.
public class World {

    private ArrayList<Boss> arcBoss = new ArrayList<Boss>();
    private ArrayList<DestructableObject> arcDestructable = new ArrayList<DestructableObject>();
    private BufferedImage bufImgBg;
    private double dGravity, dWind;
    private String sPath;
    private Mario mario;

    World(ArrayList _boss, BufferedImage _bufImgBg, double _dGravity, double _dWind, String _sPath, Mario _mario) {
        arcBoss = _boss;
        bufImgBg = _bufImgBg;
        dGravity = _dGravity;
        dWind = _dWind;
        sPath = _sPath;
        mario = _mario;
    }

    World(ArrayList _boss, BufferedImage _bufImgBg, double _dGravity, double _dWind, String _sPath, Mario _mario, ArrayList _destructables) {
        arcBoss = _boss;
        bufImgBg = _bufImgBg;
        dGravity = _dGravity;
        dWind = _dWind;
        sPath = _sPath;
        mario = _mario;
        arcDestructable = _destructables;
    }

    public void addBoss(Boss _boss) {
        arcBoss.add(_boss);
    }

    public void addDestructable(DestructableObject _dest) {
        arcDestructable.add(_dest);
    }

    public Boss getBoss(int _nBossIndex) {
        return arcBoss.get(_nBossIndex);
    }
    public ArrayList getBossArray() {
        return arcBoss;
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

    public BufferedImage getBackground() {
        return bufImgBg;
    }

    public ArrayList getDestructableArray() {
        return arcDestructable;
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

    public double getGravity() {
        return dGravity;
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

    public String getPath() {
        return sPath;
    }

    public double getWind() {
        return dWind;
    }

    public void setBackground(String sFile) {
        try {
            bufImgBg = ImageIO.read(new File(sFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBackground(BufferedImage _Background) {
        bufImgBg = _Background;
    }

    public void setBoss(Boss _boss) {
        arcBoss.clear();
        arcBoss.add(_boss);
    }

    public void setDestructable(DestructableObject _destruct) {
        System.out.println(_destruct.getImageName() + " Set");
        arcDestructable.clear();
        addDestructable(_destruct);

    }

    public void setDestructableHealth(int _nHealth, int _nIndex) {
        System.out.println("Destructable health Set to: " + _nHealth);
        arcDestructable.get(_nIndex).setHealth(_nHealth);
    }

    public void setDestructableAlive(boolean _isBossAlive, int _nIndex) {
        arcDestructable.get(_nIndex).setAlive(_isBossAlive);
    }

    public void setDestructablePoints(int _nPoints, int _nIndex) {
        arcDestructable.get(_nIndex).setPoints(_nPoints);
    }

    public void setGravity(double _dGravity) {
        dGravity = _dGravity;
    }
//    public void setPath(String _sPath) {
//        sPath= _sPath;
//    }

    public void setMario(Mario _mario) {
        mario = _mario;
    }

    public void setMarioX(int _nX) {
        mario.setX(_nX);
    }

    public void setMarioY(int _nY) {
        mario.setY(_nY);
    }

    public void setMarioImage(BufferedImage _bufImg) {
        mario.setImage(_bufImg);
    }

    public void setMarioImage(String sMarioLocation) {
        mario.setImage(sMarioLocation);
    }

    public void setWind(double _dWind) {
        dWind = _dWind;
    }
}
