/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author maxno
 */
public class Room {
    
    public char[][] map;
    public Cell[][] cells;
    private int minWidth = 3;
    private int maxWidth = 100;
    private int minHeight = 3;
    private int maxHeight = 100;
    
    public Room(){
        
        int width  = (int)(Math.random()*maxWidth)+minWidth;
        int height = (int)(Math.random()*maxHeight)+minHeight;
        
        cells = new Cell[height][width];
        for(int x = 0;x<height;x++)
            for(int y = 0;y<width;y++)
                cells[x][y] = new Cell();
    }
    
    public Room(String[] lines){
        String[] firstLine = lines[0].split(" ");
        int height = Integer.parseInt(firstLine[0]);
        int width = Integer.parseInt(firstLine[1]);

        map = new char[height][width];
        cells = new Cell[height-2][width-2];

        for(int i = 1;i<lines.length;i++){
            for(int x = 0;x<width;x++)
                if(x < lines[i].length())
                    map[i-1][x] = lines[i].charAt(x);
                else
                    map[i-1][x] = ' ';
        }

        for(int y = 0;y < cells.length;y++)
            for(int x = 0;x < cells[y].length;x++){
                cells[y][x] = new Cell();
                cells[y][x].isSetUp = true;
                cells[y][x].sidesUp();
                if(map[y+1][x+1] == '*'){
                    cells[y][x].isBlocked = true;
                }
                else{
                    if(map[y+1][x] == ' '){
                        if(x == 0)
                            cells[y][x].isExit = true;
                        cells[y][x].sides[0] = false;
                    }
                    if(map[y][x+1] == ' '){
                        if(y == 0)
                            cells[y][x].isExit = true;
                        cells[y][x].sides[1] = false;
                    }
                    if(map[y+1][x+2] == ' '){
                        if(x == cells[y].length-1)
                            cells[y][x].isExit = true;
                        cells[y][x].sides[2] = false;
                    }
                    if(map[y+2][x+1] == ' '){
                        if(y == cells.length-1)
                            cells[y][x].isExit = true;
                        cells[y][x].sides[3] = false;
                    }
                }

            }
        cells[0][0].sides[0] = false;
    }
    
    public Point carveCell(Point eFrom,Point from,Point to){
         
        cells[from.y][from.x].isSetUp = true;
        cells[from.y][from.x].sidesUp();
        
        if(eFrom.x < from.x || to.x < from.x)
            cells[from.y][from.x].sides[0] = false;
        if(eFrom.x > from.x || to.x > from.x)
            cells[from.y][from.x].sides[2] = false;
        if(eFrom.y < from.y || to.y < from.y)
            cells[from.y][from.x].sides[1] = false;
        if(eFrom.y > from.y || to.y > from.y)
            cells[from.y][from.x].sides[3] = false;
        return to;
    }
    
    public void carveOut(Point from, Point to){
        
        cells[to.y][to.x].isSetUp = true;
        cells[to.y][to.x].sidesUp();
        
        if(from.y > to.y)
            cells[to.y][to.x].sides[3] = false;
        else if(from.y < to.y)
            cells[to.y][to.x].sides[1] = false;
        else if(from.x > to.x)
            cells[to.y][to.x].sides[2] = false;
        else
            cells[to.y][to.x].sides[0] = false;
    }
    
    public Point getNeighbour(Point p,boolean isSetUp){
        ArrayList<Point> neighbors = new ArrayList();
        if(p.x+1< cells[0].length && !(cells[p.y][p.x+1].isSetUp^isSetUp))
            neighbors.add(new Point(p.x+1,p.y));
        if(p.x-1>= 0 && !(cells[p.y][p.x-1].isSetUp^isSetUp))
            neighbors.add(new Point(p.x-1,p.y));
        if(p.y+1< cells.length && !(cells[p.y+1][p.x].isSetUp^isSetUp))
            neighbors.add(new Point(p.x,p.y+1));
        if(p.y-1>= 0 && !(cells[p.y-1][p.x].isSetUp^isSetUp))
            neighbors.add(new Point(p.x,p.y-1));
        if(!neighbors.isEmpty()){
            return neighbors.get((int)(Math.random()*neighbors.size()));
        }
        return null;
    }
    
    public Point[] getAllNeighbours(Point p,boolean isSetUp){
        ArrayList<Point> neighbors = new ArrayList();
        if(p.x+1< cells[0].length && !(cells[p.y][p.x+1].isSetUp^isSetUp))
            neighbors.add(new Point(p.x+1,p.y));
        if(p.y+1< cells.length && !(cells[p.y+1][p.x].isSetUp^isSetUp))
            neighbors.add(new Point(p.x,p.y+1));
        if(p.x-1>= 0 && !(cells[p.y][p.x-1].isSetUp^isSetUp))
            neighbors.add(new Point(p.x-1,p.y));
        if(p.y-1>= 0 && !(cells[p.y-1][p.x].isSetUp^isSetUp))
            neighbors.add(new Point(p.x,p.y-1));
        if(!neighbors.isEmpty()){
            Point[] result = new Point[neighbors.size()];
            for(int i = 0;i<neighbors.size();i++)
                result[i] = neighbors.get(i);
            return result;
        }
        return null;
    }
    
    public boolean canStep(Point from,Point to){        
        return to.y >= 0 && to.x >= 0 && to.y < cells.length && to.x < cells[0].length &&
                ( (from.x > to.x && !cells[from.y][from.x].sides[0])
                ||(from.y > to.y && !cells[from.y][from.x].sides[1])
                ||(from.x < to.x && !cells[from.y][from.x].sides[2])
                ||(from.y < to.y && !cells[from.y][from.x].sides[3]));
    }
    
    public Point[] allStepChoices(Point tail,Point head){
        
        Point[] neighbours = getAllNeighbours(head,true);
        ArrayList<Point> choices = new ArrayList();
        for(Point p: neighbours){
            if((p.x != tail.x || p.y != tail.y) && canStep(head,p)){
                choices.add(p);
            }
        }
        //Left hand rule Sorting
        Point[] result = new Point[choices.size()];
        if(choices.size()>1){
            for(int z = 0;z<2;z++)
                for(int i = 1;i<choices.size();i++){
                    if(vectorHelper(tail,head,choices.get(i)) < vectorHelper(tail,head,choices.get(i-1))){
                        Point low = choices.get(i-1);
                        Point high = choices.get(i);
                        choices.remove(i-1);
                        choices.remove(i-1);
                        choices.add(i-1,low);
                        choices.add(i-1,high);
                    }   
                }
            for(int i = 0;i<choices.size();i++)            
                result[i] = choices.get(i);
        }
        else if(choices.size()==1)
            result[0] = choices.get(0);
        
        return result;
    }
    
    private int vectorHelper(Point tail,Point head,Point choice){
        return (head.x-tail.x)*(choice.y-head.y)-(head.y-tail.y)*(choice.x-head.x);
    }
    
    public Point getExit(Point[] choices){
        for(Point p: choices)
            if(cells[p.y][p.x].isExit)
                return p;
        return null;
    }
    
    public void takeStep(Point from,Point to,boolean forward){
        if(canStep(from,to)){
            int f = 0;
            int t = 0;
            if(from.y > to.y){
                f = 1;
                t = 3;
            }
            else if(from.y < to.y){
                f = 3;
                t = 1;
            }
            else if(from.x > to.x){
                f = 0;
                t = 2;
            }
            else if(from.x < to.x){
                f = 2;
                t = 0;
            }
            
            cells[from.y][from.x].paths[f] = forward;
            cells[to.y][to.x].paths[t] = forward;
            cells[from.y][from.x].point = !forward;
            if(!forward)
                cells[from.y][from.x].pointSize = .4f;
            cells[to.y][to.x].point = forward;
        }
    }
    
    public void backTrack(Point head){
        Point tail = getTail(head);
        takeStep(head,tail,false);
    }
    
    public Point getTail(Point head){
        Point[] steps = allStepChoices(head,head);
        Point tail = head;
        for(Point p:steps)
            if(cells[p.y][p.x].hasPath())
                tail = p;
        return tail;
    }
    
    public void paint(Graphics g,Rectangle screen){
        g.setColor(Color.lightGray);
        g.fillRect(screen.x, screen.y, screen.width, screen.height);
        
        int cellSize = Math.min(screen.width/cells[0].length,screen.height/cells.length);        
        Rectangle rec = screen;
        rec.x += (screen.width-cellSize*cells[0].length)/2;
        rec.y += (screen.height-cellSize*cells.length)/2;
        rec.width = cellSize*cells[0].length;
        rec.height = cellSize*cells.length;
        for(int y = 0;y<cells.length;y++)
            for(int x = 0;x<cells[y].length;x++)
                cells[y][x].paint(g, new Rectangle(rec.x+x*cellSize,rec.y+y*cellSize,cellSize,cellSize));
    }
}
