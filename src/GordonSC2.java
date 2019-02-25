/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        
        //int width = (int)(screenSize.width/1.5);
        //int heigth = (int)(screenSize.height/1.5);
        int width = (int)(screenSize.width);
        int heigth = (int)(screenSize.height);
        frame.setBounds((screenSize.width-width)/2,(screenSize.height-heigth)/2,width, heigth);
        pn.setBounds((screenSize.width-width)/2,(screenSize.height-heigth)/2,width, heigth);
        
        //frame.pack();
        frame.requestFocus();
        frame.setVisible(true);
        
        pn.start();
    }

    public static void main(final String[] args) {
        new GordonSC2();
    }
}
