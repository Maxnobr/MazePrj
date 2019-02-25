/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author maxno
 */
public class Cell {
    
    boolean point = false;
    float pointSize = 1f;
    
    boolean isSetUp = false;
    boolean isWorm = false;
    boolean isExit = false;
    boolean isBlocked = false;
    
    boolean[] sides = {false,false,false,false};
    float sideSize = 0.1f;
    boolean[] paths = {false,false,false,false};
    float pathSize = 0.3f;
    
    public void sidesUp(){
        for(int i = 0;i<4;i++)
            sides[i] = true;
    }
    
    public boolean hasPath(){
        for(int i = 0;i<4;i++)
            if(paths[i] == true)
                return true;
        return false;
    }
    
    public void paint(Graphics g,Rectangle rec){
        
        int size = 1;
        
        if(isWorm){
            g.setColor(Color.green);
            g.fillRect(rec.x, rec.y, rec.width, rec.height);
        }
        else if(!isSetUp){
            //background
            g.setColor(Color.lightGray);
            g.fillRect(rec.x, rec.y, rec.width, rec.height);
        }
        else{
            
            //background
            if(isBlocked)
                g.setColor(Color.gray);
            else
                g.setColor(Color.white);
            g.fillRect(rec.x, rec.y, rec.width, rec.height);

            //paths
            g.setColor(Color.red);
            size = Math.round(rec.width * pathSize);
            //g.fillRect(rec.x, rec.y+(rec.height-size)/2, rec.width/2, size);

            for(int x = 0;x < 4;x++)
                if(paths[x])
                    switch(x){
                        case 0:
                            g.fillRect(rec.x, rec.y+(rec.height-size)/2, (rec.width+size)/2, size);
                            break;
                        case 1:
                            g.fillRect(rec.x+(rec.width-size)/2, rec.y, size, (rec.height+size)/2);
                            break;
                        case 2:
                            g.fillRect(rec.x+(rec.width-size)/2, rec.y+(rec.height-size)/2, (int)Math.ceil((rec.width+size)/2f), size);
                            break;
                        default:
                            g.fillRect(rec.x+(rec.width-size)/2, rec.y+(rec.height-size)/2, size, (int)Math.ceil((rec.height+size)/2f));
                    }

            //sides
            g.setColor(Color.darkGray);
            size = Math.round(rec.width * sideSize);
            for(int x = 0;x < 4;x++)
                if(sides[x])                
                    switch(x){
                        case 0:
                            g.fillRect(rec.x, rec.y, size, rec.height);
                            break;
                        case 1:
                            g.fillRect(rec.x, rec.y, rec.width, size);
                            break;
                        case 2:
                            g.fillRect(rec.x+rec.width-size, rec.y, size, rec.height);
                            break;
                        default:
                            g.fillRect(rec.x, rec.y+rec.height-size, rec.width, size);
                    }
            //ball
            if(point){
                g.setColor(Color.red);
                size = Math.round(rec.width * pointSize);
                g.fillOval(rec.x+(rec.width-size)/2, rec.y+(rec.width-size)/2, size, size);
            }
        }
    }
}
