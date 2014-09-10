
package Cannon;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
//Handles all the information for drawing the projectile, and the information such as power
public class Projectile {
    private BufferedImage bufImgProjectile;
    private int nPower=100;
    Projectile(String sImgLocation){
        try{
            bufImgProjectile = ImageIO.read(new File(sImgLocation));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public BufferedImage getImage(){
        return bufImgProjectile;
    }
    public int getPower(){
        return nPower;
    }
    public int getWidth(){
        return bufImgProjectile.getWidth();
    }
    public int getHeight(){
        return bufImgProjectile.getHeight();
    }
    public void setPower(int _dPower){
        nPower = _dPower;
    }
    public void setImage(String sImgLocation){
        try{
            bufImgProjectile = ImageIO.read(new File(sImgLocation));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
