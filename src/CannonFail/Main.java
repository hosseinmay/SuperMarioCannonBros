package CannonFail;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.ReplicateScaleFilter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Main {

    public static void main(String[] args) {
        FraMainWindow frame = new FraMainWindow();

    }
    static class FraMainWindow extends JFrame {

    DrawGame pnlCannon = new DrawGame();
    ButtonPannel pnlButtons = new ButtonPannel();
    PlotProjectile trajectory = new PlotProjectile();

    public FraMainWindow() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Super Mario Cannon Bro's");
        this.setSize(900, 550);
        this.setLayout(new BorderLayout());
        this.add(pnlCannon, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);
        MouseMovement mouseMove = new MouseMovement();
        MouseAction mouseClick = new MouseAction();
        pnlCannon.addMouseMotionListener(mouseMove);
        pnlCannon.addMouseListener(mouseClick);
        FireButton actnFire = new FireButton();
        pnlButtons.btnFire.addActionListener(actnFire);
        this.setVisible(true);
    }

    public class DrawGame extends JPanel {

        Rectangle.Float rectCannon = new Rectangle.Float(30, 450, 50, 10);
        Image imgBall = new ImageIcon("ball.png").getImage();
        BufferedImage bufImgBall;
        BufferedImage bufImgBallScale;
        double dAngle = 0;
        int nX, nY;
        boolean isFired = false;

        DrawGame() {
            try {
                bufImgBallScale = ImageIO.read(new File("ball.png"));
            } catch (Exception e) {}
            bufImgBall = new BufferedImage(20, 10, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImgBall.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance((double) 20 / bufImgBallScale.getWidth(),(double) 10 / bufImgBallScale.getHeight());
            g.drawRenderedImage(bufImgBallScale, at); //http://www.rgagnon.com/javadetails/java-0243.html
        }

        public void addCannonBall() {
            isFired = true;
            repaint();
        }

        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);//Anti Aliasing from http://www.java2s.com/Code/Java/2D-Graphics-GUI/Arotatingandscalingrectangle.htm
            if (isFired) {
                
            }//Notice placement: Above rotate isn't rotated, below is.
            g2d.rotate(0 - dAngle, rectCannon.getX(), rectCannon.getY() + 5);
            g2d.drawImage(bufImgBallScale, 0, 0, null);
            g2d.fill(rectCannon);
        }
    }
    public class ButtonPannel extends JPanel {
        JButton btnFire = new JButton("Fire!");
        ButtonPannel() {
            this.add(btnFire);
        }
    }
    public class FireButton implements ActionListener {
        double dTime = 0;
        public void actionPerformed(ActionEvent e) {
            pnlCannon.addCannonBall();
            AbstractAction updateLocation = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {

                    dTime += 0.01;
                    trajectory.setAngle(pnlCannon.dAngle);
                    Point2D ptPoint = trajectory.getLocation(dTime);
                    if(ptPoint.getX()*5<0||ptPoint.getY()*5>900) return;
                    pnlCannon.nX = (int) (ptPoint.getX() * 5);
                    pnlCannon.nY = (int) (ptPoint.getY() * 5);
                    pnlCannon.repaint();
                }
            };
            Timer t=new Timer(10, updateLocation);
            t.start();
        }
    }

    public class PlotProjectile {

        double dAngle = 35.0 * 0.0174532925;
        double dVelocity = 40.0;
        double dGravity = -9.8;
        double dVox = 32.76608178757428, dVoy = 22.943057431170615, dTime = 2, dHeight, dLength;

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

        public void setGravity(double _dGravity) {
            dGravity = _dGravity;
        }

        public Point2D getLocation(double _dTime) {
            Point2D ptPoint = new Point2D.Double();
            dTime = _dTime;
            dHeight = (dVoy * dTime) + ((dGravity * (Math.pow(dTime, 2))) * 0.5);
            dLength = dVox * dTime;
            ptPoint.setLocation(dLength, dHeight);
            return ptPoint;

        }
    }

    public class MouseMovement implements MouseMotionListener {

        public void mouseDragged(MouseEvent e) {

            double dBase, dHeight, dAngle;
            dBase = e.getX() - pnlCannon.rectCannon.getX();
            dHeight = pnlCannon.rectCannon.getY() - 5 - e.getY() + 10;
            dAngle = Math.atan2(dHeight, dBase);
            pnlCannon.dAngle = dAngle;
            pnlCannon.repaint();
        }//http://download.oracle.com/javase/tutorial/uiswing/examples/events/MouseMotionEventDemoProject/src/events/MouseMotionEventDemo.java

        public void mouseMoved(MouseEvent e) {
        }
    }

    public class MouseAction implements MouseListener {

        public void mousePressed(MouseEvent e) {
            double dBase, dHeight, dAngle;
            dBase = e.getX() - pnlCannon.rectCannon.getX();
            dHeight = pnlCannon.rectCannon.getY() - 5 - e.getY() + 10;
            dAngle = Math.atan2(dHeight, dBase);
            pnlCannon.dAngle = dAngle;
            pnlCannon.repaint();
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
}
}

