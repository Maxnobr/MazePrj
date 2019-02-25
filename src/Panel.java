/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JPanel;

/**
 *
 * @author maxno
 */
public class Panel extends JPanel implements Runnable{

    int frames = 0;
    private boolean running = false;
    private boolean isPaused = false;
    private Thread loop;
    
    final int TARGET_FPS = 10;
    
    long lastLoopTime = System.nanoTime();
    long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    long lastFpsTime = 0;
    
    Room r;
    MazeMaker mm;
    MazeSolver ms;
    MyStack maps;
    String fileName = "src\\maps.txt";
    
    double timer = 0;
    double pause = 10000000;

    public void start(){   
        readMaps();
        restart();
        if(!running && loop == null){
            running = true;
            loop = new Thread(this);
            loop.start();
        }
    }
    
    public void readMaps(){
        maps = new MyStack();
        try{
            int counter = 0;
            int lineW = 0;
            String[] map = null;
            for(String s : Files.readAllLines(Paths.get(fileName))){
                if(counter == 0){
                    
                    counter = Integer.parseInt(s.split(" ")[0]);
                    if(counter < 3 || Integer.parseInt(s.split(" ")[1]) < 4)
                        throw new NullPointerException();
                    
                    map = new String[counter+1];
                    lineW = 0;
                }
                else{
                    counter--;
                    lineW++;
                    if(counter == 0)
                        maps.push(map);
                }
                map[lineW] = s;
            }
        }
        catch(Exception e){
        System.out.println("Shit, file not read!");
        }
    }
    
    public void restart(){
        if(maps != null && maps.peek() != null)
            r = new Room((String[])maps.pop());
        else{
            r = new Room();
            mm = new MazeMaker(r);
        }
        ms = new MazeSolver(r);
    }
    
    public void run(){   
        while(running){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength ;/// ((double)OPTIMAL_TIME);

            lastFpsTime += updateLength;
            if(lastFpsTime >= 1000000000){
                lastFpsTime = 0;
            }

            if(!isPaused){
                this.GameUpdate(delta);

                this.repaint();
            }

            try{
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            }
            catch(Exception e){}
        }
    }
    
    @Override
    public void paint(Graphics g){
        if(r != null)
            r.paint(g,this.getBounds());
    }
    
    public void GameUpdate(double delta){
        if(mm != null && !mm.done)
            mm.GameUpdate(delta);
        else if(ms != null)
            ms.GameUpdate(delta);
        if( ms != null && ms.done){
            if(timer > pause){
                timer = 0;
                restart();
            }
            else
                timer += delta/1000;
        }
    }
}
