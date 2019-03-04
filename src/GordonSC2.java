/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * version = 1
 */


import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class GordonSC2 extends JFrame {
    
    public GordonSC2() {
        
        JFrame frame = new JFrame ("Maze Runner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel pn = new Panel();
        
        frame.getContentPane().add(pn);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(0,0,screenSize.width, screenSize.height);
        pn.setBounds(frame.getBounds());

        frame.setVisible(true);
        pn.start();
    }

    public static void main(final String[] args) {
        new GordonSC2();
    }
}
