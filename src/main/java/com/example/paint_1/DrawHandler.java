package com.example.paint_1;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

enum DrawType{
    NONE,
    FREE,
    LINE,
    CIRCLE,
}
public class DrawHandler {
    private DrawType dType;

    Point2D posA;
    boolean firstClick;

    Color color = Color.BROWN;

    double width;




    public DrawHandler(){
        dType = DrawType.NONE;
        posA = new Point2D(0,0);
        firstClick = true;

        width = 20;
    }

    public void click(){
        firstClick = !firstClick;
    }

    public boolean isFirstClick(){
        return firstClick;
    }

    public double getWidth(){ return width; }

    public Color getCurrentColor(){
        return color;
    }

    public DrawType getDrawType(){
        return dType;
    }

    public Point2D getPosA(){
        return posA;
    }

    public double getPosAX(){
        return posA.getX();
    }

    public double getPosAY(){
        return posA.getY();
    }

    public void setWidth(double c) { width = c; }

    public void setCurrentColor(Color c){
        color = c;
    }

    public void setDrawType(DrawType d){
        dType = d;
    }

    public void setDrawType(String s){
        switch (s){
            case "FREE": dType = DrawType.FREE;
            break;
            case "LINE": dType = DrawType.LINE;
            break;
            default: dType = DrawType.NONE;
            break;
        }
    }

    public void setPosA(double x, double y){
        posA = new Point2D(x, y);
    }




}
