package com.example.paint_1;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class PaletteHandler {
    private Map<Rectangle, Color> cMap = new HashMap<>();

    Rectangle[] p = new Rectangle[8];
    Rectangle currentColor = new Rectangle (50,50);

    public PaletteHandler(){
        for (int i = 0; i < 8; i++){
            p[i] = new Rectangle(20,20);
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

        }
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

    public void setCurrentColor(Color c){
        currentColor.setFill(c);
    }




}
