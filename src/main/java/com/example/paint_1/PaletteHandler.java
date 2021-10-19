package com.example.paint_1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class PaletteHandler {
    private Map<Rectangle, Color> cMap = new HashMap<>();

    Rectangle[] p = new Rectangle[8];
    Rectangle currentColor = new Rectangle (50,50);

    Line[] l = new Line[3];
    Line menuL;
    public double thin, def, thick;
    private int currentL;

    public PaletteHandler(){
        thin = 1.0;
        def = 5.0;
        thick = 10.0;


        for (int i = 0; i < p.length; i++){
            p[i] = new Rectangle(20,20);
            currentL = 1;
            switch (i){
                case 0:
                    p[i].setFill(Color.WHITE);
                    p[i].setStroke(Color.BLACK);
                    break;
                case 1:
                    p[i].setFill(Color.RED);
                    p[i].setStroke(Color.BLACK);
                    break;
                case 2:
                    p[i].setFill(Color.YELLOW);
                    p[i].setStroke(Color.BLACK);
                    break;
                case 3:
                    p[i].setFill(Color.ORANGE);
                    p[i].setStroke(Color.BLACK);
                    break;
                case 4:
                    p[i].setFill(Color.BLACK);
                    p[i].setStroke(Color.BLACK);
                    break;
                case 5:
                    p[i].setFill(Color.BLUE);
                    p[i].setStroke(Color.BLACK);
                    break;
                case 6:
                    p[i].setFill(Color.PURPLE);
                    p[i].setStroke(Color.BLACK);
                    break;
                case 7:
                    p[i].setFill(Color.GREEN);
                    p[i].setStroke(Color.BLACK);
                    break;
                default:
                    p[i].setFill(Color.BROWN);
                    p[i].setStroke(Color.BLACK);
                    break;
            }
            cMap.put(p[i], (Color) p[i].getFill());
            this.setCurrentColor(Color.BROWN);
            currentColor.setStrokeWidth(2);
            currentColor.setStroke(Color.BLACK);

            if ( i < l.length ){
                l[i] = new Line(0,10,20,10);
                switch (i){
                    case 0:
                        l[i].setStrokeWidth(thin);
                        break;
                    case 1:
                        l[i].setStrokeWidth(def);
                        break;
                    case 2:
                        l[i].setStrokeWidth(thick);
                        break;
                    default:
                        break;
                }
            }


        }
        menuL = this.getCurrentLine();
        menuL.setStartX(0);
        menuL.setStartY(10);
        menuL.setEndX(20);
        menuL.setEndY(10);

    }

    public Line getMenuLine(){
        return menuL;
    }

    public Line getCurrentLine(){
        switch (currentL){
            case 1:
                return l[0];
            case 2:
                return l[1];
            case 3:
                return l[2];
            default:
                break;
        }
        return l[0];
    }

    public Line getLine(int i){
        if (i>2) {
            currentL = 2;
            return l[2];
        }
        currentL = i;
        return l[i];
    }

    public Color getCurrentColor(){
        return (Color) currentColor.getFill();
    }

    public Rectangle getCurrentColorRect(){
        return currentColor;
    }

    public Rectangle getRect(int i) {
        return p[i];
    }

    public Color getColor(int i){
        return cMap.get(p[i]);
    }

    public void setMenuLine(){
        menuL = getCurrentLine();
    }

    public void setCurrentColor(Color c){
        currentColor.setFill(c);
    }

    public void setCurrentLine(int i){
        if (i != thin && i != def && i!= thick) currentL = 1;
        else currentL = i;
        this.setMenuLine();
    }




}
