package Cannon;

//Simply creates the FraWindow
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                FraWindow fraMain = new FraWindow();
            }
        });

    }
}
