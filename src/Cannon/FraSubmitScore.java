/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Cannon;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

//Seperate popup window for score submitting
public class FraSubmitScore extends JFrame {
    JLabel lblName = new JLabel("Name: ");
    JTextField txtName = new JTextField("YourName");
    JButton btnSubmit = new JButton("Submit Score");
    public FraSubmitScore() {
        this.setSize(300, 100);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Enter Name To Submit Score");
        this.setAlwaysOnTop(true);
        this.setVisible(false);
        this.setLayout(new GridLayout(3,1));
        this.add(lblName);
        this.add(txtName);
        this.add(btnSubmit);
    }
}
