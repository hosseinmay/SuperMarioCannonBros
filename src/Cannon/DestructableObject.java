/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Cannon;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class DestructableObject {
private BufferedImage bufImgObsticle;
    private int nHealth=1000,nX=700,nY=370, nPoints, nInitialHealth;
    private boolean isAlive = true;
    private String sImgLocation, sSpecial="";
    private Rectangle2D.Double rectBounds;
    DestructableObject(String _sImgLocation, int _nX,int _nY){
        nHealth=100;
        nInitialHealth=nHealth;
        nPoints=100;
        nX=_nX;
        nY=_nY;
        sImgLocation=_sImgLocation;

        try{
            bufImgObsticle = ImageIO.read(new File(sImgLocation));
            rectBounds=new Rectangle2D.Double(nX, nY, bufImgObsticle.getWidth(), bufImgObsticle.getHeight());
            System.out.println("Destructable Object bounds: X "+nX+" Y "+ nY +" Width "+ bufImgObsticle.getWidth()+" Height "+ bufImgObsticle.getHeight());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    DestructableObject(String _sImgLocation, int _nHealth,int _nX,int _nY){
        nHealth=_nHealth;
        nInitialHealth=nHealth;
        nPoints=_nHealth;
        nX=_nX;
        nY=_nY;
        sImgLocation=_sImgLocation;

        try{
            bufImgObsticle = ImageIO.read(new File(sImgLocation));
            rectBounds=new Rectangle2D.Double(nX, nY, bufImgObsticle.getWidth(), bufImgObsticle.getHeight());
            System.out.println("Destructable Object bounds: X "+nX+" Y "+ nY +" Width "+ bufImgObsticle.getWidth()+" Height "+ bufImgObsticle.getHeight());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    DestructableObject(String _sImgLocation, int _nHealth,int _nX,int _nY, String _sSpecial){
        nHealth=_nHealth;
        nInitialHealth=nHealth;
        nPoints=_nHealth;
        nX=_nX;
        nY=_nY;
        sImgLocation=_sImgLocation;
        sSpecial =_sSpecial;
        try{
            bufImgObsticle = ImageIO.read(new File(sImgLocation));
            rectBounds=new Rectangle2D.Double(nX, nY, bufImgObsticle.getWidth(), bufImgObsticle.getHeight());
            System.out.println("Destructable Object bounds: X "+nX+" Y "+ nY +" Width "+ bufImgObsticle.getWidth()+" Height "+ bufImgObsticle.getHeight());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public Rectangle2D getBounds(){
        return rectBounds;
    }
    public BufferedImage getImage(){
        return bufImgObsticle;
    }
    public int getHealth(){
        return nHealth;
    }
    public int getInitialHealth(){
        return nInitialHealth;
    }
    public int getWidth(){
        return bufImgObsticle.getWidth();
    }
    public int getHeight(){
        return bufImgObsticle.getHeight();
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
        Dimension dimBoss=new Dimension(bufImgObsticle.getWidth(),bufImgObsticle.getHeight());
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
    public void setInitialHealth(int _nInitialHealth){
        nInitialHealth = _nInitialHealth;
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
            bufImgObsticle = ImageIO.read(new File(sImgLocation));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
