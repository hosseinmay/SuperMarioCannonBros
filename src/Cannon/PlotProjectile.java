/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cannon;

import java.awt.geom.Point2D;

//Physics engine that models the projectile movements based on wind etc
public class PlotProjectile {

    private double dAngle = 35.0 * 0.0174532925;
    private double dVelocity = 40.0;
    private double dGravity = -9.8;
    private double dWind = 10;
    private double dWindVariance=60;
    private double dVox = 32.76608178757428, dVoy = 22.943057431170615, dTime = 2, dHeight, dLength;

    public Point2D getLocation(double _dTime) {
        Point2D ptPoint = new Point2D.Double();
        dTime = _dTime;
        dHeight = (dVoy * dTime) + ((dGravity * (Math.pow(dTime, 2))) * 0.5);
        dLength = (dVox-dWind) * dTime;
        ptPoint.setLocation(dLength, dHeight);
        return ptPoint;

    }

    public double getVelocity() {
        return dVelocity;
    }

    public double getAngle() {
        return dAngle;
    }

    public double getGravity() {
        return dGravity;
    }
    public double getWind() {
        return 0-dWind;
    }
    public void randomizeWind(){
        dWind= Math.random()*dWindVariance-dWindVariance/2;
    }
    public void setAngle(double _dAngle) {
        dAngle = _dAngle;
        dVox = dVelocity * (Math.cos(dAngle));
        dVoy = dVelocity * (Math.sin(dAngle));
    }

    public void setVelocity(double _dVelocity) {
        dVelocity = _dVelocity;
        dVox = dVelocity * (Math.cos(dAngle));
        dVoy = dVelocity * (Math.sin(dAngle));
    }
    public void setWindVariance(double _dWindVariance) {
        dWindVariance = _dWindVariance;
    }
    public void setWind(double _dWind) {
        dWind = _dWind;
    }
    public void setGravity(double _dGravity) {
        dGravity = _dGravity;
    }
}
