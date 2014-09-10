

package Cannon;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
//Contains all information for a specific boss such as Image or health
public class Boss {
    private BufferedImage bufImgBoss;
    private int nHealth=1000,nX=700,nY=370, nPoints, nInitialHealth;
    private boolean isAlive = true;
    private String sImgLocation;
    private Rectangle2D.Double rectBounds;
    Boss(String _sImgLocation, int _nHealth,int _nPoints,int _nX,int _nY){
        nInitialHealth=_nHealth;
        nHealth=_nHealth;
        nPoints=_nPoints;
        nX=_nX;
        nY=_nY;
        sImgLocation=_sImgLocation;

        try{
            bufImgBoss = ImageIO.read(new File(sImgLocation));
            rectBounds=new Rectangle2D.Double(nX, nY, bufImgBoss.getWidth(), bufImgBoss.getHeight());
            System.out.println("Boss bounds: X "+nX+" Y "+ nY +" Width "+ bufImgBoss.getWidth()+" Height "+ bufImgBoss.getHeight());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public Rectangle2D getBounds(){
        return rectBounds;
    }
    public BufferedImage getImage(){
        return bufImgBoss;
    }
    public int getInitialHealth(){
        return nInitialHealth;
    }
    public int getHealth(){
        return nHealth;
    }
    public int getWidth(){
        return bufImgBoss.getWidth();
    }
    public int getHeight(){
        return bufImgBoss.getHeight();
    }
    public String getImageName(){
        return sImgLocation;
    }

    public int getPoints(){
        return nPoints;
    }
    public int getX(){
        return nX;
    }
    public int getY(){
        return nY;
    }
    public Dimension getDimentions(){
        Dimension dimBoss=new Dimension(bufImgBoss.getWidth(),bufImgBoss.getHeight());
        return dimBoss;
    }
    public boolean isAlive(){
        return isAlive;
    }
    public void removeHealth(int _nHealth){
        nHealth -= _nHealth;
    }
    public void setAlive(boolean _isAlive){
        isAlive = _isAlive;
    }
    public void setHealth(int _nHealth){
        nHealth = _nHealth;
    }
    public void setInitialHealth(int _nHealth){
        nInitialHealth=_nHealth;
    }
    public void setPoints(int _nPoints){
        nPoints = _nPoints;
    }
    public void setX(int _nX){
        nX = _nX;
    }
    public void setY(int _nY){
        nY = _nY;
    }
    public void setImage(String sImgLocation){
        try{
            bufImgBoss = ImageIO.read(new File(sImgLocation));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
