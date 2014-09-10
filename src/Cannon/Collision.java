package Cannon;
//Detects collision (if a point is within a given rectangle
public class Collision {

    private int nIncX,  nIncY,  nTargetX,  nTargetY,  nTargetWidth,  nTargetHeight;

    Collision() {
    }

    public void setIncLocation(int _nIncX, int _nIncY) {
        nIncX = _nIncX;
        nIncY = _nIncY;
    }

    public void setWidth(int _nTargetWidth) {
        nTargetWidth = _nTargetWidth;
    }

    public void setHeight(int _nTargetHeight) {
        nTargetHeight = _nTargetHeight;
    }

    public void setTargetLocation(int _nTargetX, int _nTargetY) {
        nTargetX = _nTargetX;
        nTargetY = _nTargetY;
    }

    public boolean isColliding() {
        if (nIncX >= nTargetX && nIncX <= (nTargetX + nTargetWidth)) {
            if (nIncY >= nTargetY && nIncY <= (nTargetY + nTargetHeight)) {
                return true;
            }
        }
        return false;
    }
}
