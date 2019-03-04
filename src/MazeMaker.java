/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;

/**
 *
 * @author maxno
 */
public class MazeMaker {
    
    private Room r;
    private Point extraTail;
    private Point tail;
    private Point head;
    private boolean isWalking = true;
    public boolean done = false;
    
    private double turnsPerCycle = 1;
    private double incrementer = 1000000;
    private double timer = 0;
    
    public MazeMaker(Room r){
        this.r = r;
        r.cells[0][0].isWorm = true;
        head = new Point(0,0);
        tail = head;
        extraTail = head;
    }
    
    private void hunt(){
        Point p;

        for(int y = 0; y< r.cells.length && head == null;y++)
            for(int x = 0; x< r.cells[y].length && head == null;x++){   
                p = new Point(x,y);
                if(!r.cells[y][x].isSetUp && r.getNeighbour(p, true) != null){
                extraTail = r.getNeighbour(p, true);
                tail = extraTail;
                head = p;
                isWalking = true;
                if(tail.x > head.x)
                    r.cells[tail.y][tail.x].sides[0] = false;
                else if(tail.y > head.y)
                    r.cells[tail.y][tail.x].sides[1] = false;
                else if(tail.x < head.x)
                    r.cells[tail.y][tail.x].sides[2] = false;
                else if(tail.y < head.y)
                    r.cells[tail.y][tail.x].sides[3] = false;
                }
            }
        if(head == null){
            done = true;
            r.cells[0][0].sides[0] = false;
                if(Math.random()>.5){
                    int x = (int)((r.cells[0].length +Math.random()*r.cells[0].length)/2);
                    r.cells[r.cells.length-1][x].isExit = true;
                    r.cells[r.cells.length-1][x].sides[3] = false;
                }
                else{
                    int y = (int)((r.cells.length+Math.random()*r.cells.length)/2);
                    r.cells[y][r.cells[0].length-1].isExit = true;
                    r.cells[y][r.cells[0].length-1].sides[2] = false;
                }
        }
    }
    
    private void walk(){
        if(!r.cells[head.y][head.x].isSetUp){
            Point worm = r.getNeighbour(head,false);
            //System.out.println("Walk: "+worm);
            if(worm != null){
                extraTail = tail;
                tail = head;
                head = worm;
                r.carveCell(extraTail,tail, head);
                r.cells[head.y][head.x].isWorm = true;
            }
            else{
                r.cells[head.y][head.x].isWorm = false;
                r.carveOut(tail,head);
                isWalking = false;
                head = null;
            }
            r.cells[tail.y][tail.x].isWorm = false;
        }
    }
    
    public void GameUpdate(double delta){
        if(timer < incrementer)
            timer += delta;
        else{
            timer = 0;
            turnsPerCycle *=1.05;
        }
        for(int i = 0;i<turnsPerCycle;i++){
            if(isWalking)
                walk();
            else if(!done)
                hunt();
        }
    }
    
}
