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
public class MazeSolver {
    
    Room r;
    MyStack s;
    boolean done = false;
    boolean firstStep = true;
    boolean backTracking = false;
    int deadEnds = 0;
    Point head;
    Point tail;
    
    private double turnsPerCycle = 1;
    private double incrementer = 1000000;
    private double timer = 0;
    
    public MazeSolver(Room r){
        this.r = r;
        s = new MyStack();
        head = new Point(0,0);
        tail = head;
    }
    
    public void step(){
        //System.out.println("head is: "+head);
        if(firstStep){
            firstStep = false;
            r.cells[0][0].point = true;
            r.cells[0][0].paths[0] = true;
        }
        else if(!backTracking){
            Point[] choices = r.allStepChoices(tail, head);
            if(r.getExit(choices) != null){
                Point exit = r.getExit(choices);
                tail = head;
                head = exit;
                r.takeStep(tail, head,true);
                r.cells[head.y][head.x].point = false;
                if(!r.cells[exit.y][exit.x].sides[1] && exit.y == 0)
                    r.cells[exit.y][exit.x].paths[1] = true;
                else if(!r.cells[exit.y][exit.x].sides[2] && exit.x+1 >= r.cells[0].length)
                    r.cells[exit.y][exit.x].paths[2] = true;
                else if(!r.cells[exit.y][exit.x].sides[3] && exit.y+1 >= r.cells.length)
                    r.cells[exit.y][exit.x].paths[3] = true;
                System.out.println("FREEDOM! after "+s.length+" choices made and "+deadEnds+" dead Ends!");
                System.out.print("Soltion is: ");
                int[] x = new int[s.length];
                for(int i = 1;i<=x.length;i++)
                    x[x.length - i] = (int)s.pop();
                for(int i = 0;i<x.length;i++)
                    System.out.print((x[i]+1)+((i != x.length-1)?",":";"));
                System.out.println();
                done = true;
            }
            //backtracking !
            else if(choices.length == 0){
                backTracking = true;
                deadEnds++;
            }
            //no choices: move along
            else{
                tail = head;
                head = choices[0];
                r.takeStep(tail, head,true);
                if(choices.length > 1){
                    s.push(0);
                }
            }
        }
        //Bactracking
        else{
            r.backTrack(head);
            head = r.getTail(head);
            tail = r.getTail(head);
            int choice = 1;
            Point[] choices = r.allStepChoices(tail, head);
            if(choices.length > 1){
                choice += (int)s.pop();
                if(choice < choices.length){
                    backTracking = false;
                    tail = head;
                    head = choices[choice];
                    r.takeStep(tail, head,true);
                    s.push(choice);
                }
            }
        }
    }
    
    public void GameUpdate(double delta){
        if(!done){
        if(timer < incrementer)
            timer += delta;
        else{
            timer = 0;
            turnsPerCycle *=1.05;
        }
        for(int i = 0;i<turnsPerCycle;i++)
            if(!done)step();
        }
    }
}