
package Cannon;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
//Contains all info for mario (X,Y, Image)
public class Mario {
private BufferedImage bufImgBoss;
    private int nX=700,nY=370;
    private String sImgLocation;
    Mario(String _sImgLocation,int _nX,int _nY){
        nX=_nX;
        nY=_nY;
        sImgLocation=_sImgLocation;
        try{
            bufImgBoss = ImageIO.read(new File(sImgLocation));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    Mario(BufferedImage buffImgMario,int _nX,int _nY){
        nX=_nX;
        nY=_nY;
        bufImgBoss = buffImgMario;

    }
    public BufferedImage getImage(){
        return bufImgBoss;
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
    public void setImage(BufferedImage _bufImg){
        try{
            bufImgBoss = _bufImg;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
